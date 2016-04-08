#!/bin/bash
bundle_name=${1:-"LatestBundle.zip"}
date_now=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

aws --profile eatcode deploy create-deployment \
  --application-name TrainWatch \
  --s3-location bucket=eatcode-trainwatch-deploy,key=${bundle_name},bundleType=zip \
  --deployment-group-name trainwatch-deploy \
  --deployment-config-name codeDeployDefault.OneAtATime \
  --description "Deployment at ${date_now}"

