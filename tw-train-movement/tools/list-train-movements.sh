#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
hazelcast_servers=${1:-localhost}
jar_file=${app_home}/libs/tw-train-movement-1.0-SNAPSHOT.jar:${app_home}/libs/tw-search-api-1.0-SNAPSHOT.jar

function clean_up() {
  kill_pid=$(ps -ef | grep [H]zTrainWatchSearch | awk '{print $2}')
  if [ ! -z "${kill_pid}" ]; then
    kill ${kill_pid}
  fi
}
trap clean_up SIGINT

echo "jar_file is ${jar_file}"
echo "hazelcast_servers ${hazelcast_servers}"

java -Xms128m -Xmx256m -cp ${jar_file} net.eatcode.trainwatch.search.hazelcast.ListMovementsApp ${hazelcast_servers}