package org.atc;

import javax.swing.*;
import java.util.List;

public class Node {
    private static int idCounter = 0;
    private int id;
    private String name;
    private Node[] previous;
    private Node[] subsequent;
    private Trackplan trackplan;

    public Node(String name, Node[] previous, Node[] subsequent, Trackplan trackplan) {
        this.previous = previous;
        this.subsequent = subsequent;
        this.trackplan = trackplan;
        this.id = idCounter++;
    }
}
