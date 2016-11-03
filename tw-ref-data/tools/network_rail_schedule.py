#!/usr/bin/env python3
import gzip
import requests
import sys
import time
from os import path
from requests.auth import HTTPBasicAuth


def check_args():
    if len(sys.argv) != 3:
        print("download_schedules.py <nr username> <nr password>")
        sys.exit(0)
    return (sys.argv[1], sys.argv[2])
    

def download_file(url, save_path, username, password):
    start = time.time()
    authentication = HTTPBasicAuth(username, password) if (username) else None
    r = requests.get(url, auth=authentication, stream=True)
    print("file length is {0}".format(r.headers["content-length"]))
    with open(save_path, "wb") as out_file:
        for chunk in r.iter_content(chunk_size=2048 * 1024): 
            out_file.write(chunk)
    return time.time() - start


def unzip(gz_file, save_path):
    start = time.time()
    with gzip.open(gz_file, "rb") as in_file:
        with open(save_path, "wb") as out_file:
            out_file.write(in_file.read())
    return time.time() - start


def extract_schedules_and_tiplocs(full_schedule_file, schedule_path, tiploc_path):
    start = time.time()
    with open(full_schedule_file, "r") as in_file:
        with open(schedule_path, "w") as out_file1:
            with open(tiploc_path, "w") as out_file2:
                for line in in_file:
                    if "JsonScheduleV1" and "schedule_location" in line:
                        out_file1.write(line)
                    elif "TiplocV1" in line and "stanox\":\"" in line:
                         out_file2.write(line)
    return time.time() - start


def main():
    
    schedule_url = "https://datafeeds.networkrail.co.uk/ntrod/CifFileAuthenticate?type=CIF_ALL_FULL_DAILY&day=toc-full"
    schedule_file = "/tmp/full-schedule"
    schedule_gz_file = schedule_file + ".gz"
    schedules_json = "/var/trainwatch/data/schedules.json"
    tiplocs_json = "/var/trainwatch/data/tiplocs.json"
    stations_url = "https://raw.githubusercontent.com/fasteroute/national-rail-stations/master/stations.json"
    stations_file = "/var/trainwatch/data/stations.json"
    (username, password) = check_args()
    
    print("Downloading stations")
    start = time.time()
    download_file(stations_url, stations_file, "", "")
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Downloading schedule file")
    start = time.time()
    download_file(schedule_url, schedule_gz_file, username, password)
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Unzipping schedule file")
    start = time.time()
    unzip(schedule_gz_file, schedule_file)
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Extracting schedules and tiplocs")
    extract_schedules_and_tiplocs(schedule_file, schedules_json, tiplocs_json)
    print("Took {0:.2f}s".format(time.time() - start))


if __name__ == '__main__':
    main()
