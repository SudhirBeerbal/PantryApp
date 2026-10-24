package com.example.pantryapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(
    tableName = "recipe_ingredients",
    primaryKeys = {"recipeId", "ingredientName"},
    foreignKeys = @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = @Index("recipeId")
)
public class RecipeIngredient {

    public int recipeId;

    @NonNull
    public String ingredientName;

    public RecipeIngredient() {
    }

    public RecipeIngredient(int recipeId, String ingredientName) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
