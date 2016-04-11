#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_url="https://github.com/fasteroute/national-rail-stations/blob/master/stations.json"
username=${1}
password=${2}
download_dir=${3:-/var/trainwatch/data}
output_file=statios.gson

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
if [[ "${status}" == "200" ]]; then
  echo "Unzipping"
  cd ${download_dir}
  gzip -fd ${full_path}
  echo "Download complete"
else
  echo "Download failed. Status was ${status}."
  exit 1
fi

echo "Removing non schedule data"
${base_dir}/clean_full_schedule.sh ${full_path%%.gz}
