#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

shopt -s expand_aliases
alias cfn='aws --profile eatcode cloudformation --output text'

aws_result=$(cfn delete-stack --stack-name 'TrainwatchStack')
echo "${aws_result}"
