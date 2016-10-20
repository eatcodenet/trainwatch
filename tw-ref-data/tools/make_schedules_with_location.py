import json
import time
from itertools import izip

def load_locations(data_file):
    with open(data_file, "r") as loc_file:
        locations = json.load(loc_file)["locations"]
        return {loc["tiploc"]:loc for loc in locations}


def load_schedules(data_file):
    schedules = []
    with open(data_file, "r") as sched_file:
        for line in sched_file:
            schedules.append(make_schedule(line))
    return schedules


def make_schedule(line):
    s = json.loads(line)["JsonScheduleV1"]
    locs = s["schedule_segment"]["schedule_location"]
    id = s["CIF_train_uid"]
    orig = next((l for l in locs if l["location_type"] == "LO"))
    dest = next((l for l in locs if l["location_type"] == "LT"))
    start = s["schedule_start_date"]
    end = s["schedule_end_date"]
    return {"id": id, "startDate": start, "endDate": end, "origin": orig["tiploc_code"],
            "destination": dest["tiploc_code"], "departure": orig["departure"], "arrival": dest["arrival"]}

def write_to_file(schedules, locations):
    with open("/var/trainwatch/data/schedules_with_locations.json", "w") as out:
        for s in schedules:
            s["origin"] = locations[s["origin"]]
            s["destination"] = locations[s["destination"]]
            print(s)
            json.dump(s, out)
            out.write("\n")


locations = load_locations("/var/trainwatch/data/locations.json")

start = time.time()
schedules = load_schedules("/var/trainwatch/data/schedules.json")
end = time.time() - start
print("took {0:.2f}s".format(end))
write_to_file(schedules, locations)
