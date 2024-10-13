package com.example.androidexample;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class payCheckModifyActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private EditText editHoursWorked, editPayRate, editBonusPay, editDeductibles;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paycheckmodify);

        // Initialize views
        userNameTextView = findViewById(R.id.userName);
        editHoursWorked = findViewById(R.id.editHoursWorked);
        editPayRate = findViewById(R.id.editPayRate);
        editBonusPay = findViewById(R.id.editBonusPay);
        editDeductibles = findViewById(R.id.editDeductibles);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        // Get the username from the intent or passed data
        String username = getIntent().getStringExtra("username");
        userNameTextView.setText(username);

        // Set up save button click listener
        saveChangesButton.setOnClickListener(view -> {
            String hoursWorked = editHoursWorked.getText().toString();
            String payRate = editPayRate.getText().toString();
            String bonusPay = editBonusPay.getText().toString();
            String deductibles = editDeductibles.getText().toString();

            // Validate input fields
            if (validateFields(hoursWorked, payRate, bonusPay, deductibles)) {
                // Perform save operation, sending data to the backend
                saveUserPayDetails(username, hoursWorked, payRate, bonusPay, deductibles);
            }
        });
    }

    // Function to validate input fields
    private boolean validateFields(String hoursWorked, String payRate, String bonusPay, String deductibles) {
        if (TextUtils.isEmpty(hoursWorked)) {
            Toast.makeText(this, "Please enter hours worked", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(payRate)) {
            Toast.makeText(this, "Please enter pay rate", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(bonusPay)) {
            Toast.makeText(this, "Please enter bonus pay", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(deductibles)) {
            Toast.makeText(this, "Please enter deductibles", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Simulated function to send updated pay details to the backend
    private void saveUserPayDetails(String username, String hoursWorked, String payRate, String bonusPay, String deductibles) {
        // Here you would send the data to your server (e.g., via a POST or PUT request using a library like Volley)

        // For now, just show a Toast message confirming the changes
        Toast.makeText(this, "Pay details updated for " + username, Toast.LENGTH_LONG).show();

        // Example of what would happen next after saving
        finish();  // Close this activity and go back to the previous screen
    }
}

