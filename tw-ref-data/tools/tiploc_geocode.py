#!/usr/bin/env python3
import json
import os
import requests
import warnings

# this is a work in progress

def tiplocs_from_file(data_file):
    with open(data_file) as f:
        tiplocs = json.load(f)
        return tiplocs


def geo():
    key = os.environ["google_api_key"]
    url = "https://maps.googleapis.com/maps/api/geocode/json?key={0}" \
        "&region=uk&address={1}".format(key,"10 Downing Street, London")
    result = requests.get(url)
    print(result.json()["results"][0]["geometry"]["location"])

#tiplocs = tiplocs_from_file("/var/trainwatch/data/tiplocs.json")["TIPLOCDATA"]
geo()
