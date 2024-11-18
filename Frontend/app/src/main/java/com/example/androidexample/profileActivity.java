package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class profileActivity extends AppCompatActivity {

    private String loggedInUsername;
    private TextView usernameView;
    private TextView fullNameView;
    private TextView emailView;
    private TextView jobTitleView;
    private TextView userTypeView;
    private TextView payRateView;
    private TextView departmentView;
    private TextView dateOfHireView;



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        fetchUserProfile(loggedInUsername);

        usernameView = findViewById(R.id.username);
        fullNameView = findViewById(R.id.fullName);
        emailView = findViewById(R.id.email);
        payRateView = findViewById(R.id.payrate);
        jobTitleView = findViewById(R.id.jobTitle);
        userTypeView = findViewById(R.id.userType);
        departmentView = findViewById(R.id.department);
        dateOfHireView = findViewById(R.id.datOfHire);
    }

    private void fetchUserProfile(String username) {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/username/" + username;

        // Create a new request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response and set the values
                            String username = response.optString("username", "N/A");
                            String fullName = response.optString("fullName", "N/A");
                            String email = response.optString("email", "N/A");
                            String jobTitle = response.optString("jobTitle", "N/A");
                            String userType = response.optString("userType", "N/A");
                            String department = response.optString("department", "N/A");
                            String dateOfHire = response.optString("dateOfHire", "N/A");

                            // Set values to TextViews
                            usernameView.setText(username);
                            fullNameView.setText(fullName);
                            emailView.setText(email);
                            jobTitleView.setText(jobTitle);
                            userTypeView.setText(userType);
                            departmentView.setText(department);
                            dateOfHireView.setText(dateOfHire);

                        } catch (Exception e) {
                            Toast.makeText(profileActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(profileActivity.this, "Error fetching profile", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
