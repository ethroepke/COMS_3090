package com.example.androidexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class joinNowActivity extends AppCompatActivity {

    private Button backButton;
    private Button signUp;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText verifyPassword;
    private TextView nameErrorMessage;
    private TextView emailErrorMessage;
    private TextView verifyPasswordErrorMessage;

    boolean isNameFilled = false;
    boolean isEmailFilled = false;
    boolean isPasswordFilled = false;
    boolean isVerifyPasswordFilled = false;
    boolean isPasswordsMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinnow);

        backButton = findViewById(R.id.backButton);
        signUp = findViewById(R.id.joinNow);
        name = findViewById(R.id.enterName);
        email= findViewById(R.id.emailInput);
        password = findViewById(R.id.enterPassword);
        verifyPassword = findViewById(R.id.reenterPassword);
        nameErrorMessage = findViewById(R.id.nameError);
        emailErrorMessage = findViewById(R.id.emailError);
        verifyPasswordErrorMessage = findViewById(R.id.verifyPasswordError);


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
                String passwordFilled = password.getText().toString().trim();
                String verifyPasswordFilled = verifyPassword.getText().toString().trim();

                //Check if name includes first and last name
                if (isValidFullName(nameFilled)){
                    isNameFilled = true;
                }
                else{
                    isNameFilled = false;
                    nameErrorMessage.setText("Enter a valid full name");
                }

                //Check if email is valid
                if (isValidEmail(emailFilled)){
                    isEmailFilled = true;
                }
                else{
                    isEmailFilled = false;
                    emailErrorMessage.setText("Enter a valid email");
                }

                //Check to make sure password has minimum requiremnts
                if(isValidPassword(passwordFilled)){
                    isPasswordFilled = true;
                }
                else{
                    showAlertDialog("Error", "Password must be at least 8 characters long, contain 1 uppercase letter, 1 number, and 1 special character");
                }

                //Check to see if passwords match
                if(passwordFilled.equals(verifyPasswordFilled)){
                    isPasswordsMatch = true;
                }
                else{
                    isPasswordsMatch = false;
                    verifyPasswordErrorMessage.setText("Passwords do not match");
                }

                if(isNameFilled && isEmailFilled && isPasswordFilled && isPasswordsMatch){
                    Intent intent = new Intent(joinNowActivity.this, loginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean isValidFullName(String nameFilled) {
        // Check if full name is not empty and contains at least one space (indicating two words)
        return !TextUtils.isEmpty(nameFilled) && nameFilled.contains(" ") && nameFilled.trim().split("\\s+").length >= 2;
    }

    private boolean isValidEmail(String emailFilled) {
        //check if email matches legit email addresses
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
