#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
data_dir=${app_dir}/data
compose_file=${app_dir}/docker-compose.yml

command=${1:-"oops"}
case "${command}" in
  application-stop)
    echo "stop.."
    /usr/local/bin/docker-compose -f ${compose_file} stop
    ;;

  before-install)
    echo "before..."
    /usr/bin/docker pull eatcode/hazelcast
    /usr/bin/docker pull eatcode/zookeeper
    /usr/bin/docker pull eatcode/kafka
    /usr/bin/docker pull eatcode/tw-train-movements
    ;;

  after-install)
    echo "after..."
    ;;

  application-start)
    echo "start..."
	/usr/local/bin/docker-compose -f ${compose_file} up
    ;;

  validate-service)
    echo "validate..."
    ;;
    
  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
