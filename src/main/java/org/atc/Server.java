package org.atc;
import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    private static Dispatcher dispatcher;
    private static boolean sessionInitialized = false;

    public static void initializeSession() {
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

        // define timetable
        Timetable timetable = new Timetable();
        Set<String> jobIds = Set.of("MISLAU-I", "LAUMIS-I", "HARSEA-L");
        timetable.setJobIds(jobIds);

        dispatcher = new Dispatcher(layout, timetable);

        sessionInitialized = true;
    }

    public static void main(String[] args) {
        if (!sessionInitialized) {
            initializeSession();
        }
        port(8080); // Spark will run on port 8080

        post("/trackwarrant", (request, response) -> {
            // Parse TrackWarrant from request
            Gson gson = new Gson();
            TrackWarrant trackWarrant = gson.fromJson(request.body(), TrackWarrant.class);
            // process the TrackWarrant object and return
            TrackWarrant responseTrackWarrant = dispatcher.handleTrackWarrant(trackWarrant);
            response.type("application/json");
            return gson.toJson(responseTrackWarrant);
        });

        get("/meta", (request, response) -> {
            Map<String, Set<String>> bothSets = new HashMap<>();
            Set<String> jobIds = dispatcher.getTimetable().getJobIds();
            Set<String> nodes = dispatcher.getLayout().getMileposts().keySet();

            bothSets.put("jobIds", jobIds);
            bothSets.put("nodes", nodes);

            // Convert to JSON
            Gson gson = new Gson();
            String bothSetsJson = gson.toJson(bothSets);

            // Set the response type to JSON
            response.type("application/json");

            // Return the job IDs as a JSON array
            return bothSetsJson;
        });
    }
}
