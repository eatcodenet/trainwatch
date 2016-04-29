#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
hazelcast_servers=${1:-localhost}
jar_file=${app_home}/libs/train-movement-1.0-SNAPSHOT.jar
crs_file=${app_home}/data/stations.json
tiploc_file=${app_home}/data/tiplocs.json
schedule_file=${app_home}/data/full-train-schedules
uname=$(uname)

function cleanup() {
	echo "cleanup..."
	kill_pid=$(ps -ef | grep [P]opulateLocationsApp | tr -s ' ' | cut -f2 -d' ')
	if [ ! -z "${kill_pid}" ]; then
		kill ${kill_pid}
	fi
}

trap cleanup SIGINT

if [ "${uname}" == "Darwin" ];then
	jar_file=${base_dir}/../build/libs/train-reference-data-1.0-SNAPSHOT.jar
fi
echo "jar_file is ${jar_file}"

echo "Populating locations..."
java -cp ${jar_file} net.eatcode.trainwatch.nr.dataimport.PopulateLocationsApp ${hazelcast_servers} ${crs_file} ${tiploc_file}

if [ $? -ne 0 ];then
  echo "Populating locations failed!"
  exit 1
fi

echo "Populating schedules..."
java -Xms1g -Xmx2g -cp ${jar_file} net.eatcode.trainwatch.nr.dataimport.PopulateSchedulesApp ${hazelcast_servers} ${schedule_file}

if [ $? -ne 0 ];then
  echo "Populating schedules failed!"
  exit 1
fi

exit 0
