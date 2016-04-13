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
aws --profile eatcode deploy push --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name}
