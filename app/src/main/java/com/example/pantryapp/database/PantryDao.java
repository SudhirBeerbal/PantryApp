package com.example.pantryapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PantryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PantryItem item);

    @Update
    int update(PantryItem item);

    @Delete
    int delete(PantryItem item);

    @Query("DELETE FROM pantry_items")
    int deleteAll();

    @Query("SELECT * FROM pantry_items ORDER BY ingredientName ASC")
    List<PantryItem> getAll();

    @Query("SELECT * FROM pantry_items WHERE id = :id")
    PantryItem getById(int id);

    @Query("SELECT * FROM pantry_items WHERE ingredientName LIKE :name LIMIT 1")
    PantryItem findByName(String name);
}
