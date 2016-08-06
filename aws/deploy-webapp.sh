#!/bin/bash
set -e
# ensure the gradle artifacts below have been built, i.e ./gradlew build

base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=$(cd ${base_dir}/.. && pwd )
aws_build_dir=${src_dir}/build/awscodedeploy

rm -rf ${aws_build_dir}
mkdir -p ${aws_build_dir}/libs
cp ${src_dir}/tw-search-api/build/libs/tw-search-api*.jar ${aws_build_dir}/libs
cp ${src_dir}/tw-webapp/target/scala-2.11/tw-webapp*.jar ${aws_build_dir}/libs
cp -R ${src_dir}/aws/codedeploy ${aws_build_dir}

echo "Source dir: ${src_dir}"

echo "Pushing to S3"
bundle_name="TrainWatchWebappLatestBundle.zip"
aws_result=$(aws --profile eatcode deploy push --source ${aws_build_dir} --application-name TrainWatchWebapp --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name})
etag=$(sed 's/.*eTag="\(.*\)".*/\1/g' <<< ${aws_result})

echo "Deploying revision..."
aws --profile eatcode deploy create-deployment --application-name TrainWatchWebApp --s3-location bucket=eatcode-trainwatch-deploy,key=${bundle_name},bundleType=zip,eTag=\"${etag}\" --deployment-group TrainWatch
echo "To deploy same revision again run:"
echo "aws --profile eatcode deploy create-deployment --application-name TrainWatchWebapp --s3-location bucket=eatcode-trainwatch-deploy,key=${bundle_name},bundleType=zip,eTag=\"${etag}\" --deployment-group TrainWatch"
echo
