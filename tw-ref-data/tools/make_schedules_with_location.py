import json
import time


def load_locations(data_file):
    with open(data_file, "r") as loc_file:
        return json.load(loc_file)["locations"]


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
    return {"id": id, "startDate": start, "endDate": end, "origin": orig,
            "destination": dest}

def write_to_file(schedules):
    with open("schedules_with_locations.json", "w") as out:
        for s in schedules:
            json.dump(s, out)
            out.write("\n")


print(load_locations("locations.json"))
start = time.time()
schedules = load_schedules("schedules.json")
end = time.time() - start
print("took {0:.2f}s".format(end))
write_to_file(schedules)