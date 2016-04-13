#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=${base_dir}/..

#${base_dir}/../gradlew clean build

rm -rf ${base_dir}/train-reference-data/build/full-train-schedules
rm -rf ${base_dir}/train-reference-data/build/tiplocs.json
rm -rf ${base_dir}/train-reference-data/build/stations.json
rm -rf ${base_dir}/train-reference-data/build/classes
rm -rf ${base_dir}/train-reference-data/build/dependency-cache
rm -rf ${base_dir}/train-reference-data/build/tmp

bundle_name=${1:-"LatestBundle.zip"}
aws_result=$(aws --profile eatcode deploy push --source ${src_dir} --application-name Trainwatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
echo $aws_result

etag=$(echo ${aws_result} | sed 's/.*eTag="\(.*\)".*/\1/g')
echo "ETAG: ${etag}"
echo "aws deploy create-deployment --application-name Trainwatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group app-only"
echo
