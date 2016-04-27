#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
data_dir=${app_dir}/data
compose_file=${app_dir}/docker-compose.yml
hz_home=/opt/hazelcast
command=${1:-"oops"}
case "${command}" in

  application-stop)
    echo "stop.."
    /bin/touch /tmp/stop.txt
    ${hz_home}/bin/stop.sh
    ;;

  before-install)
    echo "before..."
    ;;

  after-install)
    echo "after..."
    /bin/touch /tmp/after.txt
    ;;

  application-start)
    echo "start..."
	/bin/touch /tmp/start.txt
    ;;

  validate-service)
    echo "validate..."
    ;;
    
  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac

