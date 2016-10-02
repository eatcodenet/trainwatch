#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

aws_result=$(aws cloudformation delete-stack --stack-name 'TrainwatchStack')
echo "${aws_result}"
