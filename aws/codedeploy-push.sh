#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

#${base_dir}/../gradlew clean build

rm -rf ${base_dir}/train-reference-data/build/full-train-schedules
rm -rf ${base_dir}/train-reference-data/build/tiplocs.json
rm -rf ${base_dir}/train-reference-data/build/stations.json
rm -rf ${base_dir}/train-reference-data/build/classes
rm -rf ${base_dir}/train-reference-data/build/dependency-cache
rm -rf ${base_dir}/train-reference-data/build/tmp

bundle_name=${1:-"LatestBundle.zip"}
echo "pushing"

aws_result=$(aws --profile eatcode deploy push --output text --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
echo $aws_result

etag=$(echo ${aws_result} | sed 's/.*eTag="\(.*\)".*/\1/g')
echo "ETAG: ${etag}"
echo "aws deploy create-deployment --application-name TrainWatch --s3-location bucket=eatcode-trainwatch-deploy,key=LatestBundle.zip,bundleType=zip,eTag=\"${etag}\" --deployment-group trainwatch-deploy"
echo 