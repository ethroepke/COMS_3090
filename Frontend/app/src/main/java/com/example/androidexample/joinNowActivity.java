package com.example.androidexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class joinNowActivity extends AppCompatActivity {

    private Button backButton;
    private Button signUp;
    private EditText name;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText verifyPassword;
    private TextView nameErrorMessage;
    private TextView emailErrorMessage;
    private TextView verifyPasswordErrorMessage;
    private ImageButton showPassword1;
    private ImageButton showPassword2;

    boolean isPasswordVisible1 = false;
    boolean isPasswordVisible2 = false;



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinnow);

        backButton = findViewById(R.id.backButton);
        signUp = findViewById(R.id.joinNow);
        name = findViewById(R.id.enterName);
        email = findViewById(R.id.emailInput);
        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.enterPassword);
        verifyPassword = findViewById(R.id.reenterPassword);
        nameErrorMessage = findViewById(R.id.nameError);
        emailErrorMessage = findViewById(R.id.emailError);
        verifyPasswordErrorMessage = findViewById(R.id.verifyPasswordError);
        showPassword1 = findViewById(R.id.showPassword1);
        showPassword2 = findViewById(R.id.showPassword2);


        // Set initial visibility of error messages to gone
        nameErrorMessage.setVisibility(View.GONE);
        emailErrorMessage.setVisibility(View.GONE);
        verifyPasswordErrorMessage.setVisibility(View.GONE);

        // Add TextWatcher for name EditText
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidFullName(s.toString())) {
                    nameErrorMessage.setVisibility(View.GONE);
                } else {
                    nameErrorMessage.setVisibility(View.VISIBLE);
                    nameErrorMessage.setText("Enter a valid full name");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add TextWatcher for email EditText
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidEmail(s.toString())) {
                    emailErrorMessage.setVisibility(View.GONE);
                } else {
                    emailErrorMessage.setVisibility(View.VISIBLE);
                    emailErrorMessage.setText("Enter a valid email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add TextWatcher for password EditText
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidPassword(s.toString())) {
                    // Hide any alert or error related to password
                } else {
                    // Optionally display an error message if needed
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add TextWatcher for verify password EditText
        verifyPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(password.getText().toString())) {
                    verifyPasswordErrorMessage.setVisibility(View.GONE);
                } else {
                    verifyPasswordErrorMessage.setVisibility(View.VISIBLE);
                    verifyPasswordErrorMessage.setText("Passwords do not match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(joinNowActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameFilled = name.getText().toString().trim();
                String emailFilled = email.getText().toString().trim();
                String usernameFilled = username.getText().toString().trim();
                String passwordFilled = password.getText().toString().trim();
                String verifyPasswordFilled = verifyPassword.getText().toString().trim();

                // Check if name includes first and last name
                if (!isValidFullName(nameFilled)) {
                    nameErrorMessage.setText("Enter a valid full name");
                    nameErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    nameErrorMessage.setVisibility(View.GONE);
                }

                // Check if email is valid
                if (!isValidEmail(emailFilled)) {
                    emailErrorMessage.setText("Enter a valid email");
                    emailErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    emailErrorMessage.setVisibility(View.GONE);
                }

                // Check to make sure password has minimum requirements
                if (!isValidPassword(passwordFilled)) {
                    showAlertDialog("Error", "Password must be at least 8 characters long, contain 1 uppercase letter, 1 number, and 1 special character");
                    return; // Exit the method if password is invalid
                }

                // Check to see if passwords match
                if (!passwordFilled.equals(verifyPasswordFilled)) {
                    verifyPasswordErrorMessage.setText("Passwords do not match");
                    verifyPasswordErrorMessage.setVisibility(View.VISIBLE);
                    return; // Exit the method if passwords do not match
                } else {
                    verifyPasswordErrorMessage.setVisibility(View.GONE);
                }

                // If all validations pass, proceed to loginActivity
                Intent intent = new Intent(joinNowActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });


        // Set initial input types for password fields to hidden
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        verifyPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Show and hide password for first line
        showPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible1){
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword1.setImageResource(R.drawable.eyehide);
                }
                else{
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword1.setImageResource(R.drawable.eyeshow);
                }
                isPasswordVisible1 = !isPasswordVisible1;
            }
        });

        //Show and hide password for second line
        showPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible2){
                    verifyPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword2.setImageResource(R.drawable.eyehide);
                }
                else{
                    verifyPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword2.setImageResource(R.drawable.eyeshow);
                }
                isPasswordVisible2 = !isPasswordVisible2;
            }
        });
    }

    private boolean isValidFullName(String nameFilled) {
        // Check if full name is not empty and contains at least one space (indicating two words)
        return !TextUtils.isEmpty(nameFilled) && nameFilled.contains(" ") && nameFilled.trim().split("\\s+").length >= 2;
    }

    private boolean isValidEmail(String emailFilled) {
        // Check if email matches legit email addresses
        return !TextUtils.isEmpty(emailFilled) && Patterns.EMAIL_ADDRESS.matcher(emailFilled).matches();
    }

    private boolean isValidPassword(String passwordFilled) {
        // Password must be at least 8 characters long, contain an uppercase letter, a number, and a special character
        return passwordFilled.length() >= 8
                && passwordFilled.matches(".*[A-Z].*")        // At least one uppercase letter
                && passwordFilled.matches(".*[0-9].*")        // At least one digit
                && passwordFilled.matches(".*[!@#\\$%^&*].*"); // At least one special character
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(joinNowActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

