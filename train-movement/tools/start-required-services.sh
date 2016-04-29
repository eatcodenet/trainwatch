#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch
hz_home=/opt/hazelcast
kafka_home=/opt/kafka

function clean_up() {
  echo "clean up..."
}

trap clean_up SIGINT

function find_pid() {
  kill_pid=$(ps -ef | grep ${1} | tr -s ' ' | cut -f2 -d' ')
  echo ${kill_pid}
}

function kill_pid() {
  kill_pid=$(find_pid $1)
  if [ ! -z "${kill_pid}" ]; then
    kill -s SIGKILL ${kill_pid}
  fi
}
  
function kill_services() {
  kill_pid "[k]afka\.Kafka"
  kill_pid "[h]azelcast\.core"
  kill_pid "[z]ookeeper"
  rm -rf ${hz_home}/bin/hazelcast_instance.pid
  rm -rf ${app_home}/logs/*-start.log
  rm -rf /tmp/kafka-logs/.lock
}

function start_services() {
  mkdir -p ${app_home}/logs
  ${kafka_home}/bin/zookeeper-server-start.sh ${kafka_home}/config/zookeeper.properties > ${app_home}/logs/zookeeper-start.log 2>&1 &
  ${hz_home}/bin/start.sh > ${app_home}/logs/hazelcast-start.log 2>&1 &
  ${kafka_home}/bin/kafka-server-start.sh ${kafka_home}/config/server.properties > ${app_home}/logs/kafka-start.log 2>&1 &
}

kill_services
start_services
