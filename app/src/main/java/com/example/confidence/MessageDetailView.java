package com.example.confidence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageDetailView extends AppCompatActivity {
    //Views
    TextView detailViewDescription;
    ImageButton dismissDetailView;
    //Private properties
    private Bundle receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail_view);

        receivedData = getIntent().getExtras();
        detailViewDescription = findViewById(R.id.detail_page_title);

        dismissDetailView = findViewById(R.id.dismiss_detail_view);
        dismissDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDetailViewAndHandleDetails();
            }
        });

        receiveExtraFromStartPage();
    }

    private void receiveExtraFromStartPage() {

        if (receivedData == null) {
            return;
        }
        String receivedStringData = receivedData.getString("SentData");
        if (receivedStringData != null) {
            detailViewDescription.setText(receivedStringData);
        }
    }

    private void dismissDetailViewAndHandleDetails() {
        finish();
    }
}