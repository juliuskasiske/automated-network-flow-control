package org.atc;

import java.util.Set;

public class Timetable {
    public enum JobId {
        M_MISLAU,
        M_LAUMIS,
        I_PORROS,
        L_HARGAR,
        I_SEAROS
    }
    Set<Job> jobs;

    public Timetable(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Set<Job> getJobs() {
        return jobs;
    }
}
