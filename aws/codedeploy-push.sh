#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=${base_dir}/..

#${base_dir}/../gradlew clean build
echo "Removing unused files"
build_dir=${base_dir}/../train-reference-data/build
rm -rf ${build_dir}

build_dir=${base_dir}/../train-movement/build
rm -rf ${build_dir}

echo "Pushing to S3"
bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${src_dir} --application-name Trainwatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
etag=$(sed 's/.*eTag="\(.*\)".*/\1/g' <<< ${aws_result})
echo "aws --profile eatcode deploy create-deployment --application-name Trainwatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group app-only"
echo
