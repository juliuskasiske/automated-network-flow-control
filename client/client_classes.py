import uuid
from crew_client import request_track_warrant

class TrackWarrant:
    class Status:
        PENDING = "PENDING"
        DENIED = "DENIED"
        LIVE = "LIVE"
        COMPLETED = "COMPLETED"

    live_id_counter = 1

    def __init__(self, origin, destination, job=None):
        self.origin = origin
        self.destination = destination
        self.job = job.to_dict()
        self.status = self.Status.PENDING
        self.request_id = uuid.uuid4()
        self._live_id = None  # Marked as private by convention

    def to_dict(self):
        return {
            "requestId": str(self.request_id),
            "origin": self.origin.to_dict() if hasattr(self.origin, 'to_dict') else self.origin,
            "destination": self.destination.to_dict() if hasattr(self.destination, 'to_dict') else self.destination,
            "status": self.status,
            "job": self.job.to_dict() if hasattr(self.job, 'to_dict') else self.job,
        }


class Job:
    class Status:
        PENDING = "PENDING"
        ACTIVE = "ACTIVE"
        COMPLETED = "COMPLETED"

    def __init__(self, job_id, liable_crew_name):
        self.job_id = job_id
        self.liable_crew_name = liable_crew_name
        self.track_warrant_history = []
        self.status = self.Status.PENDING

    def request_track_warrant(self, origin, destination):
        # Assuming TrackWarrant is already defined and adapted to Python
        requested_track_warrant = TrackWarrant(origin, destination, self)
        request_track_warrant(request_track_warrant)
    
    def to_dict(self):
        return {
            "jobId": self.job_id,
            "liableCrewName": self.liable_crew_name,
            "status": self.status,
        }

class Milepost:
    def __init__(self, milepost_number: int):
        self.milepost_number = milepost_number

    def to_dict(self):
        return {
            "milepostNumber": self.milepost_number
        }
