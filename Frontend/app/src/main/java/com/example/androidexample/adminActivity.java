package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidexample.R;
import com.example.androidexample.loginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class adminActivity extends AppCompatActivity {
    private boolean isClockedIn = false;

    private long clockInTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    private FrameLayout borderChange;

    private Button checkButton;
    private Button projectStatButton;
    private Button assignProjButton;
    private Button employeeAttendanceButton;
    private Button employeeStatButton;
    private Button messageButton;
    private Button performanceReviewButton;
    private Button profileButton;
    private Button projButton;
    private Button selfServiceButton;
    private Button payButton;
    private TextView checkInMsg;
    private Chronometer timeClockMsg;

    private SearchView searchView;
    private Button searchButton;
    private TextView resultTextView;

    private List<String> sampleData;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        borderChange = findViewById(R.id.frameChange);
        checkButton = findViewById(R.id.checkButton);
        checkInMsg = findViewById(R.id.checkText);
        timeClockMsg = findViewById(R.id.timeText);
        projectStatButton = findViewById(R.id.projStatusButton);
        assignProjButton = findViewById(R.id.assignProjButton);
        employeeAttendanceButton = findViewById(R.id.employeeAttendanceButton);
        employeeStatButton = findViewById(R.id.employeeStatusbutton);
        messageButton = findViewById(R.id.messageButton);
        performanceReviewButton = findViewById(R.id.performanceButton);
        profileButton = findViewById(R.id.profileButton);
        projButton = findViewById(R.id.projButton);
        selfServiceButton = findViewById(R.id.selfServiceButton);
        payButton = findViewById(R.id.payButton);
        searchView = findViewById(R.id.searchView);
        searchButton = findViewById(R.id.searchButton);
        resultTextView = findViewById(R.id.resultTextView);

        initializeSampleData();

        // Search button listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        //Clock In/Out functionality
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayerDrawable layerDrawable = (LayerDrawable) borderChange.getBackground();
                Drawable borderDrawable = layerDrawable.getDrawable(0);

                if (borderDrawable instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) borderDrawable;

                    if (isClockedIn) {
                        gradientDrawable.setStroke(15, Color.GRAY);
                        checkInMsg.setText("Clock In");

                        timeClockMsg.stop();
                        timeClockMsg.setBase(SystemClock.elapsedRealtime());

                        String clockOutTime = dateFormat.format(new Date());
                        showClockOutPopup(clockInTime, System.currentTimeMillis() - clockInTime, clockOutTime);
                    } else {
                        gradientDrawable.setStroke(15, Color.GREEN);
                        checkInMsg.setText("Clock Out");

                        timeClockMsg.setBase(SystemClock.elapsedRealtime());
                        timeClockMsg.start();

                        clockInTime = System.currentTimeMillis();
                    }

                    isClockedIn = !isClockedIn;
                }
            }
        });

        //All Intents for buttons to new pages down below
        projectStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        assignProjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        employeeAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        employeeStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, messageActivity.class);
                startActivity(intent);
            }
        });
        performanceReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        projButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, projectActivity.class);
                startActivity(intent);
            }
        });
        selfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Initialize sample data for searching
    private void initializeSampleData() {
        sampleData = new ArrayList<>();
        sampleData.add("Project A");
        sampleData.add("Project B");
        sampleData.add("Employee 1");
        sampleData.add("Employee 2");
        sampleData.add("Attendance Report");
        sampleData.add("Performance Review");
    }

    // Search functionality
    private void performSearch() {
        String query = searchView.getQuery().toString().toLowerCase();
        if (!query.isEmpty()) {
            StringBuilder results = new StringBuilder("Search Results:\n");
            boolean found = false;

            for (String item : sampleData) {
                if (item.toLowerCase().contains(query)) {
                    results.append(item).append("\n");
                    found = true;
                }
            }

            if (found) {
                resultTextView.setText(results.toString());
                resultTextView.setVisibility(View.VISIBLE); // Show results
            } else {
                resultTextView.setText("No results found for: " + query);
                resultTextView.setVisibility(View.VISIBLE); // Show no results found
            }
        } else {
            resultTextView.setText("Please enter a search term.");
            resultTextView.setVisibility(View.VISIBLE); // Show prompt
        }
    }



    //Pop up page to show hours worked after clocking out
    private void showClockOutPopup(long clockInTime, long elapsedMillis, String clockOutTime) {
        long elapsedHours = elapsedMillis / 3600000;
        long elapsedMinutes = (elapsedMillis % 3600000) / 60000;

        String clockInTimeFormatted = dateFormat.format(new Date(clockInTime));
        String workedHours = String.format(Locale.getDefault(), "%02d:%02d", elapsedHours, elapsedMinutes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clock Out Summary");
        builder.setMessage("Clock In Time: " + clockInTimeFormatted +
                "\nClock Out Time: " + clockOutTime +
                "\nHours Worked: " + workedHours);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

