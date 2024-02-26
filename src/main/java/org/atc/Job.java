package org.atc;

import java.util.ArrayList;
import java.util.List;

public class Job implements SQLEntity {
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
    private Milepost position;

    public Job(String jobID, Milepost position) {
        this.jobID = jobID;
        this.position = position;
        trackWarrantHistory = new ArrayList<>();
        status = Status.PENDING;
    }

    public String getPositionUpdateStatement() {
        return "UPDATE job SET position = '" + this.position + "' WHERE jobId = '" + this.jobID + "';";
    }

    @Override
    public String getInsertStatement() {
        return "insert into job values ('" + this.getJobID() + "', '" + this.getPosition() + "');";
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
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

    public Milepost getPosition() {
        return position;
    }

    public void setPosition(Milepost position, Layout layout) {
        if (layout.getMileposts().values().contains(position)) {
            this.position = position;
        }
    }
}

