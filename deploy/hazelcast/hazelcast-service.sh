#! /bin/sh
#
#       /etc/rc.d/init.d/hazelcast
#
#       Starts hazelcast as a daemon
#
# chkconfig: 2345 90 10
# description: Starts hazelcast as a daemon.

### BEGIN INIT INFO
# Provides: hazelcast
# Required-Start: $local_fs $remote_fs
# Required-Stop: $local_fs $remote_fs
# Default-Start: 2 3 4 5
# Default-Stop: S 0 1 6
# Short-Description: hazelcast
# Description: Starts hazelcast as a daemon.
### END INIT INFO

hazelcast_home=/opt/hazelcast

function start {
  cd ${hazelcast_home}
  #rm -f ${hazelcast_home}/hazelcast.pid
  java_cmd="/usr/bin/java -server -cp ${hazelcast_home}/lib/hazelcast-3.7.2.jar -Dhazelcast.config=${hazelcast_home}/bin/hazelcast.xml com.hazelcast.core.server.StartServer"
  cmd="nohup ${java_cmd} >> ${hazelcast_home}/service.log 2>&1 & echo \$! >${hazelcast_home}/hazelcast.pid"
  su -c "${cmd}"
  pid="$(<${hazelcast_home}/hazelcast.pid)"
  echo "Started process with id: ${pid}"
  return 0;
}

function stop {
  if [ -f "${hazelcast_home}/hazelcast.pid" ]; then
    pid="$(<${hazelcast_home}/hazelcast.pid)"
    echo "Killing process with id: ${pid}"
    kill -s TERM ${pid} || return 1
    rm -f ${hazelcast_home}/hazelcast.pid
  fi
  return 0;
}

function main {
  ret_val=0
  case "${1}" in
    start)
      start
      ;;
    stop)
      stop
       ;;
    *)
      echo "Usage: ${0} {start|stop}"
      exit 1
      ;;
  esac
  exit ${ret_val}
}

main ${1}
