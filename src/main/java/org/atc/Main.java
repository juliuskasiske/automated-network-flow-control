package org.atc;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Layout layout = new Layout();

        for (int i = 1; i <= 10; i++) {
            layout.addMilepost(i);
        }

        layout.connect(1, 2, 5);
        layout.connect(2, 3, 10);
        layout.connect(2, 4, 5);
        layout.connect(3, 5, 8);
        layout.connect(5, 6, 2);
        layout.connect(4, 7, 5);
        layout.connect(7, 8, 10);
        layout.connect(8, 9, 5);
        layout.connect(8, 10, 5);

        System.out.println(layout);


    }
}