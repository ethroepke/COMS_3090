package com.example.androidexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class performanceReviewActivity extends AppCompatActivity {

    private LinearLayout reviewLayout;
    private Button addReviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_review); // Replace with the correct layout

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
}
