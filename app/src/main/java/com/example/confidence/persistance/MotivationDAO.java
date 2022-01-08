package com.example.confidence.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MotivationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Motivation motivation);

    @Query("DELETE FROM motivation_table")
    void deleteAll();

    @Query("SELECT * FROM motivation_table ORDER BY motivation ASC")
    LiveData<List<Motivation>> getAlphabetizedWords();

}
