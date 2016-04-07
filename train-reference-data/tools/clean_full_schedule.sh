#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
full_schedule_file=${1}

if [ -z "${full_schedule_file}" ];then
  echo "usage: ${BASH_SOURCE[0]} <schedule file>"
  exit 1
fi

cleaned_file="${full_schedule_file}.cleaned"

echo "Current dir is ${PWD}"
echo "Writing to file ${cleaned_file}"
grep JsonScheduleV1 < ${full_schedule_file} | grep schedule_location > ${cleaned_file}
echo "Done."

