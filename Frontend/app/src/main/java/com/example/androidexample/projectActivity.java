package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.ContextCompat;

public class projectActivity extends AppCompatActivity {
    private LinearLayout projectListLayout;
    private List<Map<String, String>> projectList;
    private Button createProjButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projectassgin);

        createProjButton = findViewById(R.id.createProject);
        projectListLayout = findViewById(R.id.project_list_layout);
        projectList = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Projects");

        createProjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(projectActivity.this, createProject.class);
                startActivity(intent);
            }
        });

        // Fetch projects from the backend
        fetchProjects();
    }


    private void fetchProjects() {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/project/allproject";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("JSON Response: " + response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject projectObject = response.getJSONObject(i);

                                // Extract fields from the JSON object
                                String projectName = projectObject.optString("projectName", "Unnamed Project");
                                String projectDescription = projectObject.optString("description", "No description available.");
                                String priority = projectObject.optString("priority", "No priority");
                                String dueDate = projectObject.optString("dueDate", "No due date");

                                // Format text for each line
                                String projectNameText = "Project: " + projectName;
                                String projectDescriptionText = "Description: " + projectDescription;
                                String dueDateText = "Due Date: " + dueDate;

                                // Create a CardView for each project
                                CardView cardView = new CardView(projectActivity.this);
                                cardView.setCardElevation(8);
                                cardView.setRadius(16);
                                cardView.setUseCompatPadding(true);

                                // Create a LinearLayout to hold the TextViews inside the CardView
                                LinearLayout cardLayout = new LinearLayout(projectActivity.this);
                                cardLayout.setOrientation(LinearLayout.VERTICAL);
                                cardLayout.setPadding(16, 16, 16, 16);

                                // Create TextViews for each line
                                TextView nameView = new TextView(projectActivity.this);
                                nameView.setText(projectNameText);
                                nameView.setPadding(0, 0, 0, 8);

                                TextView descriptionView = new TextView(projectActivity.this);
                                descriptionView.setText(projectDescriptionText);
                                descriptionView.setPadding(0, 8, 0, 8);

                                // TextView for Due Date
                                TextView dueDateView = new TextView(projectActivity.this);
                                dueDateView.setText(dueDateText);
                                dueDateView.setPadding(0, 8, 0, 8);

                                // TextView for Priority
                                TextView priorityView = new TextView(projectActivity.this);
                                priorityView.setText("Priority: " + priority);
                                priorityView.setPadding(8, 4, 8, 4); // padding for a badge-like look

                                // Set background color based on priority level
                                int priorityBackgroundColor;
                                switch (priority.toLowerCase()) {
                                    case "high":
                                        priorityBackgroundColor = ContextCompat.getColor(projectActivity.this, android.R.color.holo_red_light);
                                        break;
                                    case "medium":
                                        priorityBackgroundColor = ContextCompat.getColor(projectActivity.this, android.R.color.holo_orange_light);
                                        break;
                                    case "low":
                                        priorityBackgroundColor = ContextCompat.getColor(projectActivity.this, android.R.color.holo_green_light);
                                        break;
                                    default:
                                        priorityBackgroundColor = ContextCompat.getColor(projectActivity.this, android.R.color.darker_gray);
                                }
                                priorityView.setBackgroundColor(priorityBackgroundColor);
                                priorityView.setTextColor(ContextCompat.getColor(projectActivity.this, android.R.color.white)); // set text color to white for better contrast
                                priorityView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                                // Add TextViews to the card layout
                                cardLayout.addView(nameView);
                                cardLayout.addView(descriptionView);
                                cardLayout.addView(dueDateView);
                                cardLayout.addView(priorityView);

                                // Add the card layout to the CardView
                                cardView.addView(cardLayout);

                                // Add the CardView to the main layout
                                projectListLayout.addView(cardView);

                                // Optional: add a margin between cards
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                                layoutParams.setMargins(16, 16, 16, 16);
                                cardView.setLayoutParams(layoutParams);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(projectActivity.this, adminActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






