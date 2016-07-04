# build custom Kafka container with required application jars

FROM eatcode/docker-hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD tw-ref-data/build/libs/tw-ref-data-1.0-SNAPSHOT.jar ${HZ_HOME}/libs

RUN echo "$kafkaServers $zookeeperServers $hazelcastServers" 

CMD ls -lrt ${HZ_HOME}/libs 
