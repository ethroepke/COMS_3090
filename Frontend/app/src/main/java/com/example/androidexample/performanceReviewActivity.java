package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class performanceReviewActivity extends AppCompatActivity {

    private LinearLayout reviewLayout;
    private Button addReviewButton;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_review); // Replace with the correct layout

        Toolbar toolbar = findViewById(R.id.toolBarReviews);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Performance Reviews");

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        // Initialize views
        reviewLayout = findViewById(R.id.reviewLayout);
        addReviewButton = findViewById(R.id.addReviewButton);

        // Add an onClickListener for the "Add New Review" button
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewReview();
            }
        });

        // Load existing reviews (if any) here
        loadReviews();
    }

    // Method to add a new review
    private void addNewReview() {
        // Here you would ideally open a new activity or show a dialog to allow the user to input a new review.
        // For now, we will simulate adding a new review by adding a sample text view.

        TextView newReview = new TextView(this);
        newReview.setText("New review: Excellent performance this quarter!");
        newReview.setTextSize(16);
        newReview.setPadding(16, 16, 16, 16);
        newReview.setBackgroundColor(getResources().getColor(android.R.color.white));

        // Add the new review to the layout
        reviewLayout.addView(newReview);

        // Show a toast confirming the addition of a review
        Toast.makeText(this, "New review added", Toast.LENGTH_SHORT).show();
    }

    // Method to load existing reviews (example)
    private void loadReviews() {
        // Here, you would load reviews from a database, API, or other source.
        // For this example, we will just add a sample review manually.

        TextView existingReview = new TextView(this);
        existingReview.setText("Review 1: Great work on the recent project!");
        existingReview.setTextSize(16);
        existingReview.setPadding(16, 16, 16, 16);
        existingReview.setBackgroundColor(getResources().getColor(android.R.color.white));

        // Add the existing review to the layout
        reviewLayout.addView(existingReview);
    }

    // When on back button check userType to make sure goes back to right page
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
                            Intent intent;
                            switch (userType) {
                                case "ADMIN":
                                    intent = new Intent(performanceReviewActivity.this, adminActivity.class);
                                    break;
                                case "EMPLOYER":
                                    intent = new Intent(performanceReviewActivity.this, employerActivity.class);
                                    break;
                                case "EMPLOYEE":
                                    intent = new Intent(performanceReviewActivity.this, employeeActivity.class);
                                    break;
                                default:
                                    Toast.makeText(performanceReviewActivity.this, "Unknown user type", Toast.LENGTH_SHORT);
                                    return;
                            }
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(performanceReviewActivity.this,"Error parsing user profile.", Toast.LENGTH_SHORT);
                            Log.e("Profile Error", "JSON parsing error", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(performanceReviewActivity.this,"Failed to fetch user profile.", Toast.LENGTH_SHORT);
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
