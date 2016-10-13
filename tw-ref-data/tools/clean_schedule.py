#!/usr/bin/env python3
import sys
import time
from os import path


def extract_schedule(schedule_file):
    start = time.time()
    cleaned_file = path.dirname(schedule_file) + path.sep + "schedules.cleaned"
    with open(schedule_file, "r") as schedule:
        with open(cleaned_file, "w") as cleaned:
            for line in schedule:
                if "JsonScheduleV1" and "schedule_location" in line:
                    cleaned.write(line)
    return time.time() - start


def extract_tiplocs(schedule_file):
    start = time.time()
    tiploc_file = path.dirname(schedule_file) + path.sep + "tiplocs.cleaned"
    with open(schedule_file, "r") as schedule:
        with open(tiploc_file, "w") as tiplocs:
            for line in schedule:
                if "TiplocV" in line:
                    tiplocs.write(line)
    return time.time() - start

print("Cleaning schedules")
time_taken = extract_schedule(sys.argv[1])
print("Took {0:.2f}s".format(time_taken))

print("Extracting tiplocs")
time_taken = extract_tiplocs(sys.argv[1])
print("Took {0:.2f}s".format(time_taken))
