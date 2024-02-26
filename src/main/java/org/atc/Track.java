package org.atc;

public class Track implements SQLEntity {
    private Job occupant;
    private int trackLength;
    private Edge edge;

    public Track(Job occupant, Edge edge, int trackLength) {
        this.occupant = occupant;
        this.edge = edge;
        this.trackLength = trackLength;
    }

    public boolean isEmpty() {
        return this.occupant == null;
    }

    @Override
    public String getInsertStatement() {
        String edge = String.valueOf(this.edge.getId());
        String jobId = getOccupant() == null ? "null" : getOccupant().getJobID();
        String statement =  "insert into track(edge, trackLength, occupied)  values (" +
                edge + ", " +
                this.trackLength + ", " +
                jobId + ");";
        return statement;
    }

    public Job getOccupant() {
        return occupant;
    }

    public void setOccupant(Job occupant) {
        this.occupant = occupant;
    }
}
