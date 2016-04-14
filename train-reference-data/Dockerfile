FROM eatcode/openjdk8

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD build/full-train-schedules /var/trainwatch/data/

ADD build/tiplocs.json /var/trainwatch/data/

ADD build/stations.json /var/trainwatch/data/

ADD tools/populate_ref_data.sh /var/trainwatch/tools/

ADD build/libs/train-reference-data-1.0-SNAPSHOT.jar /var/trainwatch/libs/

CMD /var/trainwatch/tools/populate_ref_data.sh
