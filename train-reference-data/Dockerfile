FROM eatcode/openjdk8

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ADD build/data/toc-full.cleaned /var/trainwatch/data/

ADD build/data/tiplocs.json /var/trainwatch/data/

ADD build/data/stations.json /var/trainwatch/data/

ADD tools/populate_ref_data.sh /var/trainwatch/tools/

ADD build/libs/train-reference-data-1.0-SNAPSHOT.jar /var/trainwatch/libs/

CMD /var/trainwatch/tools/populate_ref_data.sh