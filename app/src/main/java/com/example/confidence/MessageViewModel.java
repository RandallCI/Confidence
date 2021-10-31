package com.example.confidence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {

    private MessageRepository messageRepository;

    private final LiveData<List<Message>> mAllWords;


    public MessageViewModel(Application application) {
        super(application);
        messageRepository = new MessageRepository(application);
        mAllWords = messageRepository.getAllMessages();
    }

    LiveData<List<Message>> getAllWords() { return mAllWords; }

    public void insert(Message message) { messageRepository.insert(message); }

}
