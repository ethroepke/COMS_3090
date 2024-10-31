package com.example.androidexample;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {

    private TextView chatTitle;
    private RecyclerView recyclerViewMessages;
    private EditText messageInput;
    private Button sendButton;

    private messageAdapter messageAdapter;
    private List<String> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        chatTitle = findViewById(R.id.chatTitle);
        recyclerViewMessages = findViewById(R.id.recyclerView_messages);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        messageInput.requestFocus();

        // Retrieve intent extras
        String name = getIntent().getStringExtra("name");
        boolean isGroup = getIntent().getBooleanExtra("isGroup", false);
        String chatId = getIntent().getStringExtra("chatId"); // Assuming you pass chatId as well
        chatTitle.setText(isGroup ? "Group: " + name : name);

        // Setup RecyclerView
        messageList = new ArrayList<>();
        messageAdapter = new messageAdapter(this, messageList, true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // Fetch messages from backend
        Apiservice apiService = new Apiservice(this);
        apiService.getMessages(chatId, new Apiservice.MessageCallback() {
            @Override
            public void onSuccess(List<String> messages) {
                messageList.addAll(messages);
                messageAdapter.notifyDataSetChanged();
                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onError(VolleyError error) {
                // Handle error
            }
        });

        // Send button click listener
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                apiService.sendMessage(chatId, message, new Apiservice.ResponseCallback() {
                    @Override
                    public void onSuccess() {
                        messageList.add(message);
                        messageAdapter.notifyItemInserted(messageList.size() - 1);
                        recyclerViewMessages.scrollToPosition(messageList.size() - 1);
                        messageInput.setText("");
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // Handle error
                    }
                });
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
