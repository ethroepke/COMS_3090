package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class employeeActivity extends AppCompatActivity {
    private boolean isClockedIn = false;
    private boolean isShiftDetailsVisible = false;
    private boolean isPayDetailsVisible = false;

    private long clockInTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    private FrameLayout borderChange;

    private Button checkButton;
    private Button messageButton;
    private Button performanceReviewButton;
    private Button profileButton;
    private Button projButton;
    private Button selfServiceButton;
    private Button payButton;
    private TextView checkInMsg;
    private Chronometer timeClockMsg;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        borderChange = findViewById(R.id.frameChange);
        checkButton = findViewById(R.id.checkButton);
        checkInMsg = findViewById(R.id.checkText);
        timeClockMsg = findViewById(R.id.timeText);
        messageButton = findViewById(R.id.messageButton);
        performanceReviewButton = findViewById(R.id.performanceButton);
        profileButton = findViewById(R.id.profileButton);
        projButton = findViewById(R.id.projButton);
        selfServiceButton = findViewById(R.id.selfServiceButton);
        payButton = findViewById(R.id.payButton);


        //Clock In/Out functionality
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayerDrawable layerDrawable = (LayerDrawable) borderChange.getBackground();
                Drawable borderDrawable = layerDrawable.getDrawable(0);

                if (borderDrawable instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) borderDrawable;

                    if (isClockedIn) {
                        gradientDrawable.setStroke(15, Color.GRAY);
                        checkInMsg.setText("Clock In");

                        timeClockMsg.stop();
                        timeClockMsg.setBase(SystemClock.elapsedRealtime());

                        String clockOutTime = dateFormat.format(new Date());
                        showClockOutPopup(clockInTime, System.currentTimeMillis() - clockInTime, clockOutTime);

                    } else {
                        gradientDrawable.setStroke(15, Color.GREEN);
                        checkInMsg.setText("Clock Out");

                        timeClockMsg.setBase(SystemClock.elapsedRealtime());
                        timeClockMsg.start();

                        clockInTime = System.currentTimeMillis();
                    }

                    isClockedIn = !isClockedIn;
                }
            }
        });

        //All Intents for buttons to new pages down below
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        performanceReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        projButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        selfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(employeeActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Pop up page to show hours worked after clocking out
    private void showClockOutPopup(long clockInTime, long elapsedMillis, String clockOutTime) {
        long elapsedHours = elapsedMillis / 3600000;
        long elapsedMinutes = (elapsedMillis % 3600000) / 60000;

        String clockInTimeFormatted = dateFormat.format(new Date(clockInTime));
        String workedHours = String.format(Locale.getDefault(), "%02d:%02d", elapsedHours, elapsedMinutes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clock Out Summary");
        builder.setMessage("Clock In Time: " + clockInTimeFormatted +
                "\nClock Out Time: " + clockOutTime +
                "\nHours Worked: " + workedHours);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

    /*

    -------------------- API REQUESTS ------------------------


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

     */

