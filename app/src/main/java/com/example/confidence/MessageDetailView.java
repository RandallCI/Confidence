package com.example.confidence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MessageDetailView extends AppCompatActivity {

    ImageButton dismissDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail_view);

        dismissDetailView = findViewById(R.id.dismiss_detail_view);
        dismissDetailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDetailViewAndHandleDetails();
            }
        });
    }

    private void dismissDetailViewAndHandleDetails() {
        finish();
    }
}