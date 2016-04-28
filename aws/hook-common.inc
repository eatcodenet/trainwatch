#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
data_dir=${app_dir}/data
compose_file=${app_dir}/docker-compose.yml
hz_home=/opt/hazelcast

# command should be set
case "${command}" in
  application-stop)
    echo "stop..."
    ${hz_home}/bin/stop.sh
    ;;
  before-install)
    echo "before..."
    rm -rf ${hz_home}/bin/hazelcast.xml
    rm -rf ${hz_home}/bin/start.sh
    ;;
  after-install)
    echo "after..."
    ;;
  application-start)
    echo "start..."
	exec ${hz_home}/bin/start.sh
    ;;
    
  validate-service)
    echo "validate..."
    ;;
    
  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
