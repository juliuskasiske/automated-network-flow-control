package org.atc;

public class Track {
    private Job occupant;

    public Track(Job occupant) {
        this.occupant = occupant;
    }

    public boolean isEmpty() {
        return this.occupant == null;
    }

    public Job getOccupant() {
        return occupant;
    }

    public void setOccupant(Job occupant) {
        this.occupant = occupant;
    }
}
