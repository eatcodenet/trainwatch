#!/bin/bash
command=${1}
if [[ -z "${command}" ]];then 
  echo "usage: ${0} <start|stop>"
  exit 1
fi

instance_ids=$(aws --profile eatcode ec2 describe-instances --filters "Name=tag-value,Values=eatcode-trainwatch" | grep InstanceId | cut -d':' -f2 | sed -e 's/[", ]//g')
echo "${command} instances: ${instance_ids}"

aws --profile eatcode ec2 ${command}-instances --instance-ids ${instance_ids}
