package com.example.androidexample;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class createScheduleActivity extends AppCompatActivity {
    EditText dateEditText;
    TextView startTimeText, endTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createschedule);

        dateEditText = findViewById(R.id.dateScheduled);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);

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
                        // Format the date and set it to EditText
                        dateEditText.setText((selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear);
                    }, year, month, day);

            datePickerDialog.show();
        });

        // Set up the time picker for the start time TextView
        startTimeText.setOnClickListener(v -> showTimePickerDialog(startTimeText));

        // Set up the time picker for the end time TextView
        endTimeText.setOnClickListener(v -> showTimePickerDialog(endTimeText));
    }

    private void showTimePickerDialog(TextView timeText) {
        // Get Current Time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        boolean isAM = calendar.get(Calendar.AM_PM) == Calendar.AM;

        // Open TimePickerDialog with 12-hour format
        TimePickerDialog timePickerDialog = new TimePickerDialog(createScheduleActivity.this,
                (view, selectedHour, selectedMinute) -> {
                    // Format the time and set it to the TextView
                    String formattedTime = String.format("%02d:%02d %s",
                            selectedHour == 0 ? 12 : selectedHour, selectedMinute,
                            selectedHour < 12 ? "AM" : "PM");
                    timeText.setText(formattedTime);
                }, hour, minute, false); // false for 12-hour format

        timePickerDialog.show();
    }
}


