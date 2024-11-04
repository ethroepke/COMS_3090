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

public class createProject extends AppCompatActivity {

    private EditText projectNameEditText;
    private EditText projectDescriptionEditText;
    private EditText dueDateEditText;
    private Spinner priorityLevelSpinner;
    private EditText employerAssignedEditText;
    private Button saveButton;

    // Replace with URL when they finish
    private static final String API_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createproject);

        projectNameEditText = findViewById(R.id.project_name);
        projectDescriptionEditText = findViewById(R.id.project_description);
        dueDateEditText = findViewById(R.id.due_date);
        priorityLevelSpinner = findViewById(R.id.priority_level);
        employerAssignedEditText = findViewById(R.id.employer_assigned);
        saveButton = findViewById(R.id.save_button);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolBarCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Project");

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
        String projectName = projectNameEditText.getText().toString();
        String projectDescription = projectDescriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();
        String priorityLevel = priorityLevelSpinner.getSelectedItem().toString();
        String employerAssigned = employerAssignedEditText.getText().toString();

        // JSON to hold data
        JSONObject projectData = new JSONObject();
        try {
            projectData.put("name", projectName);
            projectData.put("description", projectDescription);
            projectData.put("dueDate", dueDate);
            projectData.put("priority", priorityLevel);
            projectData.put("assignedTo", employerAssigned);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create project data", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(createProject.this, "Project created successfully!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error creating project
                        Toast.makeText(createProject.this, "Error creating project: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //For permissions this will be chang depending on users (Admin or Employer or Employee) to make sure send back to right page
            Intent intent = new Intent(createProject.this, projectActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
