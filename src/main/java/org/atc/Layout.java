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
        boolean downstreamConnection = mileposts.get(upstream).getDownstreamEdges().contains(mileposts.get(downstream));
        boolean upstreamConnection = mileposts.get(downstream).getUpstreamEdges().contains(mileposts.get(upstream));

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
