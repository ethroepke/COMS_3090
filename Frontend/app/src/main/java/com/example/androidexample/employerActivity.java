package com.example.androidexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidexample.R;
import com.example.androidexample.loginActivity;

import java.util.concurrent.TimeUnit;

public class employerActivity extends AppCompatActivity {

    // Check-In/Check-Out variables
    private LinearLayout checkInOutBox;
    private TextView checkInOutText;
    private boolean isCheckedIn = false;
    private long startTime;
    private Handler handler = new Handler();
    private Runnable timerRunnable;
    private TextView timerTextView; // Optional: To display timer

    // Next Shift and Payday sections
    private LinearLayout nextShiftBox, paydayBox;
    private ImageButton nextShiftArrow, paydayArrow;
    private boolean isNextShiftExpanded = false;
    private boolean isPaydayExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer);

        // Initialize Views
        checkInOutBox = findViewById(R.id.checkInOutBox);
        checkInOutText = findViewById(R.id.checkInOutText);

        // Initialize Navigation Buttons
        initializeNavigationButtons();

        // Initialize Check-In/Check-Out functionality
        checkInOutBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCheckInOut();
            }
        });

        // Initialize Next Shift Dropdown
        nextShiftBox = findViewById(R.id.nextShiftBox);
        nextShiftArrow = findViewById(R.id.nextShiftArrow);
        nextShiftBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleNextShiftDropdown();
            }
        });

        // Initialize Payday Dropdown
        paydayBox = findViewById(R.id.paydayBox);
        paydayArrow = findViewById(R.id.paydayArrow);
        paydayBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePaydayDropdown();
            }
        });
    }

    private void initializeNavigationButtons() {
        // Project Status
        Button projectStatus = findViewById(R.id.projectStatus);
        projectStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Assign Project
        Button assignProject = findViewById(R.id.assignProject);
        assignProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Employee Attendance
        Button employeeAttendance = findViewById(R.id.employeeAttendance);
        employeeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Employee Status
        Button employeeStatus = findViewById(R.id.employeeStatus);
        employeeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Time Off Menu
        Button timeOffMenu = findViewById(R.id.timeOffMenu);
        timeOffMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Contract
        Button contract = findViewById(R.id.contract);
        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Profile
        Button profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Tasks
        Button tasks = findViewById(R.id.tasks);
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Time Tracking
        Button timeTracking = findViewById(R.id.timeTracking);
        timeTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Tax/Pay
        Button taxPay = findViewById(R.id.taxPay);
        taxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toggleCheckInOut() {
        if (!isCheckedIn) {
            // Check In
            isCheckedIn = true;
            checkInOutText.setText("Check Out");
            startTime = System.currentTimeMillis();

            Toast.makeText(this, "Checked In", Toast.LENGTH_SHORT).show();

            // Optionally, start a visual timer
            // startTimer();

        } else {
            // Check Out
            isCheckedIn = false;
            checkInOutText.setText("Check In");
            long endTime = System.currentTimeMillis();
            long workedMillis = endTime - startTime;

            // Calculate hours, minutes, seconds
            long hours = TimeUnit.MILLISECONDS.toHours(workedMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(workedMillis) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(workedMillis) % 60;

            String workedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            // Show AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Work Summary")
                    .setMessage("You worked from " + formatTime(startTime) + " to " + formatTime(endTime) +
                            "\nTotal Hours: " + workedTime)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    })
                    .show();

            Toast.makeText(this, "Checked Out", Toast.LENGTH_SHORT).show();

            // Optionally, stop the visual timer
            // stopTimer();
        }
    }

    private String formatTime(long millis) {
        // Convert milliseconds to HH:mm format
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(new java.util.Date(millis));
    }

    // Optional: Timer display methods
    /*
    private void startTimer() {
        timerTextView = findViewById(R.id.timerTextView);
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;
                timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timerRunnable);
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable);
        timerTextView.setText("00:00:00");
    }
    */

    private void toggleNextShiftDropdown() {
        if (isNextShiftExpanded) {
            // Collapse
            nextShiftBox.setOrientation(LinearLayout.VERTICAL);
            nextShiftArrow.setImageResource(R.drawable.arrowdown);
            isNextShiftExpanded = false;
        } else {
            // Expand
            nextShiftBox.setOrientation(LinearLayout.VERTICAL);
            nextShiftArrow.setImageResource(R.drawable.arrowdown);
            // TODO: Add code to show more upcoming shifts
            isNextShiftExpanded = true;
        }
    }

    private void togglePaydayDropdown() {
        if (isPaydayExpanded) {
            // Collapse
            paydayBox.setOrientation(LinearLayout.VERTICAL);
            paydayArrow.setImageResource(R.drawable.arrowdown);
            isPaydayExpanded = false;
        } else {
            // Expand
            paydayBox.setOrientation(LinearLayout.VERTICAL);
            paydayArrow.setImageResource(R.drawable.arrowdown);
            // TODO: Add code to show past paychecks
            isPaydayExpanded = true;
        }
    }
}
