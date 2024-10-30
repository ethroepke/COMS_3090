package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
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
import com.android.volley.toolbox.StringRequest;
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
                // Get the users username/password inputs, trim to remove whitespace
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Check if fields filled, then either successful login or failed login
                if (!username.isEmpty() && !password.isEmpty()) {
                    String url = "http://coms-3090-046.class.las.iastate.edu:8080/login?username=" + username + "&password=" + password;

                    StringRequest loginRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Login successful
                                    if (response.equals("Login successful")) {
                                        Intent intent = new Intent(loginActivity.this, employeeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        messageText.setText(response);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    messageText.setText("Login failed. Please try again.");
                                    Log.e("Volley", error.toString());
                                }
                            });

                    Volley.newRequestQueue(loginActivity.this).add(loginRequest);

                } else {
                    // If fields not filled, message
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
}
