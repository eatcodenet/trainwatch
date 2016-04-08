#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
data_dir=${app_dir}/data

command=${1:-"oops"}
case "${command}" in
  application-stop)
    echo "stop.."
    ;;

  before-install)
    echo "before...
    mkdir -p ${data_dir}
    chown ec2-user ${data_dir}
    ;;

  after-install)
    echo "after..."
    ;;

  application-start)
    echo "start..."
    ;;

  validate-service)
    echo "validate..."
    ;;

  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
