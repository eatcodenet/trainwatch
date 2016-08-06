#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

command=${1}
if [[ -z "${command}" ]]; then
  echo "usage: ${0} <start | stop | run>"
  exit 1
fi

shopt -s expand_aliases
alias ec2='aws --profile eatcode ec2 --output text'

aws_result=$(ec2 describe-instances --filters "Name=tag-value,Values=trainwatch")
instance_ids=$(echo "${aws_result}" | grep INSTANCES | cut -f8)
echo "${command} instances: ${instance_ids}"
ec2 ${command}-instances --instance-ids ${instance_ids}
