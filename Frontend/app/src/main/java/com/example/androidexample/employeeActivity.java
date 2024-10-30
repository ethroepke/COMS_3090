package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class employeeActivity extends AppCompatActivity {

    private TextView checkText;     // define message textview variable
    private Button counterButton;     // define counter button variable

    private Button checkButton;         // define button for check in/out
    private boolean isCheckedIn = false;

    private TextView timerText;
    private long checked_time;
    private long current_checked_time;

    private Button signoutButton;
    private Button deleteButton;

    private Button timeButton;

    private int time_worked_hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);             // link to Main activity XML

        checkButton = findViewById(R.id.check_btn);
        signoutButton = findViewById(R.id.signout_btn);
        deleteButton = findViewById(R.id.delete_btn);
        checkText = findViewById(R.id.check_txt);
        timerText = findViewById(R.id.check_clk_txt);

        timeButton = findViewById(R.id.next_shift_btn);

        int check_green = Color.rgb(10, 100, 10);
        int check_red = Color.rgb(100, 10, 10);


        checkText.setTextSize(32.0F);
        checkButton.setBackgroundColor(check_green);


        //runs without a timer by reposting this handler at the end of the runnable
        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {

                if (isCheckedIn) {
                    current_checked_time = System.currentTimeMillis();
                }
                long total_time_ms = current_checked_time - checked_time;
                int seconds = (int) total_time_ms / 1000;
                int minutes = (seconds / 60);
                int hours = seconds / 60 / 60;
                time_worked_hours = hours;
                timerText.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
                timerHandler.postDelayed(this, 500);
            }
        };


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCheckedIn) {
                    timerText.setVisibility(View.VISIBLE);
                    checked_time = System.currentTimeMillis();
                    checkText.setText("Check out");
                    isCheckedIn = true;
                    checked_time = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    checkButton.setBackgroundColor(check_red);
                } else {
                    checkText.setText("Check in");
                    isCheckedIn = false;
                    checkButton.setBackgroundColor(check_green);
                    //timerText.setVisibility(View.INVISIBLE);
                }
            }
        });

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, deleteActivity.class);
                startActivity(intent);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject time = new JSONObject();
                try {
                    time.put("timeWorked", (current_checked_time - checked_time) / 1000 / 60 / 60);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                checkText.setText(time.toString());
                putRequest(time);
                postRequest(time);
            }
        });
    }

    /*

    -------------------- API REQUESTS ------------------------

     */

    // POST
    public void postRequest(JSONObject j) {
        String post_url = "http://coms-3090-046.class.las.iastate.edu:8080/api/timeWorked/";
        JsonObjectRequest post_time = new JsonObjectRequest(
                Request.Method.POST,
                post_url,
                j,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }

        )
        {
            // dont know if necessary
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(post_time);
    }

    // GET
    public void getRequest() {
        String get_url = "http://coms-3090-046.class.las.iastate.edu:8080/api/timeWorked/";
        JsonObjectRequest get_time = new JsonObjectRequest(
                Request.Method.GET,
                get_url,
                null, // get request
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                }

        )
        {
            // dont know if necessary
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(get_time);
    }

    // PUT
    public void putRequest(JSONObject j) {
        String put_url = "http://coms-3090-046.class.las.iastate.edu:8080/timeWorked/2";
        JsonObjectRequest put_time = new JsonObjectRequest(
                Request.Method.POST,
                put_url,
                j,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response", response.toString());
                        checkText.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        //checkText.setText(error.toString());
                    }
                }

        )
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(put_time);
    }
}