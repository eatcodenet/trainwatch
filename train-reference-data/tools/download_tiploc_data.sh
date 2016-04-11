#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

file_name=corpus_tiplocs
full_url="http://datafeeds.networkrail.co.uk/ntrod/SupportingFileAuthenticate?type=CORPUS"
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
echo "Downloading from: ${full_url}"
echo "Downloading to: ${full_path}"

status=$(curl -# -w "%{http_code}" -L -o ${full_path} -u ${username}:${password} ${full_url})
if [[ "${status}" == "200" ]]; then
  echo "Unzipping"
  cd ${download_dir}
  gzip -fd ${file_name}.gz
  echo "Done."
else
  echo "Download failed. Status was ${status}."
fi

# sed hack to count CRS codes
# sed -E 's/3ALPHA\":\"/\'$'\n\2/g' ${fullpath}  | cut -d',' -f1 | sed -E 's/"|^B "//g' | sed '/^$/d' | wc -l
