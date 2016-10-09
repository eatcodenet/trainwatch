#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
src_dir=$(cd ${base_dir}/.. && pwd )
deploy_dir=/var/trainwatch/deploy
kafka_dir=/opt/kafka/
hazelcast_dir=/opt/hazelcast

host=${1:-""}
if [[ -z "${host}" ]]; then
  echo "Host needs to be first parameter. Bye!"
  exit 1
fi

ssh -o StrictHostKeyChecking=no ${host} "touch ${deploy_dir}/start.txt"
ssh ${host} "mkdir -p ${deploy_dir}/{data,libs}"

scp ${src_dir}/tw-ref-data/tools/*.sh ${host}:${deploy_dir}
scp ${src_dir}/tw-ref-data/build/libs/*.jar ${host}:${deploy_dir}/libs
scp ${src_dir}/tw-train-movement/build/libs/*.jar ${host}:${deploy_dir}/libs
scp ${src_dir}/tw-search-api/build/libs/*.jar ${host}:${deploy_dir}/libs
scp ${base_dir}/ref-data/*.json ${host}:${deploy_dir}/data
scp ${base_dir}/hazelcast/hazelcast-service.sh ${host}:${deploy_dir}
ssh ${host} "sudo cp -f ${deploy_dir}/hazelcast-service.sh /etc/init.d/hazelcast"
ssh ${host} "sudo chkconfig --add /etc/init.d/hazelcast"

if [[ -z "${nr_username}" || -z "${nr_password}" ]]; then
  echo "WARNING: set nr_username and nr_password to enable download of data"
else
  echo "Creating creds.txt..."
  ssh ${host} "echo 'username=${nr_username}' >  ${deploy_dir}/creds.txt"
ssh ${host} "echo 'password=${nr_password}' >> ${deploy_dir}/creds.txt"
fi

schedule_file=$(ssh ${host} "ls ${data_dir} | grep full-train-schedules")
echo "Schedule_file: ${schedule_file}"
if [[ -z "${schedule_file}" ]]; then
  echo "No schedule_file, will download now. This may take some time..."
  ssh ${host} "/var/trainwatch/deploy/get-schedule-file-only.sh"
fi

echo "Done"
