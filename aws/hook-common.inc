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
    ;;

  before-install)
    echo "before..."
    rm -rf ${hz_home}/bin/hazelcast.xml
    rm -rf ${hz_home}/bin/start.sh
	rm -rf ${kafka_home}/config/server.properties
	rm -rf ${kafka_home}/bin/kafka-server-stop.sh
	rm -rf ${kafka_home}/bin/zookeeper-server-stop.sh
	(echo '0 4 * * * /var/trainwatch/download_all_ref_data.sh') | crontab -u ec2-user -
	(crontab -u ec2-user -l; echo '15 4 * * * /var/trainwatch/populate_ref_data.sh') | crontab -u ec2-user -
    ;;

  after-install)
    echo "after..."
    ;;

  application-start)
    ;;

  validate-service)
    echo "validate..."
    ;;

  *)
    echo $"Usage: $0 {application-stop|before-install|after-install|application-start|validate-service}"
    exit 1
esac
