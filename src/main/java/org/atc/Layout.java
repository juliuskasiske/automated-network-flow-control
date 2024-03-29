package org.atc;

import javax.naming.NamingException;
import java.util.*;
import java.util.stream.Collectors;

public class Layout {
    private Set<String> milepostNumbers;
    private Map<String, Milepost> mileposts;
    private LayoutString layoutString;
    private List<Edge> edges;

    public Layout() {
        this.mileposts = new HashMap<>();
        this.layoutString = new LayoutString();
        this.milepostNumbers = new HashSet<>();
        this.edges = new ArrayList<>();
    }

    public Layout(Set<String> milepostNumbers) {
        this.mileposts = new HashMap<>();
        this.layoutString = new LayoutString();
        createMileposts(milepostNumbers);
        this.edges = new ArrayList<>();
    }

    public void createMileposts(Set<String> milepostNumbers) {
        this.milepostNumbers = milepostNumbers;
        for (String milepostNumber : milepostNumbers) {
            addMilepost(milepostNumber);
        }
    }

    private void addMilepost(String milepostNumber) {
        if (mileposts.keySet().contains(milepostNumber)) {
            throw new RuntimeException("A Milepost with the specified number already exists in the layout!");
        }
        mileposts.put(milepostNumber, new Milepost(milepostNumber));
    }

    public Map<String, Milepost> getMileposts() {
        return mileposts;
    }

    public void connect(String upstream, String downstream, int distance, int numberTracks) {
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
        mileposts.get(upstream).connectDownstream(mileposts.get(downstream), distance, numberTracks, this);
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

    public List<Milepost> findItinerary(String startMilepostNumber, String endMilepostNumber) {
        Milepost start = mileposts.get(startMilepostNumber);
        Milepost end = mileposts.get(endMilepostNumber);

        if (start == null || end == null) {
            throw new IllegalArgumentException("Both start and end mileposts must exist in the layout.");
        }

        Queue<Milepost> queue = new LinkedList<>();
        Map<Milepost, Milepost> predecessors = new HashMap<>();
        Set<Milepost> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        predecessors.put(start, null); // Start node has no predecessor
        // perform BFS considering both downstream and upstream edges
        while (!queue.isEmpty()) {
            Milepost current = queue.poll();
            if (current.equals(end)) {
                break; // Found the destination
            }
            // enqueue unvisited successors (downstream)
            for (Edge edge : current.getDownstreamEdges()) {
                Milepost successor = edge.getDownstreamNode();
                if (!visited.contains(successor)) {
                    queue.add(successor);
                    visited.add(successor);
                    predecessors.put(successor, current);
                }
            }
            // enqueue unvisited predecessors (upstream)
            for (Edge edge : current.getUpstreamEdges()) {
                Milepost predecessor = edge.getUpstreamNode();
                if (!visited.contains(predecessor)) {
                    queue.add(predecessor);
                    visited.add(predecessor);
                    predecessors.put(predecessor, current);
                }
            }
        }
        return reconstructPath(predecessors, start, end);
    }

    private List<Milepost> reconstructPath(Map<Milepost, Milepost> predecessors, Milepost start, Milepost end) {
        List<Milepost> path = new LinkedList<>();
        for (Milepost at = end; at != null; at = predecessors.get(at)) {
            path.add(0, at); // Add to the beginning of the list
        }
        // check if a path exists
        if (path.get(0).equals(start)) {
            return path;
        }
        return Collections.emptyList(); // No path found
    }

    public Milepost acquireItinerary(List<Milepost> itinerary, Job job) {
        Queue<Milepost> pollableItinerary = new LinkedList(itinerary);
        Milepost current = itinerary.get(0);
        Milepost next = itinerary.get(1);

        while (edgeAcquiredByJob(current, next, job)) {
            pollableItinerary.poll();
            current = pollableItinerary.peek();
            next = pollableItinerary.stream().skip(1).findFirst().orElse(null);
        }
        return current;
    }

    private boolean edgeAcquiredByJob(Milepost from, Milepost to, Job job) {
        Edge edgeToAcquire = findEdge(from, to);
        if (edgeToAcquire != null && edgeToAcquire.acquireTrack(job)) {
            return true;
        }
        return false;
    }

    private Edge findEdge(Milepost from, Milepost to) {
        Set<Edge> edges = from.getDownstreamEdges().stream().collect(Collectors.toSet());
        edges.addAll(from.getUpstreamEdges().stream().collect(Collectors.toSet()));

        for (Edge edge : edges) {
            if (edge.getDownstreamNode().equals(to) || edge.getUpstreamNode().equals(to)) {
                return edge;
            }
        }
        return null;
    }

    public List<Edge> getEdges() {
        return edges;
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
