FROM eatcode/hazelcast

MAINTAINER Ayub Malik <ayub.malik@gmail.com>

ENV app_home /var/trainwatch

ENV data_dir $app_home/data

ARG nr_username

ARG nr_password

RUN mkdir -p $app_home/data

WORKDIR $app_home

ADD tools/download_full_schedule.sh $app_home

ADD tools/clean_full_schedule.sh $app_home

LABEL name=buildnumber value=1

RUN $app_home/download_full_schedule.sh $nr_username $nr_password $data_dir

CMD ls -alrt $app_home

