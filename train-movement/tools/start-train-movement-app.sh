#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
kafka_servers=${1:-localhost}
zookeeper_servers=${1:-localhost}
hazelcast_servers=${2:-localhost}
jar_file=${app_home}/libs/train-movement-1.0-SNAPSHOT.jar
uname=$(uname)
creds=${app_home}/creds.txt

function cleanup() {
  echo "cleanup..."
  kill_pid=$(ps -ef | grep [P]opulateLocationsApp | tr -s ' ' | cut -f2 -d' ')
  if [ ! -z "${kill_pid}" ]; then
    kill ${kill_pid}
  fi
}
trap cleanup SIGINT

if [ "${uname}" == "Darwin" ];then
  jar_file=${base_dir}/../build/libs/train-movement-1.0-SNAPSHOT.jar
fi
echo "jar_file is ${jar_file}"

if [ ! -r ${creds} ]; then
  echo "no readable creds. see ya!"
  exit 1
fi

echo "Starting app..."
username=$(grep username ${creds} | cut -d'=' -f2)
password=$(grep password ${creds} | cut -d'=' -f2)
echo "kafka_servers ${kafka_servers}"
echo "zookeeper_servers ${hazelcast_servers}"
echo "hazelcast_servers ${hazelcast_servers}"

topic_name=train-movement
topic=$( ${base_dir}/list-topics.sh | grep ${topic_name} )
if [ -z "${topic}" ]; then
  echo "Topic '${topic_name}' does not exist. Creating."
  ${base_dir}/create-topics.sh
fi

java -Xms1g -Xmx2g -cp ${jar_file} net.eatcode.trainwatch.movement.kafka.TrainMovementApp "${kafka_servers}:9092" ${zookeeper_servers} ${hazelcast_servers} ${username} ${password}
