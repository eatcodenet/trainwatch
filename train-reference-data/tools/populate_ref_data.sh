#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_dir=/var/trainwatch
jar_file=${app_dir}/libs/train-reference-data-1.0-SNAPSHOT.jar
crs_file=${app_dir}/data/stations.json
tiploc_file=${app_dir}/data/tiplocs.json
schedule_file=${app_dir}/data/full-train-schedules
uname=$(uname)

if [ "${uname}" == "Darwin" ];then
  jar_file=${base_dir}/../build/libs/train-reference-data-1.0-SNAPSHOT.jar
fi
echo "jar_file is ${jar_file}"

echo "Populating locations"
java -cp ${jar_file} net.eatcode.trainwatch.nr.dataimport.PopulateLocationsApp ${crs_file} ${tiploc_file}

if [ $? -ne 0 ];then
  echo "Populating locations failed!"
  exit 1
fi

echo "Populating schedules"
java -cp ${jar_file} net.eatcode.trainwatch.nr.dataimport.PopulateSchedulesApp ${schedule_file}

if [ $? -ne 0 ];then
  echo "Populating schedules failed!"
  exit 1
fi
