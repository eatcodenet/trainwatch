FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

RUN mkdir -p /var/trainwatch

WORKDIR /var/trainwatch

LABEL name="hazelcast" version="$HZ_VERSION"

RUN wget -q -O - "http://download.hazelcast.com/download.jsp?version=hazelcast-$HZ_VERSION&type=tar&p=177120386177120386" | tar -xzf - -C /opt \
    && ln -s /opt/hazelcast-$HZ_VERSION /opt/hazelcast


CMD ls -alrt /var/trainwatch

