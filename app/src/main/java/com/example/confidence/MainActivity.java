package com.example.confidence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.confidence.persistance.MotivationViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    //Properties
    //Static strings
    public static final String TAG = "Quote";
    public static final String FAILURE = "Failure";
    private static final String QUOTE_KEY = "QUOTE_KEY";
    static final String THE_MESSAGE = "THE MESSAGE";
    //storage variables
    private String messageToSave = "";
    //Views
    TextView dayTitle;
    ImageButton messageImage;
    TextView messageForTodayTextView;
    ProgressBar viewProgress;
    //Private properties
    private final DocumentReference documentReference = FirebaseFirestore.getInstance().document("GenericUser/Motivation");
    private SharedPreferences savePreferences;
    private String theMessage;
    //Create a word view model object.
    MotivationViewModel motivationViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get references to the views
        viewProgress = findViewById(R.id.view_progress);
        dayTitle = findViewById(R.id.day_title);
        messageImage = findViewById(R.id.more_on_this_message);
        messageForTodayTextView = findViewById(R.id.message_for_today_textview);
        //Make message for today text view scrollable.
        messageForTodayTextView.setMovementMethod(new ScrollingMovementMethod());


        messageImage.setImageResource(R.drawable.my_image);

        //Get the data from the network if the user is connected to the internet.
        ifNetworkIsAvailableGetMessage(MainActivity.this);
        //Set the current day.
        theCurrentDay();
        //Display the locally saved message when an internet connection is not available.
        displayTheSavedMessageFromLocalStorage();

    }



    //When this button is pressed the message will be saved to the list of messages locally on the device.
    //Th user will than be redirected to the message list view.
    public void saveMessageToMessageList(View view) {

        //After storing the value redirect the user to the list of stored values.
        Intent intent = new Intent(this, ConfidenceList.class);
        intent.putExtra(THE_MESSAGE, messageToSave);
        startActivity(intent);

    }


    @Override
    protected void onPause() {
        super.onPause();
        saveTheMessage();
    }

    //Private methods

    private void displayTheSavedMessageFromLocalStorage() {
        //Instantiate shared preferences.
        savePreferences = getSharedPreferences("SAVED_QUOTE", Context.MODE_PRIVATE);
        //Get th shared preferences context.
        savePreferences = getApplicationContext().getSharedPreferences("SAVED_QUOTE", Context.MODE_PRIVATE);
        //Return the saved message
        messageToSave = savePreferences.getString("QUOTE", "");
        //Set the message to the text view
        messageForTodayTextView.setText(messageToSave);
    }

    //Check for a valid internet connection before trying to fetch data from the database, than display that data .
    private void ifNetworkIsAvailableGetMessage(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    //Grab the new message from the database.
                    AsyncActivity asyncActivity = new AsyncActivity(MainActivity.this);
                    asyncActivity.execute();

                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    //Grab the new message from the database.
                    AsyncActivity asyncActivity = new AsyncActivity(MainActivity.this);
                    asyncActivity.execute();
                }
            } else {
               Toast toastB = Toast.makeText(this, "Hello, please connect to the internet in order to retrieve messages", Toast.LENGTH_LONG);

               toastB.show();
            }
        }

    }

    //Fetch the data from the database.
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
        detailView.putExtra("SentData", messageToSave);
        startActivity(detailView);
    }
    //Get and display the current day.
    private void theCurrentDay() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        int currentDay = dateFormat.getCalendar().get(Calendar.DATE);
        dayTitle.setText(String.format("Day: %s", currentDay));
    }
    //Save the message on screen for later viewing in shared preferences.
    private  void saveTheMessage() {
        messageToSave = messageForTodayTextView.getText().toString();
        SharedPreferences.Editor editor = savePreferences.edit();
        editor.putString("QUOTE", messageToSave);
        editor.apply();

    }
    //This button triggers a call for a new message from the cloud storage.
    public void retrieveMessage(View view) {
        ifNetworkIsAvailableGetMessage(MainActivity.this);
    }

    public void readMore(View view) {
        moreInfoOnMessage();
    }


    //Mark: Types
    //This Async activity will run th fetch message from database function and run a spinner while awaiting the results.
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
