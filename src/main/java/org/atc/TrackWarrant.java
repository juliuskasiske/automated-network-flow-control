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
    private static int liveIdCounter = 0;
    private int liveId;
    private Milepost origin;
    private Milepost destination;
    private Status status;

    /**
     * When a request is initiated by crews, status is set to pending (inactive)
     * @param origin current position of crew
     * @param destination desired destination covered by the requested track warrant
     */
    public TrackWarrant(Milepost origin, Milepost destination) {
        this.origin = origin;
        this.destination = destination;
        this.status = Status.PENDING;
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
    }
}
