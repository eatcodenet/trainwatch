#!/usr/bin/env python3
import json


def tiplocs_map(data_file):
    result = {}
    with open(data_file, "r") as lines:
        for line in lines:
            value = json.loads(line)["TiplocV1"]
            key = value["tiploc_code"]
            result[key] = value
    return result


def stations_map(data_file):
    result = {}
    with open(data_file, "r") as lines:
        station_list = json.load(lines)["locations"]
        for value in station_list:
            key = value["tiploc"]
            result[key] = value
    return result


def create_locations(tiploc_map, station_map):
    result = []
    for key, value in tiploc_map.items():
        station = station_map.get(key, {"lat": "0.0", "lon": "0.0"})
        lat_lon = {"lat": station["lat"], "lon": station["lat"]}
        result.append({"stanox": value["stanox"],
                       "description": value["tps_description"],
                       "crs": value["crs_code"],
                       "tiploc": value["tiploc_code"],
                       "latLon": lat_lon})
    return result


def write_locations(data_file, locations):
    with open(data_file, 'w') as out:
        for l in locations:
            json.dump(l, out)
            out.write("\n")


def main():
    print("Loading tiplocs")
    tiplocs = tiplocs_map("/var/trainwatch/data/tiplocs.json")
    print("len tiplocs", len(tiplocs))

    print("Loading stations")
    stations = stations_map("/var/trainwatch/data/stations.json")
    print("len stations", len(stations))

    print("Creating locations")
    locations = create_locations(tiplocs, stations)
    print("len locations", len(locations))

    print("Writing location to file")
    write_locations("/var/trainwatch/data/locations.json", locations)


if __name__ == '__main__':
    main()
