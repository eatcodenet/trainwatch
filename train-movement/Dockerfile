FROM eatcode/openjdk8

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD build/libs/train-movement-1.0-SNAPSHOT.jar /var/trainwatch/libs/

ARG nrUsername 

ARG nrPassword

ENV kafkaServers "kafka:9092"

ENV hazelcastServers "hazelcast"

ENV username $nrUsername

ENV password $nrPassword

LABEL name=version value=3

CMD java -Xms512m -Xmx1g -cp /var/trainwatch/libs/train-movement-1.0-SNAPSHOT.jar net.eatcode.trainwatch.movement.kafka.TrainMovementApp $kafkaServers $hazelcastServers $username $password
