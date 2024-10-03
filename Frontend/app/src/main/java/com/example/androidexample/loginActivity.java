package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;






public class loginActivity extends AppCompatActivity {

    private TextView messageText;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;
    private ImageButton showPassword;
    private Button forgotPasswordButton;
    private Button newUserButton;


    boolean isPasswordVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        /*Initialize UI Elements*/
        submitButton = findViewById(R.id.submitButton);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        messageText = findViewById(R.id.mainMessage);
        showPassword = findViewById(R.id.showPassword);
        forgotPasswordButton = findViewById(R.id.forgotButton);
        newUserButton = findViewById(R.id.newUserJoin);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if(username.equals("Employer") && password.equals("Boss123")){
                    Intent intent = new Intent(loginActivity.this, employerActivity.class);
                    startActivity(intent);
                } else if (username.equals("Employee") && password.equals("Associate123")) {
                    Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                    startActivity(intent);
                }
                else{
                    messageText.setText("Invalid credentials, try again.");
                }
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible){
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassword.setImageResource(R.drawable.eyehide);
                }
                else{
                    passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassword.setImageResource(R.drawable.eyeshow);
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, forgotpasswordActivity.class);
                startActivity(intent);
            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, forgotpasswordActivity.class);
                startActivity(intent);
            }
        });


    }
}
