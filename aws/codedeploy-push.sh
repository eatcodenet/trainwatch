#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=${base_dir}/..

#${base_dir}/../gradlew clean build
echo "Removing unused files"
build_dir=${base_dir}/../train-reference-data/build
rm -rf ${build_dir}/full-train-schedules
rm -rf ${build_dir}/tiplocs.json
rm -rf ${build_dir}/stations.json
rm -rf ${build_dir}/classes
rm -rf ${build_dir}/dependency-cache
rm -rf ${build_dir}/reports
rm -rf ${build_dir}/resources
rm -rf ${build_dir}/test-results
rm -rf ${build_dir}/tmp

echo "Pushing to S3"
bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${src_dir} --application-name Trainwatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})

etag=$(echo ${aws_result} | sed 's/.*eTag="\(.*\)".*/\1/g')
echo 
echo "aws --profile eatcode deploy create-deployment --application-name Trainwatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group app-only"
echo
