package org.atc;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Dispatcher {
    private Layout layout;
    private Queue<TrackWarrant> requestQueue;

    public Dispatcher(Layout layout) {
        this.layout = layout;
        this.requestQueue = new LinkedList<>();
    }

    public Layout getLayout() {
        return layout;
    }

    public Queue<TrackWarrant> getRequestQueue() {
        return requestQueue;
    }
    public TrackWarrant handleTrackWarrant(TrackWarrant requestedTrackWarrant) {
        // find itinerary needed for track warrant
        String origin = requestedTrackWarrant.getOrigin().toString();
        String destination = requestedTrackWarrant.getDestination().toString();
        List<Milepost> itinerary = layout.findItinerary(origin, destination);

        // traverse itinerary and acquire as long as free tracks are found
        Job requestingJob = requestedTrackWarrant.getJob();
        Milepost givenUntil = layout.acquireItinerary(itinerary, requestingJob);
        if (givenUntil.equals(requestedTrackWarrant.getOrigin())) {
            this.requestQueue.add(requestedTrackWarrant);
            requestedTrackWarrant.setDenied();
        } else {
            requestedTrackWarrant.setDestination(givenUntil);
            requestedTrackWarrant.setLive();
        }

        return requestedTrackWarrant;
    }

    public List<Milepost> findItinerary(String originMilePostNumber, String destinationMilePostNumber) {
        return layout.findItinerary(originMilePostNumber, destinationMilePostNumber);
    }

    public void approve(Milepost from, Milepost to, Job job) {
        Milepost approvedUntil = layout.acquireItinerary(layout.findItinerary(from.getMilepostNumber(), to.getMilepostNumber()), job);
        System.out.println("TrackWarrant approved until: " + approvedUntil);
    }
}
