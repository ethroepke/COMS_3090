package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class createTasksActivity extends AppCompatActivity {

    private EditText taskNameText;
    private EditText taskDescriptionText;
    private EditText dueDateText;
    private Spinner priorityLevelSpinner;
    private EditText employeeAssignedText;
    private Button saveButton;

    // Replace with URL when they finish
    private static final String API_URL = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtasks);

        taskNameText = findViewById(R.id.taskName);
        taskDescriptionText = findViewById(R.id.taskDescription);
        dueDateText = findViewById(R.id.dueDate);
        priorityLevelSpinner = findViewById(R.id.priorityLevel);
        employeeAssignedText = findViewById(R.id.employeeAssigned);
        saveButton = findViewById(R.id.saveButton);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolBarCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Tasks");

        // Priority level dropdown
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorityLevelSpinner.setAdapter(adapter);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
            }
        });
    }

    private void saveProject() {
        // Retrieve user input
        String taskName = taskNameText.getText().toString();
        String projectDescription = taskDescriptionText.getText().toString();
        String dueDate = dueDateText.getText().toString();
        String priorityLevel = priorityLevelSpinner.getSelectedItem().toString();
        String employeeAssigned = employeeAssignedText.getText().toString();

        // JSON to hold data
        JSONObject projectData = new JSONObject();
        try {
            projectData.put("name", taskName);
            projectData.put("description", projectDescription);
            projectData.put("dueDate", dueDate);
            projectData.put("priority", priorityLevel);
            projectData.put("assignedTo", employeeAssigned);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create task data", Toast.LENGTH_SHORT).show();
            return;
        }

        // JSON to send data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                API_URL,
                projectData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // success creating project
                        Toast.makeText(createTasksActivity.this, "Project created successfully!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error creating project
                        Toast.makeText(createTasksActivity.this, "Error creating project: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //For permissions this will be chang depending on users (Admin or Employer or Employee) to make sure send back to right page
            Intent intent = new Intent(createTasksActivity.this, projectEmployerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
