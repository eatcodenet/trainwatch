#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=$(cd ${base_dir}/.. && pwd )

#${base_dir}/../gradlew clean build
# so we dont pkg unused libs
build_dir=${base_dir}/../train-reference-data/build
rm -rf ${build_dir}

build_dir=${base_dir}/../train-movement/build
rm -rf ${build_dir}/classes
rm -rf ${build_dir}/dependency-cache
rm -rf ${build_dir}/reports

echo "Source dir: ${src_dir}"
echo "Pushing to S3. Run command below to deploy:"
bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${src_dir} --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
etag=$(sed 's/.*eTag="\(.*\)".*/\1/g' <<< ${aws_result})

echo
echo "aws --profile eatcode deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group trainwatch-app"
echo
