package org.atc;
import java.util.*;
import java.util.stream.Collectors;

public class Milepost {
    private int milepostNumber;
    private List<Edge> downstreamEdges;
    private List<Edge> upstreamEdges;

    public Milepost(int milepostNumber) {
        this.milepostNumber = milepostNumber;
        this.downstreamEdges = new ArrayList<>();
        this.upstreamEdges = new ArrayList<>();
    }

    public int getMilepostNumber() {
        return milepostNumber;
    }

    public void setMilepostNumber(int milepostNumber) {
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

    public void connectDownstream(Milepost downstreamNode, int distance) {
        Edge newEdge = new Edge(distance, this, downstreamNode);
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
        return milepostNumber == milepost.milepostNumber;
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
