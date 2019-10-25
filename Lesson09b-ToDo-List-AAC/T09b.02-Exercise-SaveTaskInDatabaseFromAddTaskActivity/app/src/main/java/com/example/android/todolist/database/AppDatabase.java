package com.example.android.todolist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;


//create DB that uses entities and it's DAOs
// (list of classes that we annotated as entities, version that we will increment by updating the DB, exportSchema is optional and we are not going to use it)
@Database(entities = {TaskEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static AppDatabase sInstance;

    // Singleton pattern - restricts the instantiation of a class to one "single" object instance
    public static AppDatabase getInstance(Context context) {
//        first time is sInstance null
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
//                new object is created and assigned to sInstance variable
                // this method creates new DB or DB connection to already existing DB
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        // TODO (2) call allowMainThreadQueries before building the instance
                        // enable queries on main thread temporarily to check that our implementation of Room works as expected
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    // Add TaskDao
    public abstract TaskDao taskDao();

}
