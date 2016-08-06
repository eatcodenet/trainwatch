#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

shopt -s expand_aliases
alias cfn='aws --profile eatcode cloudformation --output text'

template_file="${base_dir}/trainwatch-stack.json"

aws_result=$(cfn create-stack --stack-name 'TrainwatchStack' --capabilities CAPABILITY_IAM --template-body file://${template_file})
echo "${aws_result}"
