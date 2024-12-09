package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class employeeStatusActivity extends AppCompatActivity {

    private String loggedInUsername;
    private static final String BACKEND_URL = "https://example.com/api/employee_status"; // Replace with your backend URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeestatus);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarStatus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employee Status");

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        // Initialize UI components
        ScrollView scrollViewAvailability = findViewById(R.id.availabilityScroll);
        ScrollView scrollViewTimeOff = findViewById(R.id.scroll_view_time_off);

        // Load data from backend
        loadEmployeeStatus(scrollViewAvailability, scrollViewTimeOff);
    }

    private void loadEmployeeStatus(ScrollView scrollViewAvailability, ScrollView scrollViewTimeOff) {
        // Send a GET request to fetch employee statuses
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                BACKEND_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parse the JSON response
                            parseEmployeeStatus(response, scrollViewAvailability, scrollViewTimeOff);
                        } catch (JSONException e) {
                            Log.e("EmployeeStatus", "JSON Parsing error: " + e.getMessage());
                            Toast.makeText(employeeStatusActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("EmployeeStatus", "Volley error: " + error.getMessage());
                        Toast.makeText(employeeStatusActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void parseEmployeeStatus(JSONArray response, ScrollView scrollViewAvailability, ScrollView scrollViewTimeOff) throws JSONException {
        // Initialize containers for availability and time-off data
        LinearLayout availabilityLayout = new LinearLayout(this);
        availabilityLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout timeOffLayout = new LinearLayout(this);
        timeOffLayout.setOrientation(LinearLayout.VERTICAL);

        // Iterate through the JSON array
        for (int i = 0; i < response.length(); i++) {
            JSONObject employee = response.getJSONObject(i);

            // Extract employee details
            String name = employee.getString("name");
            String status = employee.getString("status"); // Available, On Leave, or Time Off Requested
            String details = employee.optString("details", ""); // Optional additional details

            // Create a TextView for each employee's status
            TextView employeeStatus = new TextView(this);
            employeeStatus.setText(name + ": " + status);
            employeeStatus.setTextSize(16);
            employeeStatus.setPadding(16, 8, 16, 8);

            // Add to the appropriate layout based on status
            if (status.equalsIgnoreCase("Available")) {
                availabilityLayout.addView(employeeStatus);
            } else if (status.equalsIgnoreCase("On Leave") || status.equalsIgnoreCase("Time Off Requested")) {
                TextView detailView = new TextView(this);
                detailView.setText("Details: " + details);
                detailView.setTextSize(14);
                detailView.setPadding(16, 4, 16, 8);

                timeOffLayout.addView(employeeStatus);
                timeOffLayout.addView(detailView);
            }
        }

        // Attach layouts to respective ScrollViews
        scrollViewAvailability.addView(availabilityLayout);
        scrollViewTimeOff.addView(timeOffLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fetchUserProfile(loggedInUsername);
        }
        return super.onOptionsItemSelected(item);
    }

    // When on back button check userType to make sure goes back to right page
    private void fetchUserProfile(final String username) {
        // URL to fetch user profile using the username
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/username/" + username;

        // Make a GET request to fetch the user profile
        JsonObjectRequest profileRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, // No body needed for GET request
                new Response.Listener<JSONObject>() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Extract user details from the response
                            int userId = response.getInt("userId");
                            String fullName = response.getString("fullName");
                            String userType = response.getString("userType");

                            // Save these details in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("userId", userId);
                            editor.putString("username", username);
                            editor.putString("userType", userType);
                            editor.putString("fullName", fullName);
                            editor.apply();

                            // Redirect to appropriate activity based on user type
                            Intent intent;
                            switch (userType) {
                                case "ADMIN":
                                    intent = new Intent(employeeStatusActivity.this, adminActivity.class);
                                    break;
                                case "EMPLOYER":
                                    intent = new Intent(employeeStatusActivity.this, employerActivity.class);
                                    break;
                                case "EMPLOYEE":
                                    intent = new Intent(employeeStatusActivity.this, employeeActivity.class);
                                    break;
                                default:
                                    Toast.makeText(employeeStatusActivity.this, "Unknown user type", Toast.LENGTH_SHORT);
                                    return;
                            }
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(employeeStatusActivity.this,"Error parsing user profile.", Toast.LENGTH_SHORT);
                            Log.e("Profile Error", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(employeeStatusActivity.this,"Failed to fetch user profile.", Toast.LENGTH_SHORT);
                        Log.e("Profile Error", error.toString());
                    }
                });

        // Add the profile request to the Volley request queue
        Volley.newRequestQueue(employeeStatusActivity.this).add(profileRequest);
    }
}
