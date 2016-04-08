#!/bin/bash
base_dir="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

command=${1:-"oops"}

# fake script - wip
touch "${command}.txt"

