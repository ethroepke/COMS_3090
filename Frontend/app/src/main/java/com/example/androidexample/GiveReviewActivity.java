package com.example.androidexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GiveReviewActivity extends AppCompatActivity {

    private Spinner employeeSpinner;
    private EditText reviewEditText;
    private Button submitReviewButton;
    private String loggedInUsername;
    private String selectedEmployeeName;
    private int selectedEmployeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review); // Use the correct layout

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolBarGiveReview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Give Review");

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        // Initialize views
        employeeSpinner = findViewById(R.id.employeeSpinner);
        reviewEditText = findViewById(R.id.reviewEditText);
        submitReviewButton = findViewById(R.id.submitReviewButton);

        // Load employee list (simulating from an API)
        loadEmployees();

        // Set up the submit button click listener
        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });
    }

    // Method to load employee list (example)
    private void loadEmployees() {
        // Fetch the list of employees (simulated API call)
        String url = "https://32bedba2-9b96-417c-bf46-88d9fc3ad047.mock.pstmn.io/names";  // Replace with your actual URL
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray employees = response.getJSONArray("employees");
                            String[] employeeNames = new String[employees.length()];

                            // Populate the spinner with employee names
                            for (int i = 0; i < employees.length(); i++) {
                                JSONObject employee = employees.getJSONObject(i);
                                employeeNames[i] = employee.getString("fullName");
                            }

                            // Set up the spinner adapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(GiveReviewActivity.this,
                                    android.R.layout.simple_spinner_item, employeeNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            employeeSpinner.setAdapter(adapter);

                            // Set up the spinner item selection listener
                            employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                    try {
                                        JSONObject selectedEmployee = employees.getJSONObject(position);
                                        selectedEmployeeName = selectedEmployee.getString("fullName");
                                        selectedEmployeeId = selectedEmployee.getInt("employeeId");
                                    } catch (JSONException e) {
                                        Log.e("Error", "Failed to parse employee data", e);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                    // Do nothing
                                }
                            });
                        } catch (JSONException e) {
                            Log.e("Error", "Failed to parse employee data", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GiveReviewActivity.this, "Failed to load employee data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley queue
        Volley.newRequestQueue(GiveReviewActivity.this).add(request);
    }

    // Method to submit the review
    private void submitReview() {
        String reviewText = reviewEditText.getText().toString().trim();

        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Please enter a review", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the review data
        JSONObject reviewData = new JSONObject();
        try {
            reviewData.put("reviewer", loggedInUsername);
            reviewData.put("employeeId", selectedEmployeeId);
            reviewData.put("review", reviewText);

            // Send the review to the server (simulated API request)
            String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/performance-reviews/create";
            JsonObjectRequest submitRequest = new JsonObjectRequest(Request.Method.POST, url, reviewData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(GiveReviewActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(GiveReviewActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                        }
                    });

            // Add the request to the Volley queue
            Volley.newRequestQueue(GiveReviewActivity.this).add(submitRequest);

        } catch (JSONException e) {
            Log.e("Error", "Failed to prepare review data", e);
        }
    }
}
