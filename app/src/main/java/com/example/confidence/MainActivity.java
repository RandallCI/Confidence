package com.example.confidence;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

        theDay();

    }

    private void theDay() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        dayTitle.setText(String.format("Day: %s", dateString));
    }
}