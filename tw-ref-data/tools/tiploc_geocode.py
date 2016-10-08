#!/usr/bin/env python3
import json

data_dir = "/var/trainwatch/data"

def tiplocs_from_file():
    with open(data_dir + "/tiplocs.json") as f:
        tiplocs = json.load(f)
        return tiplocs


tiplocs = tiplocs_from_file()["TIPLOCDATA"]
for t in tiplocs:
    print(t)
