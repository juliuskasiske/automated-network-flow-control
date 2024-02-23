package org.atc;

import java.util.ArrayList;
import java.util.List;

public class Job {
    public enum Status {
        PENDING,
        ACTIVE,
        COMPLETED
    }
    private String jobID;
    private String liableCrewName;
    private transient List<TrackWarrant> trackWarrantHistory;
    private Status status;
    private transient Dispatcher dispatcher;

    public Job(String jobID, String liableCrewName, Dispatcher dispatcher) {
        if (dispatcher.getTimetable().getJobIds().contains(jobID)) {
            this.jobID = jobID;
        } else {
            throw new IllegalStateException("The timetable does not contain jobId: " + jobID);
        }
        this.liableCrewName = liableCrewName;
        this.dispatcher = dispatcher;
        trackWarrantHistory = new ArrayList<>();
        status = Status.PENDING;
    }

    public void requestTrackWarrant(Milepost origin, Milepost destination) {
        TrackWarrant requestedTrackWarrant = new TrackWarrant(origin, destination, this);
        dispatcher.getRequestQueue().add(requestedTrackWarrant);
    }


    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        if (dispatcher.getTimetable().getJobIds().contains(jobID)) {
            this.jobID = jobID;
        } else {
            throw new IllegalStateException("The timetable does not contain jobId: " + jobID);
        }
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
