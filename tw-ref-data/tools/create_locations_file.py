#!/usr/bin/env python3
import json
import sys


def tiplocs_by_crs(data_file):
    result = {}
    with open(data_file, "r") as lines:
        for line in lines:
            value = json.loads(line)["TiplocV1"]
            key = value["crs_code"]
            if key:
                result[key] = value
    return result


def stations_by_crs(data_file):
    result = {}
    with open(data_file, "r") as lines:
        station_list = json.load(lines)["locations"]
        for value in station_list:
            key = value["crs"]
    return result


print("Loading tiplocs")
tiplocs = tiplocs_by_crs(sys.argv[1])
print("len tiplocs", len(tiplocs))

print("Loading stations")
stations = stations_by_crs(sys.argv[2])
print("len stations", len(stations))
