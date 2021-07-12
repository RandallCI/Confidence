package com.example.confidence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Quote";
    public static final String FAILURE = "Failure";
    //Views
    TextView dayTitle;
    ImageButton messageForToday;
    TextView messageForTodayTextView;
    Button newMessage;
    ProgressBar viewProgress;
    //Private properties
    private static final String QUOTE_KEY = "QUOTE_KEY";
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private DocumentReference documentReference = FirebaseFirestore.getInstance().document("GenericUser/Motivation");

    AsyncActivity asyncActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewProgress = findViewById(R.id.view_progress);
        dayTitle = findViewById(R.id.day_title);
        messageForToday = findViewById(R.id.message_for_today);
        newMessage = findViewById(R.id.new_message);
        messageForTodayTextView = findViewById(R.id.message_for_today_textview);

        messageForToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMotivation();
//               connectToDatabase();
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


    private void fetchMotivation() {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String theMessage = documentSnapshot.getString(QUOTE_KEY);
                    messageForTodayTextView.setText(theMessage);
                }
            }
        });
    }

    private void connectToDatabase() {
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(QUOTE_KEY, "Hello Database");
        documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Document saved");
            }
        })        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(FAILURE, "The Document did not saved", e);
            }
        });
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