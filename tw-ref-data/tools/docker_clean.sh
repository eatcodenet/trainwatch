#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
remote_host=$1

if [ -z "${remote_host}" ]; then
  echo "Usage: docker_clean.sh <host>"
  exit 1
fi

ssh ${remote_host} << EOSSH
  cd /var/trainwatch
  docker ps -qa | xargs docker rm
  docker volume ls -qf dangling=true | xargs docker volume rm
  docker images -qf dangling=true | xargs docker rmi
EOSSH
