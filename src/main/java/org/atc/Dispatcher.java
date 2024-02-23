package org.atc;

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
    public List<Milepost> approveTrackWarrant(TrackWarrant requestedTrackWarrant) {
        // find itinerary needed for track warrant
        int originInt = Integer.parseInt(requestedTrackWarrant.getOrigin().toString());
        int destinationInt = Integer.parseInt(requestedTrackWarrant.getDestination().toString());
        List<Milepost> itinerary = layout.findItinerary(originInt, destinationInt);

        // traverse itinerary and acquire as long as free tracks are found
        Job requestingJob = requestedTrackWarrant.getJob();
        return itinerary;
    }

    public void approve(Milepost from, Milepost to, Job job) {
        Milepost approvedUntil = layout.acquireItinerary(layout.findItinerary(from.getMilepostNumber(), to.getMilepostNumber()), job);
        System.out.println("TrackWarrant approved until: " + approvedUntil);
    }
}
