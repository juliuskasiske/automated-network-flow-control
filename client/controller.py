import yaml

def persist_track_warrant_data(trackwarrant_data: dict):
    with open("trackwarrant_data.yaml", "w") as file:
        yaml.safe_dump(trackwarrant_data, file)

def read_track_warrant_data():
    with open("trackwarrant_data.yaml", "r") as file:
        return yaml.safe_load(file)
        