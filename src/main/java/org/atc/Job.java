package org.atc;

import java.util.List;

public class Job {
    public enum Status {
        PENDING,
        ACTIVE,
        COMPLETED
    }
    private String jobID;
    private String liableCrewName;
    private List<TrackWarrant> trackWarrantHistory;
    private Status status;


}
