#!/bin/bash
bundle_name=${1:-"LatestBundle.zip"}
date_now=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

aws deploy create-deployment \
  --application-name TrainWatch \
  --s3-location bucket=eatcode-trainwatch-deploy,key=${bundle_name},bundleType=zip,eTag="9ac814e54b82b515a726e54ec686df5b-2" \
  --deployment-group-name eatcode-trainwatch \
  --deployment-config-name codeDeployDefault.OneAtATime \
  --description "Deployment at ${date_now}

