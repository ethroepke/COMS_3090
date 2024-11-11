package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scheduleActivity extends AppCompatActivity {

    private CalendarView scheduleCalendar;
    private TextView eventNameTextView;
    private TextView eventTimeTextView;
    private Map<String, List<String>> eventDetails; // Map to store event details by date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        // Initialize views for event details
        eventNameTextView = findViewById(R.id.eventName);
        eventTimeTextView = findViewById(R.id.eventTime);

        scheduleCalendar = findViewById(R.id.scheduleCalendar);

        // Initialize the map for storing event details
        eventDetails = new HashMap<>();

        // Fetch event data from the server
        fetchEventData();

        // Set a listener for date changes
        scheduleCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Convert the selected date from CalendarView (milliseconds) to "YYYY-MM-DD" format
            String selectedDate = formatDateToString(year, month, dayOfMonth);

            // Debugging log to check the selected date
            Log.d("SelectedDate", "Selected date: " + selectedDate);

            // Check if the selected date has events
            if (eventDetails.containsKey(selectedDate)) {
                List<String> events = eventDetails.get(selectedDate);
                StringBuilder eventMessage = new StringBuilder("Events on " + selectedDate + ":\n");

                // Append all event details for that date
                for (String event : events) {
                    // Assuming event format "EventType from StartTime to EndTime"
                    String[] eventParts = event.split(" from ");
                    String eventName = eventParts[0];  // Event name
                    String eventTime = eventParts[1];  // Time range (from start to end)

                    // Extract only the time part (HH:mm) from the start and end time
                    String startTime = formatTime(eventTime.split(" to ")[0]); // Get start time (HH:mm)
                    String endTime = formatTime(eventTime.split(" to ")[1]);   // Get end time (HH:mm)

                    // Set event name and time (only time, not date) in the TextViews
                    eventNameTextView.setText("Event Name: " + eventName);
                    eventTimeTextView.setText("Event Time: " + startTime + " to " + endTime);
                }
            } else {
                // If no events are found for the selected date, show N/A
                eventNameTextView.setText("Event Name: N/A");
                eventTimeTextView.setText("Event Time: N/A");
            }
        });
    }

    // Method to fetch event data and process it
    private void fetchEventData() {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/schedules"; // Your API endpoint

        // Create a request to fetch the data (JsonArrayRequest because the response is an array)
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through each event in the response array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject event = response.getJSONObject(i);

                                // Extract event details: startTime, endTime, and eventType
                                String startTime = event.getString("startTime");
                                String endTime = event.getString("endTime");
                                String eventType = event.getString("eventType");

                                // Extract the date part (YYYY-MM-DD) from the startTime
                                String eventDate = startTime.split("T")[0];

                                // Format the event details
                                String eventInfo = eventType + " from " + startTime + " to " + endTime;

                                // Store the event details in the map by date
                                if (!eventDetails.containsKey(eventDate)) {
                                    eventDetails.put(eventDate, new ArrayList<>());
                                }
                                eventDetails.get(eventDate).add(eventInfo);

                                // Debugging log to verify the event date and details being added
                                Log.d("EventDate", "Event date added: " + eventDate + " with details: " + eventInfo);
                            }

                            // After the events are fetched and processed, log the entire eventDetails map
                            Log.d("FetchedEventDetails", "Fetched event details: " + eventDetails);
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

        // Add the request to the request queue for Volley to process
        Volley.newRequestQueue(this).add(request);
    }

    // Utility method to convert the selected date to a "YYYY-MM-DD" format
    private String formatDateToString(int year, int month, int dayOfMonth) {
        // Month is 0-based, so add 1 to get the correct month number
        month = month + 1;

        // Format the date as "YYYY-MM-DD"
        return String.format("%04d-%02d-%02d", year, month, dayOfMonth);
    }

    // Method to format the time part (HH:mm) from the full datetime string
    private String formatTime(String datetime) {
        try {
            // Create a SimpleDateFormat object to parse the datetime
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            // Parse the datetime string
            java.util.Date date = inputFormat.parse(datetime);

            // Now format the date object to extract only the time (HH:mm)
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            return outputFormat.format(date);  // Return the formatted time
        } catch (ParseException e) {
            e.printStackTrace();
            return "";  // Return empty string if error occurs
        }
    }
}
