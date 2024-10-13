package com.example.androidexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class payCheckSearchActivity extends AppCompatActivity {

    private EditText usernameInput;
    private Button searchUsers;
    private Button modifyUsers;
    private TextView noUserFoundText;
    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paychecksearch);

        usernameInput = findViewById(R.id.usernameInput);
        searchUsers = findViewById(R.id.searchUsers);
        modifyUsers = findViewById(R.id.modifyUsers);
        noUserFoundText = findViewById(R.id.noUserMessage);
        requestQueue = Volley.newRequestQueue(this);

        searchUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchForUser();
            }
        });

        modifyUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the modify user logic or navigation here
                // e.g., navigate to modify user activity
            }
        });
    }

    private void searchForUser() {
        String username = usernameInput.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "YOUR_BACKEND_URL/search?username=" + username; // Replace with your actual endpoint

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        noUserFoundText.setText("Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void handleResponse(JSONObject response) {
        try {
            if (response.getBoolean("user_found")) { // Adjust based on your API's response structure
                // Navigate to user's pay info page
                // Intent intent = new Intent(this, UserPayInfoActivity.class);
                // intent.putExtra("username", usernameInput.getText().toString());
                // startActivity(intent);
            } else {
                noUserFoundText.setText("No user found");
            }
        } catch (JSONException e) {
            noUserFoundText.setText("Error parsing response");
        }
    }
}
