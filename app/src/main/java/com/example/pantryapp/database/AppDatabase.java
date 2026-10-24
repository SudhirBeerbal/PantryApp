package com.example.pantryapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PantryItem.class, Recipe.class, RecipeIngredient.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PantryDao pantryDao();
    public abstract RecipeDao recipeDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "pantry_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
