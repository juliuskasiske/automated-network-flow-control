import requests, json

base_url = 'http://localhost:8080/'

# Example of sending a POST request to create a new track warrant
new_track_warrant = {"requestId":"f43c3294-b736-40ef-93c8-b6eccb6fb0fa",
                     "origin":{"milepostNumber":1},
                     "destination":{"milepostNumber":8},
                     "status":"PENDING",
                     "job":{"jobId":"MISLAU-I",
                            "liableCrewName":"Felix Kasiske",
                            "status":"PENDING"}}

def request_track_warrant(trackwarrant):
    tw_dict = trackwarrant.to_dict()
    response = requests.post(base_url + "trackwarrant", json=tw_dict)
    if response.status_code == 200:
        return response.json()
    else:
        print("Failed to create track warrant. Status code:" + str(response.status_code))
        return response.status_code

def get_job_numbers():
    response = requests.get(base_url + "jobs")
    return list(response.json())

