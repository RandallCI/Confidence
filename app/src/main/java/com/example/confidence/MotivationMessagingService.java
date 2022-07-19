package com.example.confidence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MotivationMessagingService extends FirebaseMessagingService {

    private static final String TAG = MotivationMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, "The token from the server is" + token);
        getSharedPreferences("TOKEN", MODE_PRIVATE).edit().putString("The Token", token).apply();
    }
}
