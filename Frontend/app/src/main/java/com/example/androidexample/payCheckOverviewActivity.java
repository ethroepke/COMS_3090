package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class payCheckOverviewActivity extends AppCompatActivity {

    private LinearLayout payDetailsContainer;
    private Button showHideButton;
    private TextView userName;
    private TextView takeHomePay;
    private TextView grossPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paycheckoverview); // Make sure the layout is named properly

        // Initialize the views
        payDetailsContainer = findViewById(R.id.payDetailsContainer);
        showHideButton = findViewById(R.id.showHideDetailsButton);
        userName = findViewById(R.id.userName);
        takeHomePay = findViewById(R.id.takeHomePay);
        grossPay = findViewById(R.id.grossPay);

        // Optional: Set values for the user’s pay information
        setUserData();

        // Set up the button click listener to toggle the paycheck details
        showHideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePayDetails();
            }
        });
    }

    // This method sets some example data; replace it with real data from your backend.
    private void setUserData() {
        userName.setText("John Doe"); // Replace with actual user data
        takeHomePay.setText("Take Home Pay: $1500.00"); // Replace with actual take-home pay
        grossPay.setText("Gross Pay: $2000.00"); // Replace with actual gross pay
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

