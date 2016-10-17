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
            result[key] = value
    return result


def create_locations(tiploc_map, station_map):
    result = []
    for key in tiploc_map:
        lat_lon = {"lat": "0.0", "lon": "0.0"}
        station = station_map.get(key, None)
        if station:
            lat_lon = {"lat": station["lat"], "lon": station["lat"]}

        tiploc = tiploc_map.get(key)
        result.append({"stanox": tiploc["stanox"],
                       "description": tiploc["tps_description"],
                       "crs": tiploc["crs_code"],
                       "tiploc": tiploc["tiploc_code"],
                       "latLon": lat_lon})
    return result


def write_locations(data_file, location_list):
    locations_wrapper = {"locations": location_list}
    with open(data_file, 'w') as outfile:
        json.dump(locations_wrapper, outfile)


print("Loading tiplocs")
tiplocs = tiplocs_by_crs(sys.argv[1])
print("len tiplocs", len(tiplocs))

print("Loading stations")
stations = stations_by_crs(sys.argv[2])
print("len stations", len(stations))

print("Creating locations")
locations = create_locations(tiplocs, stations)
print("len locations", len(locations))

print("Writing location to file")
write_locations(sys.argv[3], locations)
