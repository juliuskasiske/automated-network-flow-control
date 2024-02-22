package org.atc;

import java.util.*;

public class LayoutString {
    private static final int DASH_MEASURE = 500;
    private List<StringBuilder> lines;


    public LayoutString() {
        this.lines = new ArrayList<>();
    }

    public List<StringBuilder> getLines() {
        return lines;
    }

    public void setLines(List<StringBuilder> lines) {
        this.lines = lines;
    }

    public String buildFinalString(List<Milepost> upstreamSources) {
        for (Milepost source : upstreamSources) {
            StringBuilder builder = new StringBuilder();
            lines.add(builder);
            buildStrings(source, builder);
        }

        String returnString = "";
        for (StringBuilder builder : lines) {
            returnString = returnString + builder.toString() + "\n";
        }
        return returnString;
    }

    public String buildFinalDistanceString(List<Milepost> upstreamSources) {
        for (Milepost source : upstreamSources) {
            StringBuilder builder = new StringBuilder();
            lines.add(builder);
            buildStringsDistance(source, builder);
        }

        String returnString = "";
        for (StringBuilder builder : lines) {
            returnString = returnString + builder.toString() + "\n";
        }
        return returnString;
    }

    private void buildStrings(Milepost milepost, StringBuilder currentLine) {
        List<Milepost> successors = milepost.getUniqueSuccessors();

        if (successors.isEmpty()) {
            currentLine.append(milepost);
            return;
        }
        currentLine.append(milepost + " -> ");
        Map<Milepost, StringBuilder> linesToCreate = new HashMap<>();

        for (int i = 1; i < successors.size(); i++) {
            StringBuilder newBuilder = new StringBuilder();
            newBuilder.append(" ".repeat(currentLine.length() - 5));
            newBuilder.append(milepost + " -> ");
            linesToCreate.put(successors.get(i), newBuilder);
        }
        buildStrings(successors.get(0), currentLine);
        for (Milepost nextMilepost : linesToCreate.keySet()) {
            buildStrings(nextMilepost, linesToCreate.get(nextMilepost));
            lines.add(linesToCreate.get(nextMilepost));
        }
    }

    private void buildStringsDistance(Milepost milepost, StringBuilder currentLine) {
        List<Edge> downstreamEdges = milepost.getDownstreamEdges();
        if (downstreamEdges.isEmpty()) {
            currentLine.append(milepost);
            return;
        }
        Map<Milepost, StringBuilder> linesToCreate = new HashMap<>();
        for (int i = 1; i < downstreamEdges.size(); i++) {
            Edge currentEdge = downstreamEdges.get(i);
            StringBuilder newBuilder = new StringBuilder();
            newBuilder.append(" ".repeat(currentLine.length()));
            newBuilder = appendWithDistance(currentEdge, newBuilder);
            linesToCreate.put(currentEdge.getDownstreamNode(), newBuilder);
        }
        currentLine = appendWithDistance(downstreamEdges.get(0), currentLine);
        buildStringsDistance(downstreamEdges.get(0).getDownstreamNode(), currentLine);
        for (Milepost nextMilepost : linesToCreate.keySet()) {
            buildStringsDistance(nextMilepost, linesToCreate.get(nextMilepost));
            lines.add(linesToCreate.get(nextMilepost));
        }
    }

    private StringBuilder appendWithDistance(Edge currentEdge, StringBuilder currentLine) {
        currentLine.append(currentEdge.getUpstreamNode());
        int edgeLength = currentEdge.getLength();
        int repitions = edgeLength >= DASH_MEASURE ? edgeLength / DASH_MEASURE : 1;
        currentLine.append(" " + "-".repeat(repitions) + "> ");
        return currentLine;
    }
}
