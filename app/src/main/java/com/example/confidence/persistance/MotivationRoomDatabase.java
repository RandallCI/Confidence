package com.example.confidence.persistance;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Motivation.class}, version = 1, exportSchema = false)
public abstract class MotivationRoomDatabase extends RoomDatabase {

    public abstract MotivationDAO wordDAO();

    private static volatile MotivationRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MotivationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MotivationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MotivationRoomDatabase.class,
                            "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                MotivationDAO dao = INSTANCE.wordDAO();
                dao.deleteAll();

                Motivation motivation = new Motivation("Well done randyboy");
                dao.insert(motivation);
                Motivation motivationTwo = new Motivation("Thank Yu LORD GOD!");
                dao.insert(motivationTwo);
            });
        }
    };
}
