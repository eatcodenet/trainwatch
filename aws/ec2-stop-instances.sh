#!/bin/bash
instance_ids=$(aws --profile eatcode ec2 describe-instances --filters "Name=tag-value,Values=eatcode-trainwatch" | grep InstanceId | cut -d':' -f2 | sed -e 's/[", ]//g')
echo "Stopping instances: ${instance_ids}"

aws --profile eatcode ec2 stop-instances --instance-ids ${instance_ids}
