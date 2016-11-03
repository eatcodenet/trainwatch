import json
import sched
import time

from datetime import datetime, timedelta
from os import path

def load_locations(locations_file):
    with open(locations_file, "r") as in_file:
        locations = [json.loads(line) for line in in_file]
        return {loc["tiploc"]: loc for loc in locations}


def load_schedules(schedules_file):
    with open(schedules_file, "r") as in_file:
        return list([make_schedule(line) for line in in_file])


def make_schedule(line):
    s = json.loads(line)["JsonScheduleV1"]
    sched_id = s["CIF_train_uid"]
    atoc_code = s["atoc_code"] 
    start_date = s["schedule_start_date"]
    end_date = s["schedule_end_date"]
    segment = s["schedule_segment"]
    locs = segment["schedule_location"]
    orig = next((l for l in locs if l["location_type"] == "LO"))
    dest = next((l for l in locs if l["location_type"] == "LT"))
    sig_id = segment["signalling_id"]
    service_code = segment["CIF_train_service_code"]

    is_passenger = "true" if sig_id else "false"
    return {"id": sched_id,
            "trainServiceCode": service_code,
            "startDate": start_date,
            "endDate": end_date,
            "origin": orig["tiploc_code"],
            "destination": dest["tiploc_code"],
            "departure": orig["departure"][:4],
            "arrival": dest["arrival"][:4],
            "atocCode": atoc_code,
            "isPassenger": is_passenger}


def make_filtered_path(schedule_path, max_days):
    return path.dirname(schedule_path) \
        + path.sep \
        + str(max_days) + "_days_" + path.basename(schedule_path)

def write_to_file(schedules, locations, schedule_path, max_days=30):
    new_line = "\n"
    filter_file = make_filtered_path(schedule_path, max_days)
    max_date = (datetime.now() + timedelta(days=max_days + 1)).date()
    min_date = (datetime.now() + timedelta(days=-1)).date()
    with open(schedule_path, "w") as out_file:
        with open(filter_file, "w") as out_file2:
            for s in schedules:
                if s["isPassenger"] == "true":
                    s["origin"] = locations.get(s["origin"])
                    s["destination"] = locations.get(s["destination"])
                    json.dump(s, out_file)
                    out_file.write(new_line)
                    start = datetime.strptime(s["startDate"], "%Y-%m-%d").date()
                    end = datetime.strptime(s["endDate"], "%Y-%m-%d").date()
                    if (start < max_date and end > min_date):
                        json.dump(s, out_file2)
                        out_file2.write(new_line)


def main():
    print("Loading locations")
    start = time.time()
    locations = load_locations("/var/trainwatch/data/locations.json")
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Loading schedules")
    start = time.time()
    schedules = load_schedules("/var/trainwatch/data/schedules.json")
    print("Took {0:.2f}s".format(time.time() - start))
     
    print("Writing schedules with locations")
    start = time.time()
    write_to_file(schedules, locations, "/trainwatch/data/schedules_with_locations.json")
    print("Took {0:.2f}s".format(time.time() - start))


if __name__ == '__main__':
    main()
