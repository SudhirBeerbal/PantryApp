package com.example.pantryapp;

import android.app.Application;

import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.DatabaseSeeder;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        final AppDatabase database = AppDatabase.getDatabase(this);
        AppExecutors.getInstance().diskIO().execute(() -> DatabaseSeeder.seedIfEmpty(database));
    }
}
