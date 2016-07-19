# TrainWatch

Real time UK Train monitor. Created for _experimenting_ with Apache Kafka and streaming data etc.

Very much a work in progress! [Also trying out Docker automated builds!] (https://hub.docker.com/r/eatcode/)

## Network Rail Data feeds
The `tw-ref-data` and `tw-train-movement` modules use open data from [Network Rail](https://datafeeds.networkrail.co.uk).

**NB: When connecting to the NR data you will need to use your own credentials from the data feeds website**

You will need to set these as system properties if running any of the spike Java main classes e.g. `TrainMovementApp`

### Stomp Client
This project uses the source code for the [Stomp](https://stomp.github.io/) client from [Gozirra](http://www.germane-software.com/software/Gozirra).

The source code is included as Java code as there is no maven/gradle repository for it :(


##TODO
Add crontab to pull files regularly

'''(crontab -u ayub -l; echo '*/1 * * * * /tmp/hello.sh') | crontab -u ayub -

