package com.example.confidence;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView dayTitle;
    ImageButton messageForToday;
    Button newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayTitle = findViewById(R.id.day_title);
        messageForToday = findViewById(R.id.message_for_today);
        newMessage = findViewById(R.id.new_message);

        messageForToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreInfoOnMessage();
            }
        });

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        theDay();

    }

    private void moreInfoOnMessage() {
        Intent detailView = new Intent(MainActivity.this, MessageDetailView.class);
        detailView.putExtra("Hello", "Hello");
        startActivity(detailView);
    }

    private void theDay() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        dayTitle.setText(String.format("Day: %s", dateString));
    }
}