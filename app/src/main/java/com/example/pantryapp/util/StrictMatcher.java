package com.example.pantryapp.util;

import com.example.pantryapp.database.PantryItem;
import com.example.pantryapp.database.Recipe;

import java.util.List;

public class StrictMatcher {

    /**
     * Strict-matching logic: a recipe matches only if every required ingredient
     * exists in the user's pantry (case-insensitive match on ingredient name).
     */
    public static boolean canMake(List<PantryItem> pantry, List<String> requiredIngredients) {
        if (requiredIngredients == null || requiredIngredients.isEmpty()) {
            return false;
        }
        for (String required : requiredIngredients) {
            boolean found = false;
            for (PantryItem item : pantry) {
                if (item.ingredientName != null &&
                        item.ingredientName.trim().equalsIgnoreCase(required.trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
