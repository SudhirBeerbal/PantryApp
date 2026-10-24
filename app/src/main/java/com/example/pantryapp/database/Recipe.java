package com.example.pantryapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String preparationSteps;
    public String servings;

    public Recipe() {
    }

    public Recipe(String name, String preparationSteps, String servings) {
        this.name = name;
        this.preparationSteps = preparationSteps;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(String preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }
}
