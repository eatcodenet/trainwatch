FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ENV app_home /var/trainwatch

ENV data_dir $app_home/data

ARG nr_username

ARG nr_password

RUN mkdir -p $data_dir

ADD tools/download_full_schedule.sh $app_home

ADD tools/clean_full_schedule.sh $app_home

ADD tools/download_tiploc_data.sh $app_home 

ADD https://raw.githubusercontent.com/fasteroute/national-rail-stations/master/stations.json $data_dir

RUN $app_home/download_full_schedule.sh $nr_username $nr_password $data_dir

RUN $app_home/download_tiploc_data.sh $nr_username $nr_password $data_dir

LABEL name=buildnumber value=1

ADD build/libs/train-reference-data-1.0-SNAPSHOT.jar $app_home/train-reference.jar

RUN ls -lrt $app_home

#start hz

RUN ifconfig

RUN /usr/bin/java -server -Dhazelcast.config=$HZ_HOME/bin/hazelcast.xml com.hazelcast.core.server.StartServer &

RUN sleep 3

RUN /usr/bin/java -cp $app_home/train-reference.jar net.eatcode.trainwatch.nr.dataimport.PopulateSchedulesApp /var/trainwatch/data/toc-full.cleaned

