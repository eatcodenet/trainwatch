#!/bin/bash
yum -y update
yum -y erase java-1.7.0*
yum install -y java-1.8.0-openjdk-devel curl wget

yum -y erase docker docker-engine
tee /etc/yum.repos.d/docker.repo <<-EOF
[dockerrepo]
name=Docker Repository
baseurl=https://yum.dockerproject.org/repo/main/centos/7
enabled=1
gpgcheck=1
gpgkey=https://yum.dockerproject.org/gpg
EOF

yum install -y docker-engine
service docker start
usermod -aG docker centos
