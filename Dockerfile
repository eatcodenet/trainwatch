# build custom Kafka container with required application jars

FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD tw-train-movement/build/libs/tw-train-movement-1.0-SNAPSHOT.jar ${HZ_HOME}/libs

ADD aws/hazelcast/hazelcast.xml ${HZ_HOME}/bin

# todo
CMD ls -lrt ${HZ_HOME}/libs 
