#!/bin/bash
bundle_name=${1:-"LatestBundle.zip"}
aws deploy push --application-name TrainWatch --s3-location s3://eatcode-trainwatch-deploy/${bundle_name}
