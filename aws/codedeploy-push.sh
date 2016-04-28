#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=$(cd ${base_dir}/.. && pwd )
build_dir=${src_dir}/build/awscodedeploy

rm -rf ${build_dir}
mkdir -p ${build_dir}/libs
cp ${src_dir}/train-movement/build/libs/train-movement-1.0-SNAPSHOT.jar ${build_dir}/libs
cp -R ${src_dir}/train-movement/tools ${build_dir}
cp -R ${src_dir}/train-reference-data/tools ${build_dir}
cp -R ${src_dir}/aws ${build_dir}
cp ${src_dir}/appspec.yml ${build_dir}

echo "Source dir: ${src_dir}"

username=${nr_username}
password=${nr_password}
if [[ -z "${username}" || -z "${password}" ]];then
  echo "WARNING: Credentials not fully set. Skipping creds.txt. Set nr_username/nr_password to create creds.txt"
else 
  echo "Creating creds.txt"
  echo "username=${username}" >  ${build_dir}/creds.txt
  echo "password=${password}" >> ${build_dir}/creds.txt
fi

echo "Pushing to S3. Run command below to deploy:"
bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${build_dir} --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
etag=$(sed 's/.*eTag="\(.*\)".*/\1/g' <<< ${aws_result})

echo "Deploying revision..."
echo "aws --profile eatcode deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group trainwatch-app"
aws --profile eatcode deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group trainwatch-app
echo
