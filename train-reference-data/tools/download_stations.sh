#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_url="https://github.com/fasteroute/national-rail-stations/blob/master/stations.json"
username=${1}
password=${2}
download_dir=${3:-/var/trainwatch/data}
output_file=stations.json
is_zipped=false

# you will need to supply credentials for datafeeds
if [[ -z "${username}" || -z "${password}" ]]; then
  script_name="${BASH_SOURCE[0]}"
  echo "usage: ${script_name} username password [download_dir]"
  exit 1
fi

full_path="${download_dir}/${output_file}"
echo "Downloading from: ${full_url}"
echo "Downloading to: ${full_path}"

status=$(curl -# -w "%{http_code}" -L -o ${full_path} -u ${username}:${password} ${full_url})
if [ "${status}" != "200" ]; then
  echo "Download failed. Status was ${status}."
  exit 1
fi

if [ "${is_zipped}" == "true" ];then
  echo "Unzipping"
  cd ${download_dir}
  gzip -fd ${full_path}
fi
echo "Download complete"