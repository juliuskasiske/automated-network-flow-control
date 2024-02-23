package org.atc;

import java.util.UUID;

public class TrackWarrant {
    public enum Status {
        PENDING,
        DENIED,
        LIVE,
        COMPLETED;
    }
    private UUID requestId;
    private static int liveIdCounter = 1;
    private transient int liveId;
    private Milepost origin;
    private Milepost destination;
    private Status status;
    private Job job;

    /**
     * When a request is initiated by crews, status is set to pending (inactive)
     * @param origin current position of crew
     * @param destination desired destination covered by the requested track warrant
     */
    public TrackWarrant(Milepost origin, Milepost destination, Job job) {
        this.origin = origin;
        this.destination = destination;
        this.job = job;
        this.status = Status.PENDING;
        this.requestId = UUID.randomUUID();
    }

    /**
     * Used when the track warrant is initiated by the dispatcher
     * @param status whatever status the dispatcher gives the warrant
     * @param origin current position of crew
     * @param destination destination covered by the track warrant
     */
    public TrackWarrant(Status status, Milepost origin, Milepost destination) {
        this.origin = origin;
        this.destination = destination;
        this.status = status;
    }

    public void setLive() {
        status = Status.LIVE;
        liveId = liveIdCounter++;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static int getLiveIdCounter() {
        return liveIdCounter;
    }

    public int getLiveId() {
        return liveId;
    }

    public Milepost getOrigin() {
        return origin;
    }

    public Milepost getDestination() {
        return destination;
    }

    public Status getStatus() {
        return status;
    }

    public Job getJob() {
        return job;
    }

    public void setDestination(Milepost destination) {
        this.destination = destination;
    }
}
