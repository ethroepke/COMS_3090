package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class taskEmployeeActivity extends AppCompatActivity {
    private LinearLayout taskListLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskemployee);

        taskListLayout = findViewById(R.id.tasklayoutEmployee);

        Toolbar toolbar = findViewById(R.id.toolbarEmployee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tasks");

        // Fetch all tasks from the backend and display them
        fetchAllTasks();
    }

    private void fetchAllTasks() {
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/tasks";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Clear the layout to avoid duplicating task cards
                            taskListLayout.removeAllViews();

                            // Loop through each task in the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject taskObject = response.getJSONObject(i);

                                // Extract fields from the JSON object
                                String taskName = taskObject.optString("name", "Unnamed Task");
                                String taskDescription = taskObject.optString("description", "No description available.");
                                String status = taskObject.optString("status", "No status available.");

                                // Create a new CardView for this task
                                CardView taskCard = new CardView(taskEmployeeActivity.this);
                                LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                cardLayoutParams.setMargins(16, 16, 16, 16);
                                taskCard.setLayoutParams(cardLayoutParams);
                                taskCard.setCardBackgroundColor(getResources().getColor(R.color.cardBackground));
                                taskCard.setRadius(16);
                                taskCard.setCardElevation(8);

                                // Create a layout for the CardView content
                                LinearLayout taskLayout = new LinearLayout(taskEmployeeActivity.this);
                                taskLayout.setOrientation(LinearLayout.VERTICAL);
                                taskLayout.setPadding(24, 24, 24, 24);

                                // Add Task Name (on its own line)
                                TextView taskNameView = new TextView(taskEmployeeActivity.this);
                                taskNameView.setText("Task: " + taskName);
                                taskNameView.setTextSize(18);
                                taskNameView.setTextColor(getResources().getColor(R.color.black));
                                taskLayout.addView(taskNameView);

                                // Add Task Description (on its own line)
                                TextView taskDescriptionView = new TextView(taskEmployeeActivity.this);
                                taskDescriptionView.setText("Description: " + taskDescription);
                                taskDescriptionView.setTextColor(getResources().getColor(R.color.black));
                                taskDescriptionView.setPadding(0, 8, 0, 8);
                                taskLayout.addView(taskDescriptionView);

                                // Create a FrameLayout to hold the status text on top of the color box
                                FrameLayout statusLayout = new FrameLayout(taskEmployeeActivity.this);
                                LinearLayout.LayoutParams statusLayoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                statusLayoutParams.setMargins(0, 16, 0, 0); // Add some top margin for separation
                                statusLayout.setLayoutParams(statusLayoutParams);

                                // Create the background color box for status
                                TextView colorBox = new TextView(taskEmployeeActivity.this);
                                LinearLayout.LayoutParams colorBoxLayoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                colorBox.setLayoutParams(colorBoxLayoutParams);
                                colorBox.setPadding(0, 40, 0, 40); // Add padding to increase the height of the color box

                                // Set background color based on task status
                                if (status.equals("Completed")) {
                                    colorBox.setBackgroundColor(getResources().getColor(R.color.green)); // Green for completed
                                } else if (status.equals("In Progress")) {
                                    colorBox.setBackgroundColor(getResources().getColor(R.color.yellow)); // Yellow for in progress
                                } else {
                                    colorBox.setBackgroundColor(getResources().getColor(R.color.red)); // Red for pending
                                }

                                // Create the status text and center it on top of the color box
                                TextView taskStatusView = new TextView(taskEmployeeActivity.this);
                                taskStatusView.setText(status);
                                taskStatusView.setTextColor(getResources().getColor(R.color.white));
                                taskStatusView.setTextSize(16);
                                taskStatusView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                FrameLayout.LayoutParams taskStatusViewParams = new FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        FrameLayout.LayoutParams.WRAP_CONTENT
                                );
                                taskStatusViewParams.gravity = android.view.Gravity.CENTER;
                                taskStatusView.setLayoutParams(taskStatusViewParams);

                                // Add both colorBox and taskStatusView to the FrameLayout
                                statusLayout.addView(colorBox);
                                statusLayout.addView(taskStatusView);

                                // Add the status layout to the main task layout
                                taskLayout.addView(statusLayout);

                                // Add the layout to the CardView
                                taskCard.addView(taskLayout);

                                // Add the CardView to the main layout
                                taskListLayout.addView(taskCard);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Parsing Error", "Error parsing task JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Volley Error", "Error fetching tasks: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(taskEmployeeActivity.this, employeeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}




