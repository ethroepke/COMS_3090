package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import android.content.SharedPreferences;

public class taskEmployeeActivity extends AppCompatActivity {
    private LinearLayout taskListLayout;
    private String loggedInUsername;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskemployee);

        // Retrieve username from SharedPreferences when login
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUsername = sharedPreferences.getString("username", null);

        //Filter tasks by employee
        fetchTasksForEmployee(loggedInUsername);

        taskListLayout = findViewById(R.id.tasklayoutEmployee);

        Toolbar toolbar = findViewById(R.id.toolbarEmployee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tasks");

    }

    private void fetchTasksForEmployee(String loggedInUsername) {
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

                                // Check if this task is assigned to the logged-in user
                                JSONArray assignedEmployees = taskObject.optJSONArray("assignedEmployees");
                                if (assignedEmployees != null) {
                                    for (int j = 0; j < assignedEmployees.length(); j++) {
                                        JSONObject employeeObject = assignedEmployees.getJSONObject(j);
                                        String employeeUsername = employeeObject.optString("username", "");

                                        // If task is assigned to this employee, display it
                                        if (loggedInUsername.equals(employeeUsername)) {
                                            // Create a new CardView for this task
                                            CardView taskCard = new CardView(taskEmployeeActivity.this);
                                            taskCard.setLayoutParams(new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            taskCard.setCardBackgroundColor(getResources().getColor(R.color.cardBackground));
                                            taskCard.setRadius(16);

                                            // Create a layout for the CardView content
                                            LinearLayout taskLayout = new LinearLayout(taskEmployeeActivity.this);
                                            taskLayout.setOrientation(LinearLayout.VERTICAL);
                                            taskLayout.setPadding(16, 16, 16, 16);

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

                                            // Add Task Status (on the same line with Priority box)
                                            LinearLayout statusLayout = new LinearLayout(taskEmployeeActivity.this);
                                            statusLayout.setOrientation(LinearLayout.HORIZONTAL);
                                            statusLayout.setWeightSum(2);

                                            TextView taskStatusView = new TextView(taskEmployeeActivity.this);
                                            taskStatusView.setText("Status: " + status);
                                            taskStatusView.setTextColor(getResources().getColor(R.color.black));
                                            taskStatusView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                            statusLayout.addView(taskStatusView);

                                            // Add Priority (with color box based on status)
                                            TextView priorityBox = new TextView(taskEmployeeActivity.this);
                                            priorityBox.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                                            priorityBox.setPadding(8, 8, 8, 8);
                                            priorityBox.setGravity(View.TEXT_ALIGNMENT_CENTER);

                                            // Set background color based on task status
                                            if (status.equals("Completed")) {
                                                priorityBox.setBackgroundColor(getResources().getColor(R.color.green)); // Green for completed
                                            } else if (status.equals("In Progress")) {
                                                priorityBox.setBackgroundColor(getResources().getColor(R.color.yellow)); // Yellow for in progress
                                            } else {
                                                priorityBox.setBackgroundColor(getResources().getColor(R.color.red)); // Red for pending
                                            }

                                            statusLayout.addView(priorityBox);
                                            taskLayout.addView(statusLayout);

                                            // Add the layout to the CardView
                                            taskCard.addView(taskLayout);

                                            // Add the CardView to the main layout
                                            taskListLayout.addView(taskCard);
                                        }
                                    }
                                }
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
