package org.atc;

import java.util.*;

public class LayoutString {
    List<StringBuilder> lines;


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
    
}
