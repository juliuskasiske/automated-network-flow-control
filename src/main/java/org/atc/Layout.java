package org.atc;

import java.util.*;
import java.util.stream.Collectors;

public class Layout {
    private Map<Integer, Milepost> mileposts;
    private LayoutString layoutString;

    public Layout() {
        this.mileposts = new HashMap<>();
        this.layoutString = new LayoutString();
    }

    public void addMilepost(int milepostNumber) {
        if (mileposts.keySet().contains(milepostNumber)) {
            throw new RuntimeException("A Milepost with the specified number already exists in the layout!");
        }
        mileposts.put(milepostNumber, new Milepost(milepostNumber));
    }

    public Map<Integer, Milepost> getMileposts() {
        return mileposts;
    }

    public void connect(int upstream, int downstream, int distance) {
        // check if both Mileposts are contained in layout
        boolean upstreamContained = mileposts.keySet().contains(upstream);
        boolean downstreamContained = mileposts.keySet().contains(downstream);

        if (!(upstreamContained && downstreamContained)) {
            throw new RuntimeException("The Mileposts are not contained in the layout");
        }

        // check if they are already connected
        boolean downstreamConnection = mileposts.get(upstream).getDownstreamEdges().contains(downstream);
        boolean upstreamConnection = mileposts.get(downstream).getUpstreamEdges().contains(upstream);

        if (downstreamConnection || upstreamConnection) {
            throw new RuntimeException("The Mileposts are already (partially) connected");
        }
        mileposts.get(upstream).connectDownstream(mileposts.get(downstream), distance);
    }

    private List<Milepost> findSources(Direction direction) {
        List<Milepost> sources = new ArrayList<>();
        for (Milepost milepost : mileposts.values()) {
            boolean emptyEdges = switch(direction) {
                case UPSTREAM -> milepost.getUpstreamEdges().isEmpty();
                case DOWNSTREAM -> milepost.getDownstreamEdges().isEmpty();
            };
            if (emptyEdges) {
                sources.add(milepost);
            }
        }
        return sources;
    }

    private String buildLine(List<Milepost> upstreamSources, String relativePrefix) {
        Set<Milepost> directDownstreams = new HashSet<>();
        for (Milepost relativeUpstreams : upstreamSources) {
            List<Edge> upstreamEdges = relativeUpstreams.getDownstreamEdges();
            for (Edge edge : upstreamEdges) {
                directDownstreams.add(edge.getDownstreamNode());
            }
        }
        if (directDownstreams.isEmpty()) {
            return relativePrefix;
        } else {
            List<Milepost> relativeDownstreams = new ArrayList<>(directDownstreams);
            relativePrefix = relativePrefix + "\n" + LayoutBuilder.spaceMileposts(relativeDownstreams);
            return buildLine(relativeDownstreams, relativePrefix);
        }
    }



    public String buildString(List<Milepost> mileposts, String runningPrefix) {
        for (Milepost milepost : mileposts) {
            // find downstream nodes of the current node
            Set<Milepost> relativeDownstreams = new HashSet<>();
            for (Edge edge : milepost.getDownstreamEdges()) {
                relativeDownstreams.add(edge.getDownstreamNode());
            }

            // return runningPrefix + the current milepost if no next downstreams
            if (relativeDownstreams.isEmpty()) {
                return runningPrefix + milepost;
            }

            // append "->" to prefix and run on downstream node if only one exists
            if (relativeDownstreams.size() == 1) {
                Milepost nextElement = relativeDownstreams.iterator().next();
                runningPrefix = runningPrefix + " -> " + nextElement;
                Set<Milepost> nextDownstreams = new HashSet<>();
                for (Edge edge : nextElement.getDownstreamEdges()) {
                    nextDownstreams.add(edge.getDownstreamNode());
                }
                return buildString(nextDownstreams.stream().collect(Collectors.toList()), runningPrefix);
            }

            // run this method for all downstreams and include line break if multiple downstreams
            if (relativeDownstreams.size() > 1) {
                runningPrefix = runningPrefix + " -> " + milepost;
                List<Milepost> relativeDownstreamsList = new ArrayList<>(relativeDownstreams);
                for (Milepost downstream : relativeDownstreamsList) {
                    List<Milepost> nextDownstreams = new ArrayList<>();
                    for (Edge edge : downstream.getDownstreamEdges()) {
                        nextDownstreams.add(edge.getDownstreamNode());
                    }
                    return buildString(nextDownstreams, runningPrefix);
                }
            }
        }
        /*
        for (Milepost milepost : lines.keySet()) {
            if (milepost.getDownstreamEdges().isEmpty()) {
                return runningPrefix + milepost;
            } else {
                Set<Milepost> relativeDownstreams = new HashSet<>();
                for (Edge edge : milepost.getDownstreamEdges()) {
                    relativeDownstreams.add(edge.getDownstreamNode());
                }
                runningPrefix = runningPrefix + " -> ";
                return buildString(mileposts, runningPrefix);
            }
        }

         */
        return null;
    }
    @Override
    public String toString() {
        List<Milepost> upstreamSources = findSources(Direction.UPSTREAM);
        return this.layoutString.buildFinalString(upstreamSources);
    }

    public String toString(boolean distanceBased) {
        List<Milepost> upstreamSources = findSources(Direction.UPSTREAM);
        return this.layoutString.buildFinalDistanceString(upstreamSources);
    }

    private enum Direction {
        UPSTREAM,
        DOWNSTREAM
    }
}
