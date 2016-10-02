#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

template_file="${base_dir}/trainwatch-stack.json"
aws_result=$(aws cloudformation create-stack --stack-name 'TrainwatchStack' --capabilities CAPABILITY_IAM --template-body file://${template_file})
echo "${aws_result}"
