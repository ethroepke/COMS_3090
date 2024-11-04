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

public class projectActivity extends AppCompatActivity {
    private LinearLayout projectListLayout;
    private List<Map<String, String>> projectList;
    private Button createProjButton;

    // Replace when backend finishes
    private static final String API_URL = "";

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
        fetchProjects();
    }

    private void fetchProjects() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseProjectData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        TextView errorText = new TextView(projectActivity.this);
                        errorText.setText("Error fetching projects: " + error.getMessage());
                        projectListLayout.addView(errorText);
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void parseProjectData(JSONArray projectsArray) {
        projectList.clear();
        for (int i = 0; i < projectsArray.length(); i++) {
            try {
                JSONObject project = projectsArray.getJSONObject(i);
                String name = project.getString("name");
                String description = project.getString("description");
                String assignedTo = project.getString("assignedTo");
                String dueDate = project.getString("dueDate");
                String importance = project.getString("importance");

                addProjectToList(name, description, assignedTo, dueDate, importance);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addProjectToList(String name, String description, String assignedTo, String dueDate, String importance) {
        Map<String, String> newProject = new HashMap<>();
        newProject.put("name", name);
        newProject.put("description", description);
        newProject.put("assignedTo", assignedTo);
        newProject.put("dueDate", dueDate);
        newProject.put("importance", importance);

        projectList.add(newProject);
        displayProjects();
    }

    private void displayProjects() {
        projectListLayout.removeAllViews();
        if (projectList.isEmpty()) {
            TextView noProjectsText = new TextView(this);
            noProjectsText.setText("No projects available. Please create a new project.");
            noProjectsText.setTextSize(18);
            projectListLayout.addView(noProjectsText);
        } else {
            for (Map<String, String> project : projectList) {
                View projectView = createProjectView(project);
                projectListLayout.addView(projectView);
            }
        }
    }

    private View createProjectView(Map<String, String> project) {
        LinearLayout projectView = new LinearLayout(this);
        projectView.setOrientation(LinearLayout.VERTICAL);
        TextView nameText = new TextView(this);
        nameText.setText("Name: " + project.get("name"));
        TextView descText = new TextView(this);
        descText.setText("Description: " + project.get("description"));
        TextView assignedText = new TextView(this);
        assignedText.setText("Assigned to: " + project.get("assignedTo"));
        TextView dueText = new TextView(this);
        dueText.setText("Due Date: " + project.get("dueDate"));
        TextView importanceText = new TextView(this);
        importanceText.setText("Importance: " + project.get("importance"));

        projectView.addView(nameText);
        projectView.addView(descText);
        projectView.addView(assignedText);
        projectView.addView(dueText);
        projectView.addView(importanceText);

        return projectView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //For permissions this will be chang depending on users (Admin or Employer or Employee) to make sure send back to right page
            Intent intent = new Intent(projectActivity.this, adminActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





