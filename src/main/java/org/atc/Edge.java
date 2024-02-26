package org.atc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Edge implements SQLEntity {
    private static int idAutoIncrement = 0;
    private int id;
    private int length;
    private transient Milepost upstreamNode;
    private transient Milepost downstreamNode;
    private List<Track> tracks;

    public Edge(int length, Milepost upstreamNode, Milepost downstreamNode) {
        this.id = idAutoIncrement++;
        this.length = length;
        this.upstreamNode = upstreamNode;
        this.downstreamNode = downstreamNode;
        this.tracks = Arrays.asList(new Track(null, this, length));
    }
    public Edge(int length, Milepost upstreamNode, Milepost downstreamNode, int numberTracks) {
        this.id = idAutoIncrement++;
        this.length = length;
        this.upstreamNode = upstreamNode;
        this.downstreamNode = downstreamNode;
        this.tracks = new ArrayList<>();
        for (int i = 0; i < numberTracks; i++) {
            tracks.add(new Track(null, this, length));
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

    @Override
    public String getInsertStatement() {
        String startNode = this.getUpstreamNode().getMilepostNumber();
        String endNode = this.getDownstreamNode().getMilepostNumber();
        String numberTracks = String.valueOf(this.tracks.size());
        String statement = "insert into edge values (" +
                this.id + ", '" +
                startNode + "', '" +
                endNode + "', " +
                numberTracks + ");";
        return statement;
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

    public int getId() {
        return id;
    }

    public List<Track> getTracks() {
        return tracks;
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
