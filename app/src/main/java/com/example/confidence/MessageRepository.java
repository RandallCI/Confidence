package com.example.confidence;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MessageRepository {
    //private properties
    private MessageDAO messageDAO;
    private LiveData<List<Message>> mAllMessages;

    MessageRepository(Application application) {
        MessageRoomDatabase messageRoomDatabase = MessageRoomDatabase.getDatabase(application);
        messageDAO = messageRoomDatabase.messageDAO();
        mAllMessages = messageDAO.getAlphabetizedWords();
    }

    LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }

    void insert(Message message) {
        MessageRoomDatabase.databaseWriterExecutor.execute(() -> {
            messageDAO.insert(message);
        });
    }

}
