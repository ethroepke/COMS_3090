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

    // Replace with URL when they finish
    private static final String URL = "http://coms-3090-046.class.las.iastate.edu:8080/api/project/create";


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
        fetchEmployerNames();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProject();
            }
        });
    }

    //API to save Project
    private void saveProject() {
        // Retrieve user input
        String projectName = projectNameEditText.getText().toString();
        String description = projectDescriptionEditText.getText().toString();
        String dueDate = dueDateEditText.getText().toString();
        String priority = priorityLevelSpinner.getSelectedItem().toString();
        String assignedTo = employerAssigned.getSelectedItem().toString();

        // JSON to hold data
        JSONObject projectData = new JSONObject();
        try {
            projectData.put("name", projectName);
            projectData.put("description", description);
            projectData.put("dueDate", dueDate);
            projectData.put("priority", priority);
            projectData.put("assignedTo", assignedTo);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create project data", Toast.LENGTH_SHORT).show();
            return;
        }

        // JSON to send data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
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

    //API to fetch all names
    private void fetchEmployerNames() {
        String fetch_url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, fetch_url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        employerNamesList.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String name = response.getString(i);
                                employerNamesList.add(name);
                            }
                            adapter2.notifyDataSetChanged();
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

        requestQueue.add(jsonArrayRequest);
    }



}

