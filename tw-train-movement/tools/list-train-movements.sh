#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
hazelcast_servers=${1:-localhost}
class_path=${app_home}/libs/tw-train-movement-1.0-SNAPSHOT.jar:${app_home}/libs/tw-search-api-1.0-SNAPSHOT.jar

function clean_up() {
  kill_pid=$(ps -ef | grep [H]zTrainWatchSearch | awk '{print $2}')
  if [ ! -z "${kill_pid}" ]; then
    kill ${kill_pid}
  fi
}
trap clean_up SIGINT

echo "class_path is ${class_path}"
echo "hazelcast_servers ${hazelcast_servers}"

java -Xms128m -Xmx256m -cp ${class_path} net.eatcode.trainwatch.search.hazelcast.ListMovementsApp ${hazelcast_servers}