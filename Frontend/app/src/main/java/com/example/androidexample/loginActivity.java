package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.example.androidexample.EmployerActivity;



public class loginActivity extends AppCompatActivity {

    private TextView messageText;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /*Initialize UI Elements*/
        submitButton = findViewById(R.id.submitButton);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        messageText = findViewById(R.id.mainMessage);


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
    }
}
