package org.atc;

import java.util.ArrayList;
import java.util.List;

public abstract class LayoutBuilder {
    private static final int LINELENGTH = 20;
    public static String spaceMileposts(List<Milepost> mileposts) {
        int segmentSize = LINELENGTH / (mileposts.size() + 1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < segmentSize; i++) {
            builder.append(" ");
        }
        for (Milepost milepost : mileposts) {
            builder.append(milepost);
            for (int i = 0; i < segmentSize; i++) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
