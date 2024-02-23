package org.atc;
import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.Set;

public class Server {
    private static Dispatcher dispatcher;
    private static boolean sessionInitialized = false;

    public static void initializeSession() {
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

        get("/jobs", (request, response) -> {
            // Fetch the job IDs from the timetable
            Set<String> jobIds = dispatcher.getTimetable().getJobIds();

            // Convert the Set of job IDs to JSON
            Gson gson = new Gson();
            String jobIdsJson = gson.toJson(jobIds);

            // Set the response type to JSON
            response.type("application/json");

            // Return the job IDs as a JSON array
            return jobIdsJson;
        });
    }
}
