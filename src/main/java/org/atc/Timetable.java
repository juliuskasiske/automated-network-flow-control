package org.atc;

import java.util.HashSet;
import java.util.Set;

public class Timetable {
    private Set<String> jobIds;
    Set<Job> jobs;

    public Timetable(Set<String> jobIds) {
        this.jobIds = jobIds;
    }

    public Timetable() {
        this.jobs = new HashSet<>();
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Set<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(Set<String> jobIds) {
        if (this.jobIds == null) {
            this.jobIds = jobIds;
        } else {
            throw new IllegalArgumentException("Job IDs cannot be set at this point");
        }

    }
}
