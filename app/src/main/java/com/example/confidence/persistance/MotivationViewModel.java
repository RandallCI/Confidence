package com.example.confidence.persistance;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MotivationViewModel extends AndroidViewModel {

    private MotivationRepository motivationRepository;

    private final LiveData<List<Motivation>> allMotivations;

    public MotivationViewModel(Application application) {
        super(application);
        motivationRepository = new MotivationRepository(application);
        allMotivations = motivationRepository.getAllMotivations();
    }

    public LiveData<List<Motivation>> getAllWords() { return allMotivations; }

    public void insert(Motivation motivation) {
        motivationRepository.insert(motivation);
    }

}
