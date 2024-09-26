package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class employeeActivity extends AppCompatActivity {

    private TextView checkText;     // define message textview variable
    private Button counterButton;     // define counter button variable

    private Button checkButton;         // define button for check in/out
    private boolean isCheckedIn = false;

    private TextView timerText;
    private long checked_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);             // link to Main activity XML

        checkButton = findViewById(R.id.check_btn);
        checkText = findViewById(R.id.check_txt);
        timerText = findViewById(R.id.check_clk_txt);

        int check_green = Color.rgb(10, 100, 10);
        int check_red = Color.rgb(100, 10, 10);


        checkText.setTextSize(32.0F);
        checkButton.setBackgroundColor(check_green);


        /* click listener on counter button pressed */


        //runs without a timer by reposting this handler at the end of the runnable
        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {

                long total_time_ms = System.currentTimeMillis() - checked_time;
                int seconds = (int) total_time_ms / 1000;
                int minutes = (seconds / 60);
                int hours = seconds / 60 / 60;
                timerText.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
                timerHandler.postDelayed(this, 500);
            }
        };


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCheckedIn) {
                    timerText.setVisibility(View.VISIBLE);
                    checked_time = System.currentTimeMillis();
                    checkText.setText("Check out");
                    isCheckedIn = true;
                    checked_time = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    checkButton.setBackgroundColor(check_red);
                } else {
                    checkText.setText("Check in");
                    isCheckedIn = false;
                    checkButton.setBackgroundColor(check_green);
                    timerText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}