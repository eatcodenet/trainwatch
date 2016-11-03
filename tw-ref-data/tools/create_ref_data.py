import sys
import time
import network_rail_schedule as nr
import create_locations as loc
import create_schedules as sched


def check_args():
    if len(sys.argv) != 3:
        print(sys.argv[0] + " <nr username> <nr password>")
        sys.exit(0)
    return (sys.argv[1], sys.argv[2])
    
    
def main():
    start_total = start = time.time()
    schedule_url = "https://datafeeds.networkrail.co.uk/ntrod/CifFileAuthenticate?type=CIF_ALL_FULL_DAILY&day=toc-full"
    schedule_file = "/tmp/full-schedule"
    schedule_gz_file = schedule_file + ".gz"
    schedules_json = "/var/trainwatch/data/schedules.json"
    tiplocs_json = "/var/trainwatch/data/tiplocs.json"
    stations_url = "https://raw.githubusercontent.com/fasteroute/national-rail-stations/master/stations.json"
    stations_json = "/var/trainwatch/data/stations.json"
    (username, password) = check_args()
    
    ### download files
    print("Downloading stations")
    start = time.time()
    nr.download_file(stations_url, stations_json, "", "")
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Downloading schedule file")
    start = time.time()
    nr.download_file(schedule_url, schedule_gz_file, username, password)
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Unzipping schedule file")
    start = time.time()
    nr.unzip(schedule_gz_file, schedule_file)
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Extracting schedules and tiplocs")
    nr.extract_schedules_and_tiplocs(schedule_file, schedules_json, tiplocs_json)
    print("Took {0:.2f}s".format(time.time() - start))
    
    ### now create locations
    print("\nLoading tiplocs")
    start = time.time()
    tiplocs = loc.tiplocs_map(tiplocs_json)
    print("len tiplocs", len(tiplocs))

    print("Loading stations")
    stations = loc.stations_map(stations_json)
    print("len stations", len(stations))
    print("Took {0:.2f}s".format(time.time() - start))

    print("Creating locations")
    start = time.time()
    locations = loc.create_locations(tiplocs, stations)
    print("len locations", len(locations))
    print("Took {0:.2f}s".format(time.time() - start))

    print("Writing locations to file")
    start = time.time()
    locations_json = "/var/trainwatch/data/locations.json"
    loc.write_locations(locations_json, locations)
    print("Took {0:.2f}s".format(time.time() - start))

    ### now create schedules with locations
    print("Loading locations")
    start = time.time()
    locations = sched.load_locations("/var/trainwatch/data/locations.json")
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Loading schedules")
    start = time.time()
    schedules = sched.load_schedules("/var/trainwatch/data/schedules.json")
    print("Took {0:.2f}s".format(time.time() - start))
     
    print("Writing schedules with locations")
    start = time.time()
    sched.write_to_file(schedules, locations, "/var/trainwatch/data/schedules_with_locations.json")
    print("Took {0:.2f}s".format(time.time() - start))
    
    print("Finished ALL processing. Took {0:.2f}s".format(time.time() - start_total))

if __name__ == '__main__':
    main()

