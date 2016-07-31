#!/bin/bash

# ensure the gradle artifacts below have been built, i.e ./gradlew build

base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=$(cd ${base_dir}/.. && pwd )
aws_build_dir=${src_dir}/build/awscodedeploy

rm -rf ${aws_build_dir}
mkdir -p ${aws_build_dir}/libs
cp ${src_dir}/tw-train-movement/build/libs/tw-train-movement-1.0-SNAPSHOT.jar ${aws_build_dir}/libs
cp ${src_dir}/tw-search-api/build/libs/tw-search-api-1.0-SNAPSHOT.jar ${aws_build_dir}/libs
cp -R ${src_dir}/tw-train-movement/tools ${aws_build_dir}
cp -R ${src_dir}/tw-ref-data/tools ${aws_build_dir}
cp -R ${src_dir}/aws ${aws_build_dir}
cp ${src_dir}/appspec.yml ${aws_build_dir}

echo "Source dir: ${src_dir}"

username=${nr_username}
password=${nr_password}
if [[ -z "${username}" || -z "${password}" ]];then
  echo "ERROR: Credentials not fully set. Export nr_username/nr_password to create creds.txt"
  exit 0
else 
  echo "Creating creds.txt"
  echo "username=${username}" >  ${aws_build_dir}/creds.txt
  echo "password=${password}" >> ${aws_build_dir}/creds.txt
fi

echo "Pushing to S3"
bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${aws_build_dir} --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
etag=$(sed 's/.*eTag="\(.*\)".*/\1/g' <<< ${aws_result})

echo "Deploying revision..."
aws --profile eatcode deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group TrainWatch
echo "To deploy same revision again run:"
echo "aws --profile eatcode deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group TrainWatch"
echo
