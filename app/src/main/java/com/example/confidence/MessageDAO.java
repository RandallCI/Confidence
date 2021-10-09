package com.example.confidence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Message message);

    @Query("DELETE FROM messages_table")
    void deleteAll();

    @Query("SELECT * FROM messages_table ORDER BY message ASC")
    LiveData<List<Message>> getAlphabetizedWords();

}
