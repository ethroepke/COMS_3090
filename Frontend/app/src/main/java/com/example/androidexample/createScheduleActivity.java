package com.example.androidexample;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidexample.adminActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class createScheduleActivity extends AppCompatActivity {
    private EditText dateEditText;
    private TextView startTimeText, endTimeText;
    private Spinner names;
    private ArrayList<String> teamMembers;
    private ArrayAdapter<String> adapter;
    private RequestQueue requestQueue;
    private Button saveButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createschedule);

        dateEditText = findViewById(R.id.dateScheduled);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);
        names = findViewById(R.id.nameSearch);
        saveButton = findViewById(R.id.saveButton);

        Toolbar toolbar = findViewById(R.id.toolBarScheduler);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Schedule");

        requestQueue = Volley.newRequestQueue(this);

        // Spinner to call names
        spinnerNames();

        // Set up the date picker for the EditText
        dateEditText.setOnClickListener(v -> {
            // Get Current Date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Open DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(createScheduleActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        dateEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                    }, year, month, day);

            datePickerDialog.show();
        });

        startTimeText.setOnClickListener(v -> showTimePickerDialog(startTimeText));
        endTimeText.setOnClickListener(v -> showTimePickerDialog(endTimeText));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createScheduleActivity.this, selfServiceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showTimePickerDialog(TextView timeText) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Open TimePickerDialog with 12-hour format
        TimePickerDialog timePickerDialog = new TimePickerDialog(createScheduleActivity.this,
                (view, selectedHour, selectedMinute) -> {
                    boolean isAM = selectedHour < 12;
                    int hourIn12HourFormat = selectedHour % 12;
                    if (hourIn12HourFormat == 0) {
                        hourIn12HourFormat = 12;
                    }

                    // Format the time and set it to the TextView
                    String formattedTime = String.format("%02d:%02d %s", hourIn12HourFormat, selectedMinute, isAM ? "AM" : "PM");
                    timeText.setText(formattedTime);
                }, hour, minute, false); // false for 12-hour format

        timePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // For permissions this will be changed depending on users (Admin or Employer or Employee) to make sure send back to the right page
            Intent intent = new Intent(createScheduleActivity.this, adminActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Spinner for all names from the database
    private void spinnerNames() {
        teamMembers = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamMembers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        names.setAdapter(adapter);
        namesInTeam();
    }

    // API for names to add into the spinner
    private void namesInTeam() {
        // Add the proper URL when the backend is finished
        String url = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            teamMembers.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject teamMember = response.getJSONObject(i);
                                String name = teamMember.getString("name");
                                teamMembers.add(name);
                            }
                            adapter.notifyDataSetChanged(); // Update the Spinner with new data
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(createScheduleActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(createScheduleActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}



