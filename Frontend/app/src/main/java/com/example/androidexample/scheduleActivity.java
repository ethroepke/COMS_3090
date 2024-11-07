package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class scheduleActivity extends AppCompatActivity {

    private CalendarView scheduleCalendar;
    private Set<String> eventDates; // To store event dates in "YYYY-MM-DD" format

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        scheduleCalendar = findViewById(R.id.scheduleCalendar);

        // Initialize the set for storing event dates
        eventDates = new HashSet<>();

        // Fetch event data from the server
        fetchEventData();

        // Set a listener for date changes
        scheduleCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Convert the selected date from CalendarView (milliseconds) to "YYYY-MM-DD" format
            String selectedDate = formatDateToString(year, month, dayOfMonth);

            // Debugging log to check the selected date
            Log.d("SelectedDate", "Selected date: " + selectedDate);

            if (eventDates.contains(selectedDate)) {
                // Debugging log to check if eventDates contains the selected date
                Log.d("EventFound", "Event found for date: " + selectedDate);
                Toast.makeText(scheduleActivity.this, "Event on: " + selectedDate, Toast.LENGTH_SHORT).show();
            } else {
                // Debugging log for when no event is found
                Log.d("EventNotFound", "No event found for date: " + selectedDate);
                Toast.makeText(scheduleActivity.this, "No event on: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch event data and process it
    private void fetchEventData() {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/schedules"; // Your API endpoint

        // Create a request to fetch the data
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray events = response.getJSONArray("events");
                            for (int i = 0; i < events.length(); i++) {
                                JSONObject event = events.getJSONObject(i);
                                String startTime = event.getString("startTime");

                                // Extract the date part (YYYY-MM-DD) from the startTime
                                String eventDate = startTime.split("T")[0];
                                eventDates.add(eventDate); // Add the date to the set

                                // Debugging log to see the event date being added
                                Log.d("EventDate", "Event date added: " + eventDate);
                            }

                            // After the events are fetched and processed, log the entire eventDates set
                            Log.d("FetchedEventDates", "Fetched event dates: " + eventDates);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Schedule", "Error processing the event data.", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Schedule", "Error fetching data.", error);
                    }
                });

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(request);
    }

    // Utility method to convert the selected date to a "YYYY-MM-DD" format
    private String formatDateToString(int year, int month, int dayOfMonth) {
        // Month is 0-based, so add 1 to get the correct month number
        month = month + 1;

        // Format the date as "YYYY-MM-DD"
        return String.format("%04d-%02d-%02d", year, month, dayOfMonth);
    }
}
