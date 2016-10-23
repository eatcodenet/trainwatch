import json
import time


def load_locations(data_file):
    with open(data_file, "r") as loc_file:
        locations = json.load(loc_file)["locations"]
        return {loc["tiploc"]: loc for loc in locations}


def load_schedules(data_file):
    with open(data_file, "r") as sched_file:
        return list([make_schedule(line) for line in sched_file])


def make_schedule(line):
    s = json.loads(line)["JsonScheduleV1"]
    locs = s["schedule_segment"]["schedule_location"]
    s_id = s["CIF_train_uid"]
    orig = next((l for l in locs if l["location_type"] == "LO"))
    dest = next((l for l in locs if l["location_type"] == "LT"))
    start_date = s["schedule_start_date"]
    end_date = s["schedule_end_date"]
    run_days = s["schedule_days_runs"]
    sig_id = s["schedule_segment"]["signalling_id"]
    is_passenger = "true" if sig_id else "false"
    service_code = s["schedule_segment"]["CIF_train_service_code"]
    atoc_code = ""
    return {"id": s_id, "startDate": start_date, "endDate": end_date,
            "origin": orig["tiploc_code"],
            "destination": dest["tiploc_code"], "departure": orig["departure"],
            "arrival": dest["arrival"],
            "runDays": run_days, "trainServiceCode": service_code,
            "atocCode": atoc_code, "isPassenger": is_passenger}


def write_to_file(schedules, locations):
    with open("/var/trainwatch/data/schedules_with_locations.json",
              "w") as out:
        for s in schedules:
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
