package org.atc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Timetable {
    private Map<String, String> jobInfos;
    private Set<Job> jobs;
    private transient Layout layout;

    public Timetable(Map<String, String> jobInfos, Layout layout) {
        this.jobInfos = jobInfos;
        this.layout = layout;
        this.jobs = new HashSet<>();
    }

    public Timetable() {
        this.jobs = new HashSet<>();
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Map<String, String> getJobInfos() {
        return jobInfos;
    }

    public void setJobInfos(Map<String, String> jobIds) {
        if (this.jobInfos == null) {
            this.jobInfos = jobIds;
        } else {
            throw new IllegalArgumentException("Job IDs cannot be set at this point");
        }
    }
    public String generatePersistSqlString(Job job) {
        return "insert into job values ('" + job.getJobID() + "', '" + job.getPosition() + "');";
    }

    public boolean initializeTimetable(Dispatcher dispatcher) {
        // check if all starting positions are in the layout mileposts
        for (String jobName : jobInfos.keySet()) {
            if (!layout.getMileposts().keySet().contains(jobInfos.get(jobName))) {
                return false;
            }
            Job newJob = new Job(jobName, layout.getMileposts().get(jobInfos.get(jobName)), dispatcher);
            jobs.add(newJob);
            boolean persistSuccess = DatabaseConnector.insertJob(newJob, this);
            if (!persistSuccess) {
                return false;
            }
        }
        return true;
    }

    public Layout getLayout() {
        return layout;
    }
}
