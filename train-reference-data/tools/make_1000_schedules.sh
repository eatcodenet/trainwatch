#!/bin/bash

base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_schedule_file=${1}

if [[ -z "${full_schedule_file}" ]]; then
  script_name="${BASH_SOURCE[0]}"
  echo "usage: ${script_name} full_schedule_file"
  exit 1
fi

out_dir=$( dirname ${full_schedule_file} )
grep JsonScheduleV1 < ${full_schedule_file} | grep schedule_location | head -1000 > ${out_dir}/1000_schedules.json
echo "Done."
