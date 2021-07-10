package com.example.confidence;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //Views
    TextView dayTitle;
    ImageButton messageForToday;
    Button newMessage;
    ProgressBar viewProgress;
    //Private properties
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    AsyncActivity asyncActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewProgress = findViewById(R.id.view_progress);
        dayTitle = findViewById(R.id.day_title);
        messageForToday = findViewById(R.id.message_for_today);
        newMessage = findViewById(R.id.new_message);

        messageForToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              moreInfoOnMessage();
            }
        });

        theCurrentDay();

    }

   
    private void moreInfoOnMessage() {
        Intent detailView = new Intent(MainActivity.this, MessageDetailView.class);
        detailView.putExtra("SentData", "Hello");
        startActivity(detailView);
    }

    private void theCurrentDay() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        dayTitle.setText(String.format("Day: %s", dateString));
    }



}

//Mark: Types
@SuppressLint("StaticFieldLeak")
class AsyncActivity extends AsyncTask<Integer, Integer, String> {



    @Override
    protected String doInBackground(Integer... integers) {

        for (int i = 0; i < integers[0]; i++) {
            publishProgress((i * 100) / integers[0]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Finished";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }

}