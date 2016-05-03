#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
hazelcast_servers=${1:-localhost}
class_path=${app_home}/libs/tw-train-movement-1.0-SNAPSHOT.jar
crs_file=${app_home}/data/stations.json
tiploc_file=${app_home}/data/tiplocs.json
schedule_file=${app_home}/data/full-train-schedules
uname=$(uname)

function clean_up() {
	echo "clean_up..."
	kill_pid=$(ps -ef | grep [P]opulateLocationsApp | awk '{print $2}')
	if [ ! -z "${kill_pid}" ]; then
		kill ${kill_pid}
	fi
}

trap clean_up SIGINT

if [ "${uname}" == "Darwin" ];then
	class_path=${base_dir}/../build/libs/tw-ref-data-1.0-SNAPSHOT.jar
fi
echo "class_path is ${class_path}"

echo "Populating locations..."
java -cp ${class_path} net.eatcode.trainwatch.nr.dataimport.PopulateLocationsApp ${hazelcast_servers} ${crs_file} ${tiploc_file}

if [ $? -ne 0 ];then
  echo "Populating locations failed!"
  exit 1
fi

echo "Populating schedules..."
java -Xms1g -Xmx2g -cp ${class_path} net.eatcode.trainwatch.nr.dataimport.PopulateSchedulesApp ${hazelcast_servers} ${schedule_file}

if [ $? -ne 0 ];then
  echo "Populating schedules failed!"
  exit 1
fi

exit 0
