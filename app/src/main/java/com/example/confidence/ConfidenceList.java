package com.example.confidence;

import static com.example.confidence.MainActivity.THE_MESSAGE;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confidence.persistance.MotivationViewModel;

public class ConfidenceList extends AppCompatActivity {

    MotivationViewModel motivationViewModel;
    private String retrievedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidence_list);

        //Create an instance of the database.

        //Instantiate the View model.
        motivationViewModel = new ViewModelProvider(this).get(MotivationViewModel.class);

        //Get the forwarded string from main.
        if (savedInstanceState == null) {
            Bundle sentData = getIntent().getExtras();
            if (sentData == null) {
                retrievedMessage = null;
            } else {
                retrievedMessage = sentData.getString(THE_MESSAGE);
                Log.d("Message", "The message is " + retrievedMessage);
            }
        }

        //Create the recycler view and insert data into it.
        RecyclerView recyclerView = findViewById(R.id.message_list);
        //Instantiate the Adaptor.
        final MotivationListAdaptor motivationListAdaptor = new MotivationListAdaptor(new MotivationListAdaptor.MotivationDiff());
        recyclerView.setAdapter(motivationListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Observe the LiveData.
        motivationViewModel.getAllMotivations().observe( this, messages -> {
            motivationListAdaptor.submitList(messages);
        });

    }

}