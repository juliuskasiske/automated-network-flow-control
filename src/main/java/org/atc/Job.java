package org.atc;

import java.util.ArrayList;
import java.util.List;

public class Job {
    public enum Status {
        PENDING,
        ACTIVE,
        COMPLETED
    }
    private Timetable.JobId jobID;
    private String liableCrewName;
    private List<TrackWarrant> trackWarrantHistory;
    private Status status;
    private Dispatcher dispatcher;

    public Job(Timetable.JobId jobID, String liableCrewName, Dispatcher dispatcher) {
        this.jobID = jobID;
        this.liableCrewName = liableCrewName;
        this.dispatcher = dispatcher;
        trackWarrantHistory = new ArrayList<>();
        status = Status.PENDING;
    }

    public void requestTrackWarrant(Milepost origin, Milepost destination) {
        TrackWarrant requestedTrackWarrant = new TrackWarrant(origin, destination, this);
        dispatcher.getRequestQueue().add(requestedTrackWarrant);
    }




    public Timetable.JobId getJobID() {
        return jobID;
    }

    public void setJobID(Timetable.JobId jobID) {
        this.jobID = jobID;
    }

    public String getLiableCrewName() {
        return liableCrewName;
    }

    public void setLiableCrewName(String liableCrewName) {
        this.liableCrewName = liableCrewName;
    }

    public List<TrackWarrant> getTrackWarrantHistory() {
        return trackWarrantHistory;
    }

    public void setTrackWarrantHistory(List<TrackWarrant> trackWarrantHistory) {
        this.trackWarrantHistory = trackWarrantHistory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
