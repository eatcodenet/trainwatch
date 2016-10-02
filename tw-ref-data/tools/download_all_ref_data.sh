#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

deploy_dir=/var/trainwatch/deploy
data_dir=/var/trainwatch/data
creds=${deploy_dir}/creds.txt
if [ ! -r ${creds} ]; then
  echo "no readable creds. see ya!"
  exit 1
fi

username=$(grep username ${creds} | cut -d'=' -f2)
password=$(grep password ${creds} | cut -d'=' -f2)

${base_dir}/download_full_schedule.sh ${username} ${password}
${base_dir}/download_tiplocs.sh ${username} ${password}
${base_dir}/download_stations.sh ${username} ${password}
