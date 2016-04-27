#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo hello

${base_dir}/hook-common.sh "application-start"
