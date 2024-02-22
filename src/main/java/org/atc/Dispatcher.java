package org.atc;

import java.util.LinkedList;
import java.util.Queue;

public class Dispatcher {
    private Layout layout;
    private Queue<TrackWarrant> requestQueue;

    public Dispatcher(Layout layout) {
        this.layout = layout;
        this.requestQueue = new LinkedList<>();
    }
}
