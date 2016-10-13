#!/usr/bin/env python3

def clean(schedule_file):
    with open(schedule_file, "r") as schedule:
        with open(schedule_file + ".cleaned") as cleaned:
            for line in schedule:
                if "JsonScheduleV1" in line:
                    cleaned.write(line)

base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

full_schedule_file=${1}
cleaned_file="${full_schedule_file}.cleaned"
if [ -z "${full_schedule_file}" ];then
  echo "usage: ${BASH_SOURCE[0]} <schedule file>"
  exit 1
fi

echo "Current dir is ${PWD}"
echo "Writing to file ${cleaned_file}"
grep JsonScheduleV1 < ${full_schedule_file} | grep schedule_location > ${cleaned_file}
echo "Renaming ${cleaned_file} to ${full_schedule_file}"
rm -f ${full_schedule_file}
mv ${cleaned_file} ${full_schedule_file}