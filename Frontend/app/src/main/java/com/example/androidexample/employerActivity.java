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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class employerActivity extends AppCompatActivity {
    private boolean isClockedIn = false;
    private boolean isShiftDetailsVisible = false;
    private boolean isPayDetailsVisible = false;

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
    /*
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
    */

    private ImageView shiftArrow;
    private ImageView payArrow;
    private LinearLayout shiftDetails;
    private LinearLayout payDetails;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer);

        borderChange = findViewById(R.id.frameChange);
        checkButton = findViewById(R.id.checkButton);
        shiftArrow = findViewById(R.id.downArrowShift);
        payArrow = findViewById(R.id.downArrowPay);
        checkInMsg = findViewById(R.id.checkText);
        timeClockMsg = findViewById(R.id.timeText);
        shiftDetails = findViewById(R.id.shiftDetails);
        payDetails = findViewById(R.id.payDetails);
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


        /*
        welcomeMsg = findViewById(R.id.welcomeMessage);
        */



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
                        showClockOutPopup(clockInTime, SystemClock.elapsedRealtime() - timeClockMsg.getBase(), clockOutTime);
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

        //Shift arrow functionality
        shiftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShiftDetails();
            }
        });

        //Pay arrow functionality
        payArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePayDetails();
            }
        });

        //All Intents for buttons to new pages down below
        projectStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        assignProjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        employeeAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        employeeStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        performanceReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        projButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        selfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
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

    //Toggle shift details when arrow pressed
    private void toggleShiftDetails() {
        if (isShiftDetailsVisible) {
            shiftDetails.setVisibility(View.GONE);
            shiftArrow.setImageResource(R.drawable.arrowdown);
        } else {
            shiftDetails.setVisibility(View.VISIBLE);
            shiftArrow.setImageResource(R.drawable.uparrow);
        }
        isShiftDetailsVisible = !isShiftDetailsVisible;
    }

    //Toggle pay details when arrow pressed
    private void togglePayDetails() {
        if (isPayDetailsVisible) {
            payDetails.setVisibility(View.GONE);
            payArrow.setImageResource(R.drawable.arrowdown);
        } else {
            payDetails.setVisibility(View.VISIBLE);
            payArrow.setImageResource(R.drawable.uparrow);
        }
        isPayDetailsVisible = !isPayDetailsVisible;
    }
}

