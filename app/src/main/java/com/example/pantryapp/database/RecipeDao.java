package com.example.pantryapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(List<RecipeIngredient> ingredients);

    @Update
    int update(Recipe recipe);

    @Delete
    int delete(Recipe recipe);

    @Query("DELETE FROM recipes")
    int deleteAllRecipes();

    @Query("DELETE FROM recipe_ingredients")
    int deleteAllRecipeIngredients();

    @Query("SELECT * FROM recipes ORDER BY name ASC")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getById(int id);

    @Query("SELECT ingredientName FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<String> getIngredientsForRecipe(int recipeId);
}
