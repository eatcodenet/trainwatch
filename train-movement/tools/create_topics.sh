#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
kafka_home=/opt/kafka
zk_server=${1:-'docker.local'}
topic='train-movement'
$kafka_home/bin/kafka-topics.sh --zookeeper $zk_server --create --partitions 1 --replication 1 --topic $topic