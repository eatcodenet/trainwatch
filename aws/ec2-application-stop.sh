#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

${base_dir}/ec2-install.sh "application-stop"

