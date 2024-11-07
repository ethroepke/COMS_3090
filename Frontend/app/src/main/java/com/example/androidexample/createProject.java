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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class createProject extends AppCompatActivity {

    private EditText projectNameEditText;
    private EditText projectDescriptionEditText;
    private EditText dueDateEditText;
    private Spinner priorityLevelSpinner;
    private Spinner employerAssigned;
    private Button saveButton;

    private ArrayAdapter<String> adapter2;
    private ArrayList<String> employerNamesList;

    private RequestQueue requestQueue;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createproject);

        projectNameEditText = findViewById(R.id.project_name);
        projectDescriptionEditText = findViewById(R.id.project_description);
        dueDateEditText = findViewById(R.id.due_date);
        priorityLevelSpinner = findViewById(R.id.priority_level);
        employerAssigned = findViewById(R.id.employer_Assigned);
        saveButton = findViewById(R.id.save_button);

        employerNamesList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Toolbar toolbar = findViewById(R.id.toolBarCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Project");

        // Priority level dropdown
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorityLevelSpinner.setAdapter(adapter);

        // employer search dropdown
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employerNamesList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employerAssigned.setAdapter(adapter2);

        // Fetch employers from API
        fetchEmployers();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save project data
                saveProject();
            }
        });
    }

    private void fetchEmployers() {
        String url = "hhttp://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/usernames";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            employerNamesList.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject employer = response.getJSONObject(i);
                                String username = employer.getString("username");
                                employerNamesList.add(username);
                            }

                            // Update the spinner with the employer names
                            adapter2.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(createProject.this, "Failed to load employers", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(createProject.this, "Error fetching employers: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void saveProject() {
        String projectName = projectNameEditText.getText().toString();
        String projectDescription = projectDescriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();
        String priorityLevel = priorityLevelSpinner.getSelectedItem().toString();
        String assignedEmployer = employerAssigned.getSelectedItem().toString();

        // Validate inputs
        if (projectName.isEmpty() || projectDescription.isEmpty() || dueDate.isEmpty() || assignedEmployer.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create project JSON object
        JSONObject projectData = new JSONObject();
        try {
            projectData.put("name", projectName);
            projectData.put("description", projectDescription);
            projectData.put("due_date", dueDate);
            projectData.put("priority_level", priorityLevel);
            projectData.put("assigned_employer", assignedEmployer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // POST request to save the project
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/project/create";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, projectData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(createProject.this, "Project saved successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(createProject.this, projectEmployerActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Toast.makeText(createProject.this, "Error saving project: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add request to the queue
        requestQueue.add(jsonObjectRequest);
    }
}


