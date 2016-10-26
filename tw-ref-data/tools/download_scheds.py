#!/usr/bin/env python3
import requests
import sys
import time
from requests.auth import HTTPBasicAuth

SCHEDULE_URL = "https://datafeeds.networkrail.co.uk/ntrod/CifFileAuthenticate?type=CIF_ALL_FULL_DAILY&day=toc-full"

def download(url, username, password):
    start = time.time()
    r = requests.get(url, stream=True, auth=HTTPBasicAuth(username, password))
    print("length {0}".format(r.headers['content-length']))
    with open("/tmp/scheds.json", 'w') as f:
        for chunk in r.iter_content(chunk_size=1024 * 1024): 
            if chunk:  # filter out keep-alive new chunks
                f.write(chunk)
    return time.time() - start

def main():
    username = sys.argv[1]
    password = sys.argv[2]
    print("Downloading schedules")
    time_taken = download(SCHEDULE_URL, username, password)
    print("Took {0:.2f}s".format(time_taken))

if __name__ == '__main__':
    main()
