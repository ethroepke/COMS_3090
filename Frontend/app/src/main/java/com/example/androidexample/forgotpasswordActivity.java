package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class forgotpasswordActivity extends AppCompatActivity {

    private Button back_button;
    private EditText email_input;
    private Button submit_button;
    private TextView messageText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        back_button = findViewById(R.id.backButton);
        email_input = findViewById(R.id.usernameInput);
        submit_button = findViewById(R.id.submitButton);
        messageText = findViewById(R.id.messageText);

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
                String email = email_input.getText().toString().trim();

                if(email.equals("admin123@gmail.com")){
                    //SEND RESET PASSWORD TO EMAIL

                    //Send user to email sent page
                    Intent intent = new Intent(forgotpasswordActivity.this, forgotPasswordVerifiedActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                }

                //Email not in database send message letting them know
                else{
                    messageText.setText("An account could not be found for the given email ID");
                }
            }
        });
    }
}
