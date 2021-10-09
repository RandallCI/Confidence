package com.example.confidence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "messages_table")
public class Message {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "message")
    public String theMessage;

    public String getSentence() { return this.theMessage; }
}
