#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
kafka_servers=${1:-localhost}
zookeeper_servers=${1:-localhost}
hazelcast_servers=${2:-localhost}
class_path=${app_home}/libs/tw-train-movement-1.0-SNAPSHOT.jar
uname=$(uname)
creds=${app_home}/creds.txt

function clean_up() {
  echo "clean_up..."
  kill_pid=$(ps -ef | grep [P]opulateLocationsApp | awk '{print $2}')
  if [ ! -z "${kill_pid}" ]; then
    kill ${kill_pid}
  fi
}
trap clean_up SIGINT

if [ "${uname}" == "Darwin" ];then
  class_path=${base_dir}/../build/libs/tw-train-movement-1.0-SNAPSHOT.jar
fi
echo "class_path is ${class_path}"

if [ ! -r ${creds} ]; then
  echo "no readable creds. see ya!"
  exit 1
fi

echo "Starting app..."
username=$(grep username ${creds} | cut -d'=' -f2)
password=$(grep password ${creds} | cut -d'=' -f2)
echo "kafka_servers ${kafka_servers}"
echo "zookeeper_servers ${zookeeper_servers}"
echo "hazelcast_servers ${hazelcast_servers}"

topic_name='train-movement'
topic=$( ${base_dir}/list-kafka-topics.sh | grep ${topic_name} )
if [ -z "${topic}" ]; then
  echo "Topic '${topic_name}' does not exist. Creating."
  ${base_dir}/create-kafka-topic.sh ${zookeeper_servers} ${topic_name}
fi

rm ${app_home}/logs/trainwatch.log
java -Xms512m -Xmx1g -cp ${class_path} net.eatcode.trainwatch.movement.kafka.TrainMovementApp "${kafka_servers}:9092" ${zookeeper_servers} ${hazelcast_servers} ${username} ${password} &
