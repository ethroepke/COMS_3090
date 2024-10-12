package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                //Calling in submit button to check email in other function
                checkEmail(email);

            }
        });
    }

    // Check if the email exists in the database
    private void checkEmail(String email) {
        // Construct the URL for checking the email
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/checkEmail";

        // Create a request
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // If the email exists, send the user to the reset password page
                    Intent intent = new Intent(forgotpasswordActivity.this, resetPasswordActivity.class);
                    intent.putExtra("userEmail", email); // Pass email to the next activity
                    startActivity(intent);
                },
                error -> {
                    // Check for specific status code (optional, refine error handling)
                    if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                        messageText.setText("An account could not be found for the given email ID.");
                    } else {
                        messageText.setText("An error occurred. Please try again.");
                    }
                }
        );
    }

}
