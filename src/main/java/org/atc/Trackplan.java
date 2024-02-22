package org.atc;

public class Trackplan {
    public enum Descriptor {
        STATION,
        SIDING,
        MILEPOST
    }
    private Descriptor descriptor;
    private short numberOfTracks;
    private int lengthInCm;

    public Trackplan(Descriptor descriptor, short numberOfTracks, int lengthInCm) {
        this.descriptor = descriptor;
        this.numberOfTracks = numberOfTracks;
        this.lengthInCm = lengthInCm;
    }

    public Descriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public short getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(short numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public int getLengthInCm() {
        return lengthInCm;
    }

    public void setLengthInCm(int lengthInCm) {
        this.lengthInCm = lengthInCm;
    }
}
