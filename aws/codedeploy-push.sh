#!/bin/bash
bundle_name=${1:-"LatestBundle.zip"}
aws --profile eatcode deploy push --application-name TrainWatch --ignore-hidden-files --s3-location s3://eatcode-trainwatch-deploy/${bundle_name}
