package org.atc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Edge {
    private int length;
    private transient Milepost upstreamNode;
    private transient Milepost downstreamNode;
    private List<Track> tracks;

    public Edge(int length, Milepost upstreamNode, Milepost downstreamNode) {
        this.length = length;
        this.upstreamNode = upstreamNode;
        this.downstreamNode = downstreamNode;
        this.tracks = Arrays.asList(new Track(null));

    }
    public Edge(int length, Milepost upstreamNode, Milepost downstreamNode, int numberTracks) {
        this.length = length;
        this.upstreamNode = upstreamNode;
        this.downstreamNode = downstreamNode;
        this.tracks = new ArrayList<>();
        for (int i = 0; i < numberTracks; i++) {
            tracks.add(new Track(null));
        }
    }

    public boolean acquireTrack(Job job) {
        for (Track track : tracks) {
           if (track.isEmpty()) {
               track.setOccupant(job);
               return true;
           }
        }

        return false;
    }

    public boolean releaseTrack(Job job) {
        for (Track track : tracks) {
            if (track.getOccupant().getJobID() == job.getJobID()) {
                track.setOccupant(null);
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Milepost getUpstreamNode() {
        return upstreamNode;
    }

    public void setUpstreamNode(Milepost upstreamNode) {
        this.upstreamNode = upstreamNode;
    }

    public Milepost getDownstreamNode() {
        return downstreamNode;
    }

    public void setDownstreamNode(Milepost downstreamNode) {
        this.downstreamNode = downstreamNode;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "length=" + length +
                ", upstreamNode=" + upstreamNode +
                ", downstreamNode=" + downstreamNode +
                '}';
    }
}
