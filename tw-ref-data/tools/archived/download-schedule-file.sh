#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_url="https://datafeeds.networkrail.co.uk/ntrod/CifFileAuthenticate?type=CIF_ALL_FULL_DAILY&day=toc-full"
username=${1}
password=${2}
data_dir=/var/trainwatch/data
output_file=full-train-schedules.gz
is_zipped=true

# you will need to supply credentials for datafeeds
if [[ -z "${username}" || -z "${password}" ]]; then
  script_name="${BASH_SOURCE[0]}"
  echo "usage: ${script_name} username password"
  exit 1
fi

mkdir -p ${data_dir}
full_path="${data_dir}/${output_file}"
echo "Downloading from: ${full_url}"
echo "Downloading to: ${full_path}"

status=$(curl -# -w "%{http_code}" -L -o ${full_path} -u ${username}:${password} ${full_url})
if [ "${status}" != "200" ]; then
  echo "Download failed. Status was ${status}."
  exit 1
fi

if [ "${is_zipped}" == "true" ];then
  echo "Unzipping"
  cd ${data_dir}
  gzip -fd ${full_path}
fi
echo "Download complete"

#echo "Removing non schedule data"
#${base_dir}/clean-schedule-file.sh ${full_path%%.gz}