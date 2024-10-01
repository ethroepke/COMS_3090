package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class forgotpasswordActivity extends AppCompatActivity {

    private Button back_button;
    private EditText email_input;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        back_button = findViewById(R.id.backButton);
        email_input = findViewById(R.id.usernameInput);
        submit_button = findViewById(R.id.submitButton);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotpasswordActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = email_input.getText().toString();
            }
        });


    }




}
