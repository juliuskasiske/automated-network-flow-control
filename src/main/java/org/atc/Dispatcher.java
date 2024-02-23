package org.atc;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Dispatcher {
    private Layout layout;
    private Timetable timetable;
    private Queue<TrackWarrant> requestQueue;

    public Dispatcher(Layout layout, Timetable timetable) {
        this.layout = layout;
        this.timetable = timetable;
        this.requestQueue = new LinkedList<>();
    }

    public Layout getLayout() {
        return layout;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public Queue<TrackWarrant> getRequestQueue() {
        return requestQueue;
    }
    public TrackWarrant handleTrackWarrant(TrackWarrant requestedTrackWarrant) {
        // find itinerary needed for track warrant
        int originInt = Integer.parseInt(requestedTrackWarrant.getOrigin().toString());
        int destinationInt = Integer.parseInt(requestedTrackWarrant.getDestination().toString());
        List<Milepost> itinerary = layout.findItinerary(originInt, destinationInt);

        // traverse itinerary and acquire as long as free tracks are found
        Job requestingJob = requestedTrackWarrant.getJob();
        Milepost givenUntil = layout.acquireItinerary(itinerary, requestingJob);
        if (givenUntil.equals(requestedTrackWarrant.getOrigin())) {
            this.requestQueue.add(requestedTrackWarrant);
        } else {
            requestedTrackWarrant.setLive();
        }
        requestedTrackWarrant.setDestination(givenUntil);
        return requestedTrackWarrant;
    }

    public void approve(Milepost from, Milepost to, Job job) {
        Milepost approvedUntil = layout.acquireItinerary(layout.findItinerary(from.getMilepostNumber(), to.getMilepostNumber()), job);
        System.out.println("TrackWarrant approved until: " + approvedUntil);
    }
}
