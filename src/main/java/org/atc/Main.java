package org.atc;
import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // define layout
        Layout layout = new Layout();
        Set<Integer> milepostNumbers = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        layout.createMileposts(milepostNumbers);

        layout.connect(1, 2, 1000);
        layout.connect(2, 3, 1000);
        layout.connect(2, 4, 500);
        layout.connect(3, 5, 1500);
        layout.connect(5, 6, 2000);
        layout.connect(4, 7, 500);
        layout.connect(7, 8, 2000);
        layout.connect(8, 9, 1000);
        layout.connect(8, 10, 1000);

        // define timetable
        Timetable timetable = new Timetable();
        Set<String> jobIds = Set.of("MISLAU-I", "LAUMIS-I", "HARSEA-L");
        timetable.setJobIds(jobIds);

        Dispatcher dispatcher = new Dispatcher(layout, timetable);
        System.out.println(layout.toString());
        Job job = new Job("MISLAU-I", "S", dispatcher);
        TrackWarrant tw = new TrackWarrant(layout.getMileposts().get(1), layout.getMileposts().get(8), job);

       // System.out.println(dispatcher.approveTrackWarrant(tw));

        dispatcher.approve(layout.getMileposts().get(4), layout.getMileposts().get(8), job);
        dispatcher.approve(layout.getMileposts().get(1), layout.getMileposts().get(8), job);
        dispatcher.approve(layout.getMileposts().get(10), layout.getMileposts().get(8), job);
        Gson gson = new Gson();
        String jsonified = gson.toJson(tw);
        System.out.println(jsonified);
    }
}