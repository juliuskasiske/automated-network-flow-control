package org.atc;

public class Edge {
    private int length;
    private Milepost upstreamNode;
    private Milepost downstreamNode;

    public Edge(int length, Milepost upstreamNode, Milepost downstreamNode) {
        this.length = length;
        this.upstreamNode = upstreamNode;
        this.downstreamNode = downstreamNode;
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
}
