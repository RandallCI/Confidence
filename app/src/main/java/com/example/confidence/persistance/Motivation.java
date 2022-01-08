package com.example.confidence.persistance;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "motivation_table")
public class Motivation {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "motivation")
    private String motivations;

    public Motivation(@NonNull String motivations) { this.motivations = motivations; }

    public String getMotivations() { return this.motivations; }
}
