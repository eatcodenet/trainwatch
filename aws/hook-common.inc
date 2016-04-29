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
    ${kafka_home}/bin/kafka-server-stop.sh
    ;;

  before-install)
    echo "before..."
    rm -rf ${hz_home}/bin/hazelcast.xml
    rm -rf ${hz_home}/bin/start.sh
	rm -rf ${kafka_home}/config/server.properties
	rm -rf ${kafka_home}/bin/kafka-server-stop.sh
	rm -rf ${kafka_home}/bin/zookeeper-server-stop.sh
    ;;

  after-install)
    echo "after..."
    ;;

  application-start)
    ${hz_home}/bin/start.sh > /var/trainwatch/hazelcast.log 2>&1 &
	${kafka_home}/bin/kafka-server-start.sh -daemon ${kafka_home}/config/server.properties
    ;;

  validate-service)
    echo "validate..."
    ;;

  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
