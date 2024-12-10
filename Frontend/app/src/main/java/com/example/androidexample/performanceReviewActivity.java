package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class performanceReviewActivity extends AppCompatActivity {

    private Spinner employeeSpinner;
    private String loggedInUsername;
    private String employerName;
    private ArrayList<String> employeeUsernames = new ArrayList<>(); // To store employee usernames

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_review); // Use the correct layout

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolBarReviews);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Performance Reviews");

        // Retrieve username and employer name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);
        employerName = sharedPreferences.getString("fullName", null); // Assuming fullName is saved as employer's name

        // Initialize views
        employeeSpinner = findViewById(R.id.employeeSpinner);

        // Load employee list
        loadEmployees();
    }

    // Method to load employees from the API
    private void loadEmployees() {
        // URL to fetch the list of employees
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/all";

        // Make a GET request to fetch the list of employees
        JsonArrayRequest employeeRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, // No body needed for GET request
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Clear the previous list of employee usernames
                            employeeUsernames.clear();

                            // Parse the response and add employee usernames to the list
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject employee = response.getJSONObject(i);
                                String username = employee.getString("username");
                                employeeUsernames.add(username);
                            }

                            // Populate the Spinner with employee usernames
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(performanceReviewActivity.this,
                                    android.R.layout.simple_spinner_item, employeeUsernames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            employeeSpinner.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(performanceReviewActivity.this, "Error parsing employee list.", Toast.LENGTH_SHORT).show();
                            Log.e("Employee List Error", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(performanceReviewActivity.this, "Failed to fetch employee list.", Toast.LENGTH_SHORT).show();
                        Log.e("Employee List Error", error.toString());
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(performanceReviewActivity.this).add(employeeRequest);
    }

    // When on back button, check userType to make sure it goes back to the right page
    private void checkUserType(final String username) {
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
                            // (Handle redirect based on the userType logic here)
                        } catch (JSONException e) {
                            Toast.makeText(performanceReviewActivity.this, "Error parsing user profile.", Toast.LENGTH_SHORT).show();
                            Log.e("Profile Error", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(performanceReviewActivity.this, "Failed to fetch user profile.", Toast.LENGTH_SHORT).show();
                        Log.e("Profile Error", error.toString());
                    }
                });

        // Add the profile request to the Volley request queue
        Volley.newRequestQueue(performanceReviewActivity.this).add(profileRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            checkUserType(loggedInUsername);
        }
        return super.onOptionsItemSelected(item);
    }
}
