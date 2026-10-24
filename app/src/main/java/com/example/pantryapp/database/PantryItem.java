package com.example.pantryapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantry_items")
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String ingredientName;
    public String quantity;
    public String unit;
    public String expiryDate; // ISO-8601 string, nullable

    public PantryItem() {
    }

    public PantryItem(String ingredientName, String quantity, String unit, String expiryDate) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
