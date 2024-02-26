package org.atc;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // define layout
        Layout layout = new Layout();
        Set<String> milepostNumbers = Set.of("Portola", "MP 1", "Pier B", "MP 3", "MP 10", "MP 14",
                                        "MP 18", "Crossroad Jct.", "MP 27", "MP 34", "Barstow", "Harrison",
                                        "Roseville", "MP 135", "MP 130", "MP 116", "MP 112", "MP 109",
                                        "MP 107", "MP 105", "Phoenix");
        layout.createMileposts(milepostNumbers);
        // connect main branch 1
        layout.connect("Portola", "MP 1", 400, 16);
        layout.connect("Pier B", "MP 1", 400, 16);
        layout.connect("MP 1", "MP 3", 200, 1);
        layout.connect("MP 3", "MP 10", 400, 2);
        layout.connect("MP 10", "MP 14", 350, 2);
        layout.connect("MP 14", "Crossroad Jct.", 550, 1);
        layout.connect("Crossroad Jct.", "MP 27", 350, 1);
        layout.connect("MP 27", "MP 34", 200, 1);
        layout.connect("MP 27", "Harrison", 400, 1);
        layout.connect("MP 34", "Barstow", 400, 16);

        // connect main branch 2
        layout.connect("Roseville", "MP 135", 400, 16);
        layout.connect("MP 135", "MP 130", 250, 2);
        layout.connect("MP 130", "Crossroad Jct.", 200, 2);
        layout.connect("Crossroad Jct.", "MP 116", 700, 1);
        layout.connect("MP 116", "MP 112", 600, 2);
        layout.connect("MP 112", "MP 109", 400, 1);
        layout.connect("MP 109", "MP 107", 200, 3);
        layout.connect("MP 107", "MP 105", 100, 1);
        layout.connect("MP 105", "Phoenix", 400, 16);

        Dispatcher dispatcher = new Dispatcher(layout);

        // define timetable
        Map<String, String> jobInfos = new HashMap();
        jobInfos.put("MISLAU-C", "Phoenix");
        jobInfos.put("LAUMIS-L", "Barstow");
        jobInfos.put("LOGHAR-L", "Harrison");
        jobInfos.put("WLCHAR-L", "Portola");

        Timetable timetable = new Timetable(jobInfos, layout);



        // test DB connection


        System.out.println(layout.toString());
        //Job job = new Job("MISLAU-I", "S", dispatcher);
        //TrackWarrant tw = new TrackWarrant(layout.getMileposts().get("MP 1"), layout.getMileposts().get("Crossroad Jct."), job);

       // System.out.println(dispatcher.approveTrackWarrant(tw));
        /*
        dispatcher.approve(layout.getMileposts().get(4), layout.getMileposts().get(8), job);
        dispatcher.approve(layout.getMileposts().get(1), layout.getMileposts().get(8), job);
        dispatcher.approve(layout.getMileposts().get(10), layout.getMileposts().get(8), job);

         */
        Gson gson = new Gson();
        //String jsonified = gson.toJson(tw);
        //System.out.println(jsonified);
    }
}