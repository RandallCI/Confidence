package com.example.confidence.persistance;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MotivationRepository {

    private MotivationDAO mMotivationDao;
    private LiveData<List<Motivation>> allMotivations;

    MotivationRepository(Application application) {
        MotivationRoomDatabase motivationRoomDatabase = MotivationRoomDatabase.getDatabase(application);
        mMotivationDao = motivationRoomDatabase.wordDAO();
        allMotivations = mMotivationDao.getAlphabetizedWords();
    }

    LiveData<List<Motivation>> getAllMotivations() {
        return allMotivations;
    }

    void insert(Motivation motivation) {
        MotivationRoomDatabase.databaseWriteExecutor.execute(() -> {
            mMotivationDao.insert(motivation);
        });
    }
}
