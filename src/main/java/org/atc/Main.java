package org.atc;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Layout layout = new Layout();

        for (int i = 1; i <= 10; i++) {
            layout.addMilepost(i);
        }

        layout.connect(1, 2, 1000);
        layout.connect(2, 3, 1000);
        layout.connect(2, 4, 500);
        layout.connect(3, 5, 1500);
        layout.connect(5, 6, 2000);
        layout.connect(4, 7, 500);
        layout.connect(7, 8, 2000);
        layout.connect(7, 5, 2000);

        layout.connect(8, 9, 1000);
        layout.connect(8, 10, 1000);
       // System.out.println(layout.getMileposts().get(2).getDownstreamEdges());
        System.out.println(layout.toString(true));
        System.out.println(layout.getMileposts().get(8).getDownstreamEdges());

    }
}