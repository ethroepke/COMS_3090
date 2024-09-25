package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.example.androidexample.EmployerActivity;



public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Initialize UI Elements
        Button signInButton = findViewById(R.id.submitButton);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, EmployerActivity.class);
                //startActivity(intent);
            }
        });
    }
}
