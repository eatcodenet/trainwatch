FROM eatcode/openjdk8

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD build/libs/train-movement-1.0-SNAPSHOT.jar /var/trainwatch/libs/

ARG nrUsername 

ARG nrPassword

ENV boostrapServer "kafka:9092"

ENV username $nrUsername

ENV password $nrPassword

LABEL name=ver value=1

CMD java -cp /var/trainwatch/libs/train-movement-1.0-SNAPSHOT.jar net.eatcode.trainwatch.movement.kafka.TrainMovementProducer $username $password $boostrapServer
