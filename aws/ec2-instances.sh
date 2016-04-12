#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

command=${1}
if [[ -z "${command}" ]];then
  echo "usage: ${0} <start | stop | run>"
  exit 1
fi

shopt -s expand_aliases
alias ec2='aws --profile eatcode ec2'

if [ "${command}" == "run" ];then
  echo "${command} instances..."
  user_data_file="${base_dir}/user-data.yml"
  aws_result=$(ec2 run-instances \
    --image-id ami-7abd0209 \
    --security-group-ids sg-978b63f0 \
    --subnet-id subnet-4c925e28 \
    --iam-instance-profile Name="Eatcode-EC2-deploy" \
    --instance-type t2.medium \
    --associate-public-ip-address \
    --key-name aws-eatcode \
    --count 1 \
    --user-data "$(<${user_data_file})")

  if [ "$?" == "0" ]; then
    instance_ids=$(grep INSTANCES <<< "${aws_result}" | cut -f8 | tr '\n' ' ')
    echo "Tagging instances ${instance_ids}"
    ec2 create-tags --resources ${instance_ids} --tags Key=Name,Value="eatcode-trainwatch"
    ec2 describe-instances --instance-ids ${instance_ids} --query "Reservations[*].Instances[*].PublicIpAddress"
  fi
  exit $?
fi

instance_ids=$(ec2 describe-instances --filters "Name=tag-value,Values=eatcode-trainwatch" | grep InstanceId | cut -d':' -f2 | sed -e 's/[", ]//g')
echo "${command} instances: ${instance_ids}"
ec2 ${command}-instances --instance-ids ${instance_ids}
