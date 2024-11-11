package com.example.androidexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.Arrays;
import java.util.List;

public class messageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIndividual;
    private RecyclerView recyclerViewGroup;
    private Button newChat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagehome);

        recyclerViewIndividual = findViewById(R.id.recyclerView_individual);
        recyclerViewGroup = findViewById(R.id.recyclerView_group);
        newChat = findViewById(R.id.newChat);

        setTitle("Messages");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(messageActivity.this, chatActivity.class);
                startActivity(intent);
            }
        });


        // Fetch messages from backend
        Apiservice apiService = new Apiservice(this);
        apiService.getMessages("user_chat_id", new Apiservice.MessageCallback() {
            @Override
            public void onSuccess(List<String> messages) {
                recyclerViewIndividual.setLayoutManager(new LinearLayoutManager(messageActivity.this));
                recyclerViewIndividual.setAdapter(new messageAdapter(messageActivity.this, messages, false));
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //For permissions this will be chang depending on users (Admin or Employer or Employee) to make sure send back to right page
            Intent intent = new Intent(messageActivity.this, adminActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

