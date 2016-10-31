import json
import time


def load_locations(locations_file):
    with open(locations_file, "r") as in_file:
        locations = [json.loads(line) for line in in_file]
        return {loc["tiploc"]: loc for loc in locations}


def load_schedules(schedules_file):
    with open(schedules_file, "r") as in_file:
        return list([make_schedule(line) for line in in_file])


def make_schedule(line):
    s = json.loads(line)["JsonScheduleV1"]
    s_id = s["CIF_train_uid"]
    locs = s["schedule_segment"]["schedule_location"]
    orig = next((l for l in locs if l["location_type"] == "LO"))
    dest = next((l for l in locs if l["location_type"] == "LT"))
    start_date = s["schedule_start_date"]
    end_date = s["schedule_end_date"]
    sig_id = s["schedule_segment"]["signalling_id"]
    service_code = s["schedule_segment"]["CIF_train_service_code"]
    atoc_code = s["atoc_code"] 
    is_passenger = "true" if sig_id else "false"
    return {"id": s_id,
            "trainServiceCode": service_code,
            "startDate": start_date,
            "endDate": end_date,
            "origin": orig["tiploc_code"],
            "destination": dest["tiploc_code"],
            "departure": orig["departure"][:4],
            "arrival": dest["arrival"][:4],
            "atocCode": atoc_code,
            "isPassenger": is_passenger}


def write_to_file(schedules, locations):
    with open("/var/trainwatch/data/schedules_with_locations.json",
              "w") as out:
        for s in schedules:
            if s["isPassenger"] == "true":
                s["origin"] = locations.get(s["origin"])
                s["destination"] = locations.get(s["destination"])
                json.dump(s, out)
                out.write("\n")


def main():
    print("started...")
    locations = load_locations("/var/trainwatch/data/locations.json")
    start = time.time()
    schedules = load_schedules("/var/trainwatch/data/schedules.json")
    end = time.time() - start
    print("load schedules took {0:.2f}s".format(end))
    start = time.time()
    write_to_file(schedules, locations)
    end = time.time() - start
    print("write schedules took {0:.2f}s".format(end))


if __name__ == '__main__':
    main()
