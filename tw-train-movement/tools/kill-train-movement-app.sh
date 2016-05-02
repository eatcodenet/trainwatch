#!/bin/bash
base_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
app_home=/var/trainwatch

ps -ef | grep [T]rainMovementApp | awk '{print $2}'| xargs kill
