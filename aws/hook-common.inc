#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
data_dir=${app_dir}/data
compose_file=${app_dir}/docker-compose.yml
hz_home=/opt/hazelcast
kafka_home=/opt/kafka

# command should be set
case "${command}" in
  application-stop)
    echo "stop..."
    ${hz_home}/bin/stop.sh
    # kill zookeeper as copied from script without SIGINT
    ps ax | grep -i 'zookeeper' | grep -v grep | awk '{print $1}' | xargs kill
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
    /bin/touch /tmp/start.txt
	${hz_home}/bin/start.sh > /var/trainwatch/hazelcast.log 2>&1 &
	${kafka_home}/bin/zookeeper-server-start.sh -daemon ${kafka_home}/config/zookeeper.properties
    ;;
    
  validate-service)
    echo "validate..."
    ;;
    
  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
