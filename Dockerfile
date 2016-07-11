# build custom Kafka container with required application jars

FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD tw-ref-data/build/libs/tw-ref-data-1.0-SNAPSHOT.jar ${HZ_HOME}/lib

ADD tw-train-movement/build/libs/tw-train-movement-1.0-SNAPSHOT.jar ${HZ_HOME}/lib

ADD aws/hazelcast/hazelcast.xml ${HZ_HOME}/bin

ENV LB_VERSION 1.1.7

RUN wget -q -O - "http://logback.qos.ch/dist/logback-${LB_VERSION}.tar.gz" | tar -xzf - -C /opt

RUN cp /opt/logback-${LB_VERSION}/logback-core-${LB_VERSION}.jar ${HZ_HOME}/lib

RUN cp /opt/logback-${LB_VERSION}/logback-classic-${LB_VERSION}.jar ${HZ_HOME}/lib

ENV SLF4J_VERSION 1.7.21

RUN wget -q -O - "http://www.slf4j.org/dist/slf4j-${SLF4J_VERSION}.tar.gz" | tar -xzf - -C /opt

RUN cp /opt/slf4j-${SLF4J_VERSION}/slf4j-api-${SLF4J_VERSION}.jar ${HZ_HOME}/lib

ADD https://bintray.com/artifact/download/easymock/distributions/objenesis-2.4-bin.zip /tmp

CMD java -server -cp ${HZ_HOME}/lib/'*' -Xms1g -Xmx1g -Dhazelcast.config=$HZ_HOME/bin/hazelcast.xml com.hazelcast.core.server.StartServer

