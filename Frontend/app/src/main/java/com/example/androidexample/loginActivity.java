package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class loginActivity extends AppCompatActivity {

    private TextView messageText;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button submitButton;
    private ImageButton showPassword;
    private Button forgotPasswordButton;
    private Button newUserButton;

    boolean isPasswordVisible = false;

    String url = "https://304b2c41-4ef3-4e62-a2f8-e40348b54d5e.mock.pstmn.io";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        /*Initialize UI Elements*/
        submitButton = findViewById(R.id.submitButton);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        messageText = findViewById(R.id.mainMessage);

        forgotPasswordButton = findViewById(R.id.forgotButton);
        newUserButton = findViewById(R.id.newUserJoin);

        showPassword = findViewById(R.id.showPassword);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the text fields
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Check if both fields are filled
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Call loginRequest() to send the request
                    Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                    startActivity(intent);
                    loginRequest();

                } else {
                    // Display a message if fields are empty
                    messageText.setText("Please enter both username and password.");
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
                Intent intent = new Intent(loginActivity.this, joinNowActivity.class);
                startActivity(intent);
            }
        });


    }

    //For login
    public void loginRequest() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Add username and password to URL as query parameters
        String url = "http://coms-3090-046.class.las.iastate.edu:8080/api/userprofile/login";

        JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // No body for GET request
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Login Response", response.toString());
                        if (response.optString("message").equals("login successfully")) {
                            Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                            startActivity(intent);
                        } else {
                            messageText.setText("Unexpected response: " + response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Login Error", error.toString());
                        messageText.setText(error.toString());
                    }
                }
        );

        Volley.newRequestQueue(this).add(loginRequest);
    }
}
