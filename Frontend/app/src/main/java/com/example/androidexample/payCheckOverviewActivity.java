package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;

public class payCheckOverviewActivity extends AppCompatActivity {

    private LinearLayout payDetailsContainer;
    private Button showHideButton;
    private TextView userName;
    private TextView takeHomePay;
    private TextView grossPay;
    private String loggedInUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paycheckoverview);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);


        // Initialize the views
        payDetailsContainer = findViewById(R.id.payDetailsContainer);
        showHideButton = findViewById(R.id.showHideDetailsButton);
        userName = findViewById(R.id.userName);
        takeHomePay = findViewById(R.id.takeHomePay);
        grossPay = findViewById(R.id.grossPay);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolBarPay);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pay Details");
        }

        // Display the logged-in username
        userName.setText(loggedInUsername);

        // Fetch user data using the logged-in username
        fetchUserData(loggedInUsername);

        // Set up the button click listener to toggle the paycheck details
        showHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePayDetails();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Determine navigation based on user role
            Intent intent = new Intent(payCheckOverviewActivity.this, adminActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to fetch user data from the backend and set it in the TextViews
    private void fetchUserData(String username) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/username/" + username;

        // Create a new request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response and set the values
                            String name = response.optString("name", "N/A");
                            String takeHome = response.optString("takeHomePay", "0.00");
                            String gross = response.optString("grossPay", "0.00");

                            userName.setText(name);
                            takeHomePay.setText("Take Home Pay: $" + takeHome);
                            grossPay.setText("Gross Pay: $" + gross);
                        } catch (Exception e) {
                            Toast.makeText(payCheckOverviewActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(payCheckOverviewActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    // Method to toggle the visibility of the pay details container
    private void togglePayDetails() {
        if (payDetailsContainer.getVisibility() == View.VISIBLE) {
            payDetailsContainer.setVisibility(View.GONE);
            showHideButton.setText("Show Pay Details");
        } else {
            payDetailsContainer.setVisibility(View.VISIBLE);
            showHideButton.setText("Hide Pay Details");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


