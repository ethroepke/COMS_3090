package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class EmployeePerformanceReviewActivity extends AppCompatActivity {

    private TextView performanceReviewTitle;
    private LinearLayout reviewLayout;
    private Button addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_perfomance_review);  // Load the XML layout

        // Initialize the UI components
        performanceReviewTitle = findViewById(R.id.performanceReviewTitle);
        reviewLayout = findViewById(R.id.reviewLayout);
        addReviewButton = findViewById(R.id.addReviewButton);

        // Get the username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String currentUsername = sharedPreferences.getString("username", null);  // Retrieve the username

        if (currentUsername != null) {
            // Set the performance review title with the employee's username
            performanceReviewTitle.setText("Performance Reviews for " + currentUsername);

            // Fetch and display the performance reviews associated with the employee here
            displayEmployeeReviews(currentUsername);
        }

        // Button click listener (You can use this for functionality to add a review)
        addReviewButton.setOnClickListener(v -> {
            // Handle the review creation logic here (e.g., open a dialog or another activity to add a new review)
        });
    }

    // This method will display the reviews for the current employee
    private void displayEmployeeReviews(String username) {
        // For now, we are just simulating the display of reviews.
        // You will need to fetch the actual reviews from your database or API.

        // Example review text (replace with actual data retrieval logic)
        String[] reviews = {
                "Great performance this month, keep up the good work!",
                "Needs improvement in communication skills.",
                "Excellent teamwork and collaboration."
        };

        // Display reviews in the reviewLayout LinearLayout
        for (String review : reviews) {
            TextView reviewTextView = new TextView(this);
            reviewTextView.setText(review);
            reviewTextView.setTextSize(16);
            reviewTextView.setPadding(16, 8, 16, 8);  // Padding for the reviews
            reviewLayout.addView(reviewTextView);
        }

        // If there are no reviews, you can show a message to the employee
        if (reviews.length == 0) {
            TextView noReviewsMessage = new TextView(this);
            noReviewsMessage.setText("No performance reviews available at the moment.");
            noReviewsMessage.setTextSize(16);
            noReviewsMessage.setPadding(16, 8, 16, 8);
            reviewLayout.addView(noReviewsMessage);
        }
    }
}
