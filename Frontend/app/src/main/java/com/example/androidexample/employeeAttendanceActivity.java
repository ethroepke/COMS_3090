package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class employeeAttendanceActivity extends AppCompatActivity {

    // UI components
    private ScrollView currentUsers;
    private ScrollView previousUsers;
    private LinearLayout currentUsersLayout;
    private LinearLayout previousUsersLayout;
    private String loggedInUsername;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeeattendance);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employee Attendance");

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);


        currentUsers = findViewById(R.id.currentUsersScrollView);
        previousUsers = findViewById(R.id.previousUsersScrollView);
        currentUsersLayout = findViewById(R.id.currentUsersScrollView).findViewById(R.id.currentUserLayout);
        previousUsersLayout = findViewById(R.id.previousUsersScrollView).findViewById(R.id.previousUserLayout);

        // Populate UI dynamically
        populateCurrentUsers();
        populatePreviousUsers();
    }

    /**
     * Populates the list of current users working.
     */
    private void populateCurrentUsers() {
        try {
            JSONArray currentUsers = getCurrentUsersData(); // Simulate fetching current users data
            for (int i = 0; i < currentUsers.length(); i++) {
                JSONObject user = currentUsers.getJSONObject(i);
                String name = user.getString("name");
                String clockInTime = user.getString("clockInTime");

                // Create a TextView for each user
                TextView userView = new TextView(this);
                userView.setText(name + " - Clocked In: " + clockInTime);
                userView.setTextSize(16);
                userView.setPadding(8, 8, 8, 8);
                userView.setBackgroundColor(getResources().getColor(android.R.color.white));
                userView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                currentUsersLayout.addView(userView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the list of users who worked today.
     */
    private void populatePreviousUsers() {
        try {
            JSONArray previousUsers = getPreviousUsersData(); // Simulate fetching previous users data
            for (int i = 0; i < previousUsers.length(); i++) {
                JSONObject user = previousUsers.getJSONObject(i);
                String name = user.getString("name");
                String clockInTime = user.getString("clockInTime");
                String clockOutTime = user.getString("clockOutTime");

                // Create a TextView for each user
                TextView userView = new TextView(this);
                userView.setText(name + "\nClocked In: " + clockInTime + ", Clocked Out: " + clockOutTime);
                userView.setTextSize(16);
                userView.setPadding(8, 8, 8, 8);
                userView.setBackgroundColor(getResources().getColor(android.R.color.white));
                userView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                previousUsersLayout.addView(userView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates fetching data for current users working.
     */
    private JSONArray getCurrentUsersData() throws JSONException {
        // Simulate API response or database query
        JSONArray users = new JSONArray();
        users.put(new JSONObject().put("name", "Ethan Roepke").put("clockInTime", "09:00 AM"));
        users.put(new JSONObject().put("name", "John Doe").put("clockInTime", "08:30 AM"));
        return users;
    }

    /**
     * Simulates fetching data for users who worked today.
     */
    private JSONArray getPreviousUsersData() throws JSONException {
        // Simulate API response or database query
        JSONArray users = new JSONArray();
        users.put(new JSONObject().put("name", "Ethan Roepke").put("clockInTime", "09:00 AM").put("clockOutTime", "05:00 PM"));
        users.put(new JSONObject().put("name", "Jane Smith").put("clockInTime", "08:00 AM").put("clockOutTime", "04:00 PM"));
        return users;
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
                                    intent = new Intent(employeeAttendanceActivity.this, adminActivity.class);
                                    break;
                                case "EMPLOYER":
                                    intent = new Intent(employeeAttendanceActivity.this, employerActivity.class);
                                    break;
                                case "EMPLOYEE":
                                    intent = new Intent(employeeAttendanceActivity.this, employeeActivity.class);
                                    break;
                                default:
                                    Toast.makeText(employeeAttendanceActivity.this, "Unknown user type", Toast.LENGTH_SHORT);
                                    return;
                            }
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(employeeAttendanceActivity.this,"Error parsing user profile.", Toast.LENGTH_SHORT);
                            Log.e("Profile Error", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(employeeAttendanceActivity.this,"Failed to fetch user profile.", Toast.LENGTH_SHORT);
                        Log.e("Profile Error", error.toString());
                    }
                });

        // Add the profile request to the Volley request queue
        Volley.newRequestQueue(employeeAttendanceActivity.this).add(profileRequest);
    }
}
