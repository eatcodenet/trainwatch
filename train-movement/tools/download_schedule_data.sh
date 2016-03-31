#!/bin/bash

base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

file_name="toc-full"
sched_full_url="https://datafeeds.networkrail.co.uk/ntrod/CifFileAuthenticate?type=CIF_ALL_FULL_DAILY&day=${file_name}"
username=${1}
password=${2}
download_dir=${3:-/tmp}

# you will need to supply credentials for datafeeds
if [[ -z "${username}" || -z "${password}" ]]; then
  script_name="${BASH_SOURCE[0]}"
  echo "usage: ${script_name} username password [download_dir]"
  exit 1
fi

full_path="${download_dir}/${file_name}.gz"
echo "Downloading from: ${sched_full_url}"
echo "Downloading to: ${full_path}"

status=$(curl -# -w "%{http_code}" -L -o ${full_path} -u ${username}:${password} ${sched_full_url})
if [[ "${status}" == "200" ]]; then
  echo "Unzipping"
  cd ${download_dir}
  gzip -fd ${file_name}.gz
  echo "Done."
else
  echo "Download failed. Status was ${status}."
fi
