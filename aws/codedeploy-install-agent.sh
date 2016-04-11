#!/bin/bash
yum -y update
yum install -y ruby
yum install -y aws-cli
cd /home/ec2-user
aws s3 cp s3://aws-codedeploy-eu-west-1/latest/install . --region eu-west-1
chmod +x ./install
./install auto
