package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class payCheckOverviewActivity extends AppCompatActivity {

    private LinearLayout payDetailsContainer;
    private Button showHideButton;
    private TextView userName;
    private TextView takeHomePay;
    private TextView grossPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paycheckoverview); // Ensure the layout is named properly

        // Initialize the views
        payDetailsContainer = findViewById(R.id.payDetailsContainer);
        showHideButton = findViewById(R.id.showHideDetailsButton);
        userName = findViewById(R.id.userName);
        takeHomePay = findViewById(R.id.takeHomePay);
        grossPay = findViewById(R.id.grossPay);

        // Fetch user data from the backend
        fetchUserData();

        // Set up the button click listener to toggle the paycheck details
        showHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePayDetails();
            }
        });
    }

    // Method to fetch user data from the backend and set it in the TextViews
    private void fetchUserData() {
        String url = "https://your-api-url.com/api/user/paycheck"; // Replace with your API URL

        // Create a new request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response and set the values
                            String name = response.getString("name"); // Adjust based on your API response structure
                            String takeHome = response.getString("takeHomePay"); // Adjust as needed
                            String gross = response.getString("grossPay"); // Adjust as needed

                            userName.setText(name);
                            takeHomePay.setText("Take Home Pay: $" + takeHome);
                            grossPay.setText("Gross Pay: $" + gross);
                        } catch (JSONException e) {
                            e.printStackTrace(); // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace(); // Handle error response
                        // Optionally show an error message to the user
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    // Method to toggle the visibility of the pay details container
    private void togglePayDetails() {
        if (payDetailsContainer.getVisibility() == View.VISIBLE) {
            // If currently visible, hide it
            payDetailsContainer.setVisibility(View.GONE);
            showHideButton.setText("Show Pay Details"); // Update button text
        } else {
            // If currently hidden, show it
            payDetailsContainer.setVisibility(View.VISIBLE);
            showHideButton.setText("Hide Pay Details"); // Update button text
        }
    }
}


