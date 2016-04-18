#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
hazelcast_servers=${1:-hazelcast}
jar_file=${app_dir}/libs/train-reference-data-1.0-SNAPSHOT.jar
crs_file=${app_dir}/data/stations.json
tiploc_file=${app_dir}/data/tiplocs.json
schedule_file=${app_dir}/data/full-train-schedules
uname=$(uname)

function cleanup() {
	echo "cleanup..."
	killall java
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
java -Xms1g -Xmx1g -cp ${jar_file} net.eatcode.trainwatch.nr.dataimport.PopulateSchedulesApp ${hazelcast_servers} ${schedule_file}

if [ $? -ne 0 ];then
  echo "Populating schedules failed!"
  exit 1
fi

exit 0
