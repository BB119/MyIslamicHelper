package com.bbexcellence.myislamichelper.room;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bbexcellence.myislamichelper.models.Day;
import com.bbexcellence.myislamichelper.models.Progress;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Day.class, Progress.class}, version = 1, exportSchema = false)
public abstract class DayRoomDatabase extends RoomDatabase {

    public abstract DayDao dayDao();
    public abstract ProgressDao progressDao();

    private static volatile DayRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DayRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DayRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DayRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
