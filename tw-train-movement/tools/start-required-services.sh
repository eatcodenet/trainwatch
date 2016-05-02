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
  term_pid=$(ps -ef | grep ${1} | awk '{print $2}')
  echo ${term_pid}
}

function kill_pid() {
  the_sig=${1}
  the_pid=$(find_pid ${2})
  if [ ! -z "${the_pid}" ]; then
    kill -s ${the_sig} ${the_pid}
  fi
}
  
function kill_services() {
  kill_pid SIGTERM "[h]azelcast\.core"
  kill_pid SIGTERM "[z]ookeeper"
  kill_pid SIGKILL "[k]afka\.Kafka"
  rm -rf ${hz_home}/bin/hazelcast_instance.pid
  rm -rf /tmp/kafka-logs/.lock
  rm -rf ${app_home}/logs/*-start.log
}

function start_services() {
  mkdir -p ${app_home}/logs
  ${kafka_home}/bin/zookeeper-server-start.sh ${kafka_home}/config/zookeeper.properties > ${app_home}/logs/zookeeper-start.log 2>&1 &
  ${hz_home}/bin/start.sh > ${app_home}/logs/hazelcast-start.log 2>&1 &
  ${kafka_home}/bin/kafka-server-start.sh ${kafka_home}/config/server.properties > ${app_home}/logs/kafka-start.log 2>&1 &
}

kill_services
start_services
