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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class employerActivity extends AppCompatActivity {
    private boolean isClockedIn = false;
    private long clockInTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    //CheckIn and checkOut border color change
    private FrameLayout borderChange;

    //Buttons for changing screens
    private Button checkButton;
    /*
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

    //ImageButtons for shifts and pay
    private ImageButton shiftArrow;
    private ImageButton payArrow;

    //Text that will get modified when GET users info
    private TextView welcomeMsg;
    */
    private TextView checkInMsg;
    private Chronometer timeClockMsg;
    /*
    private TextView shiftDateMsg;
    private TextView shiftHoursMsg;
    private TextView assignedProjMsg;
    private TextView extraShiftMsg;
    private TextView extraHoursMsg;
    private TextView extraProjMsg;
    private TextView payMsg;
    private TextView hoursWorkedMsg;
    private TextView payDateMsg;
    private TextView extraPayMsg;
    private TextView extraHoursWorkedMsg;
    private TextView extraPayDateMsg;

    //Placeholders for drop down arrows
    private LinearLayout shiftDetails;
    private LinearLayout payDetails;
    */


    @SuppressLint({"WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer);

        borderChange = findViewById(R.id.frameChange);
        checkButton = findViewById(R.id.checkButton);
        /*
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
        shiftArrow = findViewById(R.id.downArrowShift);
        payArrow = findViewById(R.id.downArrowPay);
        welcomeMsg = findViewById(R.id.welcomeMessage);
        */
        checkInMsg = findViewById(R.id.checkText);
        timeClockMsg = findViewById(R.id.timeText);
        /*
        shiftDateMsg = findViewById(R.id.nextShiftText);
        shiftHoursMsg = findViewById(R.id.shiftHoursText);
        assignedProjMsg = findViewById(R.id.assignedProjText);
        extraShiftMsg = findViewById(R.id.moreShiftText);
        extraHoursMsg = findViewById(R.id.moreHoursText);
        extraProjMsg = findViewById(R.id.moreProjText);
        payMsg = findViewById(R.id.payText);
        hoursWorkedMsg = findViewById(R.id.hoursWorkedText);
        payDateMsg = findViewById(R.id.payDateText);
        extraPayMsg = findViewById(R.id.morePaytext);
        extraHoursWorkedMsg = findViewById(R.id.moreHoursWorkedText);
        extraPayDateMsg = findViewById(R.id.morePayDateText);

        shiftDetails = findViewById(R.id.shiftDetails);
        payDetails = findViewById(R.id.payDetails);
        */

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayerDrawable layerDrawable = (LayerDrawable) borderChange.getBackground();
                Drawable borderDrawable = layerDrawable.getDrawable(0);

                if (borderDrawable instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) borderDrawable;

                    if (isClockedIn) {
                        // Clock out: Stop the timer, set border to gray, reset Chronometer, and show clock-out popup
                        gradientDrawable.setStroke(15, Color.GRAY);
                        checkInMsg.setText("Clock In");

                        timeClockMsg.stop();  // Stop the chronometer
                        timeClockMsg.setBase(SystemClock.elapsedRealtime());  // Reset to 00:00

                        // Get clock-out time and show popup with details
                        String clockOutTime = dateFormat.format(new Date());
                        showClockOutPopup(clockInTime, SystemClock.elapsedRealtime() - timeClockMsg.getBase(), clockOutTime);
                    } else {
                        // Clock in: Start timer, set border to green, and record clock-in time
                        gradientDrawable.setStroke(15, Color.GREEN);
                        checkInMsg.setText("Clock Out");

                        timeClockMsg.setBase(SystemClock.elapsedRealtime());  // Start from 00:00
                        timeClockMsg.start();  // Start the chronometer

                        clockInTime = System.currentTimeMillis();
                    }

                    // Toggle the clock-in state
                    isClockedIn = !isClockedIn;
                }
            }
        });


        /*
        // Intents for all pages
        projectStatButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, projectStatusActivity.class)));
        assignProjButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, assignProjectActivity.class)));
        employeeAttendanceButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, employeeAttendanceActivity.class)));
        employeeStatButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, employeeStatusActivity.class)));
        messageButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, messageActivity.class)));
        performanceReviewButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, performanceReviewActivity.class)));
        profileButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, profileActivity.class)));
        projButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, projectActivity.class)));
        selfServiceButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, selfServiceActivity.class)));
        payButton.setOnClickListener(v -> startActivity(new Intent(employerActivity.this, payActivity.class)));
        */
    }

    private void showClockOutPopup(long clockInTime, long elapsedMillis, String clockOutTime) {
        // Format elapsed time in hours and minutes
        long elapsedHours = (elapsedMillis / 3600000);
        long elapsedMinutes = (elapsedMillis % 3600000) / 60000;

        String clockInTimeFormatted = dateFormat.format(new Date(clockInTime));
        String workedHours = String.format(Locale.getDefault(), "%02d:%02d", elapsedHours, elapsedMinutes);

        // Create a dialog with clock-in/out times and hours worked
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clock Out Summary");
        builder.setMessage("Clock In Time: " + clockInTimeFormatted +
                "\nClock Out Time: " + clockOutTime +
                "\nHours Worked: " + workedHours);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

}
