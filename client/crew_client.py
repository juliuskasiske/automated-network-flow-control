import requests, json

base_url = 'http://localhost:8080/trackwarrant'

# Example of sending a POST request to create a new track warrant
new_track_warrant = {"requestId":"f43c3294-b736-40ef-93c8-b6eccb6fb0fa",
                     "origin":{"milepostNumber":1},
                     "destination":{"milepostNumber":8},
                     "status":"PENDING",
                     "job":{"jobID":"MISLAU-I",
                            "liableCrewName":"Felix Kasiske",
                            "status":"PENDING"}}

response = requests.post(base_url, json=new_track_warrant)
if response.status_code == 200:
    print(response.json())
else:
    print(response.headers)
    print("Failed to create track warrant. Status code:", response.status_code)