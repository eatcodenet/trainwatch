#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

aws_result=$(aws ec2 describe-instances --filters "Name=tag-value,Values=trainwatch")
instance_ids=$(echo "${aws_result}" | grep INSTANCES | cut -f8)
#echo "instances: ${instance_ids}"

aws ec2 describe-instances --instance-ids ${instance_ids}
