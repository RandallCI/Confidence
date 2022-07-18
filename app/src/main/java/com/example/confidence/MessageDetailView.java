package com.example.confidence;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MessageDetailView extends AppCompatActivity {
    //Views
    TextView detailViewContent;
    ImageButton messageImage;
    //Private properties
    private Bundle receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail_view);

        receivedData = getIntent().getExtras();
        //Get the detail text view.
        detailViewContent = findViewById(R.id.detail_page_content);
        //Set scrollability to the content text view.
        detailViewContent.setMovementMethod(new ScrollingMovementMethod());
        //Excess the image button.
        messageImage = findViewById(R.id.detail_view_image);
        //Place an image in the image button.
        messageImage.setImageResource(R.drawable.my_image);
        //Set an on click listener to the image button
        messageImage.setOnClickListener(new View.OnClickListener() {
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
            detailViewContent.setText(receivedStringData);
        }
    }

    private void dismissDetailViewAndHandleDetails() {
        finish();
    }
}