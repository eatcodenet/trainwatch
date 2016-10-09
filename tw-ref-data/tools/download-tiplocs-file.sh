#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_url="http://datafeeds.networkrail.co.uk/ntrod/SupportingFileAuthenticate?type=CORPUS"
username=${1}
password=${2}
data_dir=/var/trainwatch/data
output_file=tiplocs.json.gz
is_zipped=true

# you will need to supply credentials for datafeeds
if [[ -z "${username}" || -z "${password}" ]]; then
  script_name="${BASH_SOURCE[0]}"
  echo "usage: ${script_name} username password"
  exit 1
fi

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

# sed hack to count CRS codes
# sed -E 's/3ALPHA\":\"/\'$'\n\2/g' ${fullpath}  | cut -d',' -f1 | sed -E 's/"|^B "//g' | sed '/^$/d' | wc -l
