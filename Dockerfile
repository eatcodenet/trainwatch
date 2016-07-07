# build custom Kafka container with required application jars

FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD tw-train-movement/build/libs/tw-train-movement-1.0-SNAPSHOT.jar ${HZ_HOME}/libs

ADD aws/hazelcast/hazelcast.xml ${HZ_HOME}/bin


#CMD java -server -cp ${HZ_HOME}/lib/hazelcast-all-${HZ_VERSION}.jar \
#  -Xms1g -Xmx1g -Dhazelcast.config=$HZ_HOME/bin/hazelcast.xml com.hazelcast.core.server.StartServer


