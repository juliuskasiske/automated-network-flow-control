package org.atc;
import java.util.*;
import java.util.stream.Collectors;

public class Milepost implements SQLEntity{
    private String milepostNumber;
    private transient List<Edge> downstreamEdges;
    private transient List<Edge> upstreamEdges;

    public Milepost(String milepostNumber) {
        this.milepostNumber = milepostNumber;
        this.downstreamEdges = new ArrayList<>();
        this.upstreamEdges = new ArrayList<>();
    }

    @Override
    public String getInsertStatement() {
        return "insert into milepost values ('" + this.getMilepostNumber() + "');";
    }

    public String getMilepostNumber() {
        return milepostNumber;
    }

    public void setMilepostNumber(String milepostNumber) {
        this.milepostNumber = milepostNumber;
    }

    public List<Edge> getDownstreamEdges() {
        return downstreamEdges;
    }

    public void setDownstreamEdges(List<Edge> downstreamEdges) {
        this.downstreamEdges = downstreamEdges;
    }

    public List<Edge> getUpstreamEdges() {
        return upstreamEdges;
    }

    public void setUpstreamEdges(List<Edge> upstreamEdges) {
        this.upstreamEdges = upstreamEdges;
    }

    public void connectDownstream(Milepost downstreamNode, int distance, int numberTracks, Layout layout) {
        Edge newEdge = new Edge(distance, this, downstreamNode, numberTracks);
        layout.getEdges().add(newEdge);
        this.getDownstreamEdges().add(newEdge);
        downstreamNode.getUpstreamEdges().add(newEdge);
    }

    public List<Milepost> getUniqueSuccessors() {
        Set<Milepost> relativeDownstreams = new HashSet<>();
        for (Edge edge : this.getDownstreamEdges()) {
            relativeDownstreams.add(edge.getDownstreamNode());
        }
        return relativeDownstreams.stream().collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milepost milepost = (Milepost) o;
        return milepostNumber.equals(milepost.milepostNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(milepostNumber);
    }

    @Override
    public String toString() {
        return String.valueOf(this.milepostNumber);
    }
}
