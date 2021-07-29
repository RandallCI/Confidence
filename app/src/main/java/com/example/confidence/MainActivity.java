package com.example.confidence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Quote";
    public static final String FAILURE = "Failure";
    private static final String QUOTE_KEY = "QUOTE_KEY";
    private String messageToSave;
    //Views
    TextView dayTitle;
    ImageButton messageForToday;
    TextView messageForTodayTextView;
    ProgressBar viewProgress;
    //Private properties
    private final DocumentReference documentReference = FirebaseFirestore.getInstance().document("GenericUser/Motivation");
    private SharedPreferences savePreferences;
    private String theMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get references to the views
        viewProgress = findViewById(R.id.view_progress);
        dayTitle = findViewById(R.id.day_title);
        messageForToday = findViewById(R.id.message_for_today);
        messageForTodayTextView = findViewById(R.id.message_for_today_textview);
        //Instantiate shared preferences.
        savePreferences = getSharedPreferences("SAVED_QUOTE", Context.MODE_PRIVATE);

        savePreferences = getApplicationContext().getSharedPreferences("SAVED_QUOTE", Context.MODE_PRIVATE);
        messageToSave = savePreferences.getString("QUOTE", "");
        messageForTodayTextView.setText(messageToSave);

        messageForToday.setOnClickListener(v -> {
            moreInfoOnMessage();

//               connectToDatabase();
        });
        //Grab the new message from the database.
        AsyncActivity asyncActivity = new AsyncActivity(MainActivity.this);
        asyncActivity.execute();

        theCurrentDay();

    }

    public void newMessage(View view) {
        AsyncActivity asyncActivity = new AsyncActivity(MainActivity.this);
        asyncActivity.execute();
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveTheMessage();
    }

    //Private methods
    private void fetchMotivation() {
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {

                    theMessage = documentSnapshot.getString(QUOTE_KEY);
                    messageForTodayTextView.setText(theMessage);

                Log.d(TAG, "fetchMotivation: " + theMessage);
            }
        });
    }

    private void connectToDatabase() {
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put(QUOTE_KEY, "Hello Database");
        documentReference.set(dataToSave).addOnSuccessListener(unused -> Log.d(TAG, "Document saved")).addOnFailureListener(e -> Log.d(FAILURE, "The Document did not saved", e));
    }

    private void moreInfoOnMessage() {
        Intent detailView = new Intent(MainActivity.this, MessageDetailView.class);
        detailView.putExtra("SentData", "Hello");
        startActivity(detailView);
    }
    //Get and display the current day.
    private void theCurrentDay() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        int currentDay = dateFormat.getCalendar().get(Calendar.DATE);
        dayTitle.setText(String.format("Day: %s", currentDay));
    }
    //Save the message on screen for later viewing.
    private  void saveTheMessage() {
        messageToSave = messageForTodayTextView.getText().toString();
        SharedPreferences.Editor editor = savePreferences.edit();
        editor.putString("QUOTE", messageToSave);
        editor.apply();

    }


    //Mark: Types
private static class AsyncActivity extends AsyncTask<Void, Void, Void> {
        private final WeakReference<MainActivity> weakReference;

        AsyncActivity(MainActivity mainActivity) {
            weakReference = new WeakReference<>(mainActivity);
        }
    //Start the spinner.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity mainActivity = weakReference.get();
        if (mainActivity == null || mainActivity.isFinishing()) {
            return;
        }
        mainActivity.viewProgress.setVisibility(View.VISIBLE);
    }
    //Do the background tasks.
    @Override
    protected Void doInBackground(Void... voids) {
        MainActivity mainActivity = weakReference.get();
        if (mainActivity == null || mainActivity.isFinishing()) {
            return null;
        } else {
            mainActivity.fetchMotivation();
        }
        return null;
    }
    //Display the returned result.
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        MainActivity mainActivity = weakReference.get();
        if (mainActivity != null) {
            mainActivity.viewProgress.setVisibility(View.INVISIBLE);
        }

    }
}

}
