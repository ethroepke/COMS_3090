package com.example.androidexample;

import android.annotation.SuppressLint;
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

public class taskEmployeeActivity extends AppCompatActivity {
    private LinearLayout taskListLayout;
    private List<Map<String, String>> taskList;

    // Replace when backend finishes
    private static final String API_URL = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskemployee);

        taskListLayout = findViewById(R.id.tasklayoutEmployee);
        taskList = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbarEmployee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tasks");
        
        fetchtasks();
    }

    private void fetchtasks() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseTasksData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        TextView errorText = new TextView(taskEmployeeActivity.this);
                        errorText.setText("Error fetching tasks: " + error.getMessage());
                        taskListLayout.addView(errorText);
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void parseTasksData(JSONArray tasksArray) {
        taskList.clear();
        for (int i = 0; i < tasksArray.length(); i++) {
            try {
                JSONObject task = tasksArray.getJSONObject(i);
                String name = task.getString("name");
                String description = task.getString("description");
                String assignedTo = task.getString("assignedTo");
                String dueDate = task.getString("dueDate");
                String importance = task.getString("importance");

                addtaskToList(name, description, assignedTo, dueDate, importance);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addtaskToList(String name, String description, String assignedTo, String dueDate, String importance) {
        Map<String, String> newtask = new HashMap<>();
        newtask.put("name", name);
        newtask.put("description", description);
        newtask.put("assignedTo", assignedTo);
        newtask.put("dueDate", dueDate);
        newtask.put("importance", importance);

        taskList.add(newtask);
        displaytasks();
    }

    private void displaytasks() {
        taskListLayout.removeAllViews();
        if (taskList.isEmpty()) {
            TextView noTaskText = new TextView(this);
            noTaskText.setText("No tasks assigned");
            noTaskText.setTextSize(18);
            taskListLayout.addView(noTaskText);
        } else {
            for (Map<String, String> task : taskList) {
                View taskView = createTasksView(task);
                taskListLayout.addView(taskView);
            }
        }
    }

    private View createTasksView(Map<String, String> task) {
        LinearLayout taskView = new LinearLayout(this);
        taskView.setOrientation(LinearLayout.VERTICAL);
        TextView nameText = new TextView(this);
        nameText.setText("Name: " + task.get("name"));
        TextView descText = new TextView(this);
        descText.setText("Description: " + task.get("description"));
        TextView assignedText = new TextView(this);
        assignedText.setText("Assigned to: " + task.get("assignedTo"));
        TextView dueText = new TextView(this);
        dueText.setText("Due Date: " + task.get("dueDate"));
        TextView importanceText = new TextView(this);
        importanceText.setText("Importance: " + task.get("importance"));

        taskView.addView(nameText);
        taskView.addView(descText);
        taskView.addView(assignedText);
        taskView.addView(dueText);
        taskView.addView(importanceText);

        return taskView;
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