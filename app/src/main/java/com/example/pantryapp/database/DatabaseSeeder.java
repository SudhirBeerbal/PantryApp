package com.example.pantryapp.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Seeds the 20 built-in recipes into the local SQLite database the first time
 * the app runs. Runs once on a background thread, so the insert is never
 * executed inside the database creation callback.
 */
public class DatabaseSeeder {

    public static void seedIfEmpty(AppDatabase db) {
        if (db.recipeDao().getAll() != null && !db.recipeDao().getAll().isEmpty()) {
            return;
        }
        List<Recipe> recipes = seedRecipes();
        for (Recipe recipe : recipes) {
            long newId = db.recipeDao().insert(recipe);
            recipe.id = (int) newId;
            db.recipeDao().insertIngredients(seedIngredients(recipe.id, recipe.name));
        }
    }

    private static List<Recipe> seedRecipes() {
        List<Recipe> list = new ArrayList<>();
        list.add(new Recipe("Spaghetti Bolognese",
                "1. Brown the mince in a pan.\n2. Add onion, garlic and carrots; cook until soft.\n3. Stir in tinned tomatoes and tomato puree.\n4. Simmer for 30 minutes.\n5. Cook spaghetti according to pack instructions.\n6. Serve sauce over pasta.",
                "4"));
        list.add(new Recipe("Chicken Stir-Fry",
                "1. Slice chicken and vegetables.\n2. Heat oil in a wok.\n3. Stir-fry chicken until cooked through.\n4. Add vegetables and soy sauce.\n5. Cook for 5 minutes.\n6. Serve with rice or noodles.",
                "2"));
        list.add(new Recipe("Vegetable Omelette",
                "1. Whisk eggs with salt and pepper.\n2. Heat butter in a non-stick pan.\n3. Pour in eggs and swirl.\n4. Add chopped vegetables on one side.\n5. Fold and cook until set.\n6. Slide onto a plate.",
                "1"));
        list.add(new Recipe("Beef Tacos",
                "1. Brown minced beef with taco seasoning.\n2. Warm tortillas.\n3. Fill with beef, lettuce, tomato and cheese.\n4. Top with salsa and sour cream.\n5. Serve immediately.",
                "3"));
        list.add(new Recipe("Tomato Basil Pasta",
                "1. Cook pasta in salted water.\n2. Heat olive oil and garlic in a pan.\n3. Add chopped tomatoes and simmer.\n4. Toss cooked pasta in sauce.\n5. Finish with fresh basil and parmesan.",
                "2"));
        list.add(new Recipe("Egg Fried Rice",
                "1. Scramble eggs in a wok; set aside.\n2. Stir-fry cooked rice with soy sauce.\n3. Add peas, carrots and spring onion.\n4. Return egg to pan.\n5. Toss everything together and serve.",
                "2"));
        list.add(new Recipe("Greek Salad",
                "1. Chop cucumber, tomato and red onion.\n2. Toss with olives and feta cheese.\n3. Dress with olive oil, lemon juice and oregano.\n4. Serve chilled.",
                "2"));
        list.add(new Recipe("Ham and Cheese Sandwich",
                "1. Lay out two slices of bread.\n2. Layer ham and cheese between them.\n3. Optional: toast in a pan.\n4. Cut in half and serve.",
                "1"));
        list.add(new Recipe("Pancakes",
                "1. Mix flour, eggs, milk and sugar into a batter.\n2. Heat a greased pan.\n3. Pour batter and cook until bubbles form.\n4. Flip and cook the other side.\n5. Serve with syrup or fruit.",
                "2"));
        list.add(new Recipe("Minestrone Soup",
                "1. Sauté onion, carrot and celery.\n2. Add tinned tomatoes, vegetable stock and pasta.\n3. Simmer until vegetables are tender.\n4. Stir in canned beans.\n5. Season and serve with parmesan.",
                "4"));
        list.add(new Recipe("Chicken Curry",
                "1. Sauté onion, ginger and garlic.\n2. Add curry powder and cook briefly.\n3. Add chicken and cook until sealed.\n4. Pour in coconut milk and simmer.\n5. Serve with rice.",
                "3"));
        list.add(new Recipe("Mushroom Risotto",
                "1. Sauté onion and mushrooms in butter.\n2. Add arborio rice and stir.\n3. Add warm stock one ladle at a time, stirring.\n4. Continue until rice is creamy and cooked.\n5. Finish with parmesan and parsley.",
                "2"));
        list.add(new Recipe("Caesar Salad",
                "1. Toss romaine lettuce with Caesar dressing.\n2. Add croutons and shaved parmesan.\n3. Top with grilled chicken if desired.\n4. Serve immediately.",
                "1"));
        list.add(new Recipe("Chocolate Mug Cake",
                "1. Mix flour, sugar, cocoa and milk in a mug.\n2. Add oil and stir smooth.\n3. Microwave for 60-90 seconds.\n4. Let cool slightly and enjoy.",
                "1"));
        list.add(new Recipe("Beef and Vegetable Stew",
                "1. Brown beef chunks in a pot.\n2. Add onion, carrots and potatoes.\n3. Pour in beef stock and tomatoes.\n4. Simmer for 2 hours until tender.\n5. Season and serve with bread.",
                "6"));
        list.add(new Recipe("Avocado Toast",
                "1. Toast two slices of bread.\n2. Mash avocado with lemon juice and salt.\n3. Spread on toast.\n4. Top with chilli flakes and a poached egg if desired.",
                "1"));
        list.add(new Recipe("Lentil Soup",
                "1. Sauté onion, carrot and celery.\n2. Add lentils, tomatoes and vegetable stock.\n3. Simmer until lentils are soft.\n4. Blend partially for texture.\n5. Serve with a drizzle of oil.",
                "4"));
        list.add(new Recipe("Fruit Smoothie",
                "1. Add banana, berries and yogurt to a blender.\n2. Pour in milk or juice.\n3. Blend until smooth.\n4. Pour into a glass and serve.",
                "1"));
        list.add(new Recipe("Cheese and Tomato Toastie",
                "1. Butter two slices of bread.\n2. Layer cheese and sliced tomato.\n3. Close the sandwich.\n4. Toast in a pan until golden on both sides.\n5. Cut in half and serve warm.",
                "1"));
        list.add(new Recipe("Vegetable Curry",
                "1. Sauté onion, garlic and ginger.\n2. Add curry powder and cook briefly.\n3. Add potatoes and carrots.\n4. Pour in coconut milk and simmer until tender.\n5. Serve with rice.",
                "3"));
        return list;
    }

    private static List<RecipeIngredient> seedIngredients(int recipeId, String recipeName) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        switch (recipeName) {
            case "Spaghetti Bolognese":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "mince"),
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "garlic"),
                        new RecipeIngredient(recipeId, "carrot"),
                        new RecipeIngredient(recipeId, "tinned tomatoes"),
                        new RecipeIngredient(recipeId, "tomato puree"),
                        new RecipeIngredient(recipeId, "spaghetti")));
                break;
            case "Chicken Stir-Fry":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "chicken"),
                        new RecipeIngredient(recipeId, "vegetables"),
                        new RecipeIngredient(recipeId, "oil"),
                        new RecipeIngredient(recipeId, "soy sauce"),
                        new RecipeIngredient(recipeId, "rice")));
                break;
            case "Vegetable Omelette":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "eggs"),
                        new RecipeIngredient(recipeId, "vegetables"),
                        new RecipeIngredient(recipeId, "butter"),
                        new RecipeIngredient(recipeId, "salt"),
                        new RecipeIngredient(recipeId, "pepper")));
                break;
            case "Beef Tacos":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "minced beef"),
                        new RecipeIngredient(recipeId, "taco seasoning"),
                        new RecipeIngredient(recipeId, "tortillas"),
                        new RecipeIngredient(recipeId, "lettuce"),
                        new RecipeIngredient(recipeId, "tomato"),
                        new RecipeIngredient(recipeId, "cheese")));
                break;
            case "Tomato Basil Pasta":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "pasta"),
                        new RecipeIngredient(recipeId, "olive oil"),
                        new RecipeIngredient(recipeId, "garlic"),
                        new RecipeIngredient(recipeId, "tomatoes"),
                        new RecipeIngredient(recipeId, "basil"),
                        new RecipeIngredient(recipeId, "parmesan")));
                break;
            case "Egg Fried Rice":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "eggs"),
                        new RecipeIngredient(recipeId, "rice"),
                        new RecipeIngredient(recipeId, "soy sauce"),
                        new RecipeIngredient(recipeId, "peas"),
                        new RecipeIngredient(recipeId, "carrots"),
                        new RecipeIngredient(recipeId, "spring onion")));
                break;
            case "Greek Salad":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "cucumber"),
                        new RecipeIngredient(recipeId, "tomato"),
                        new RecipeIngredient(recipeId, "red onion"),
                        new RecipeIngredient(recipeId, "olives"),
                        new RecipeIngredient(recipeId, "feta cheese"),
                        new RecipeIngredient(recipeId, "olive oil"),
                        new RecipeIngredient(recipeId, "lemon")));
                break;
            case "Ham and Cheese Sandwich":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "bread"),
                        new RecipeIngredient(recipeId, "ham"),
                        new RecipeIngredient(recipeId, "cheese")));
                break;
            case "Pancakes":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "flour"),
                        new RecipeIngredient(recipeId, "eggs"),
                        new RecipeIngredient(recipeId, "milk")));
                break;
            case "Minestrone Soup":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "carrot"),
                        new RecipeIngredient(recipeId, "celery"),
                        new RecipeIngredient(recipeId, "tinned tomatoes"),
                        new RecipeIngredient(recipeId, "vegetable stock"),
                        new RecipeIngredient(recipeId, "pasta"),
                        new RecipeIngredient(recipeId, "beans")));
                break;
            case "Chicken Curry":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "ginger"),
                        new RecipeIngredient(recipeId, "garlic"),
                        new RecipeIngredient(recipeId, "curry powder"),
                        new RecipeIngredient(recipeId, "chicken"),
                        new RecipeIngredient(recipeId, "coconut milk"),
                        new RecipeIngredient(recipeId, "rice")));
                break;
            case "Mushroom Risotto":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "mushrooms"),
                        new RecipeIngredient(recipeId, "butter"),
                        new RecipeIngredient(recipeId, "arborio rice"),
                        new RecipeIngredient(recipeId, "stock"),
                        new RecipeIngredient(recipeId, "parmesan")));
                break;
            case "Caesar Salad":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "romaine lettuce"),
                        new RecipeIngredient(recipeId, "Caesar dressing"),
                        new RecipeIngredient(recipeId, "croutons"),
                        new RecipeIngredient(recipeId, "parmesan")));
                break;
            case "Chocolate Mug Cake":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "flour"),
                        new RecipeIngredient(recipeId, "sugar"),
                        new RecipeIngredient(recipeId, "cocoa"),
                        new RecipeIngredient(recipeId, "milk"),
                        new RecipeIngredient(recipeId, "oil")));
                break;
            case "Beef and Vegetable Stew":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "beef"),
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "carrots"),
                        new RecipeIngredient(recipeId, "potatoes"),
                        new RecipeIngredient(recipeId, "beef stock"),
                        new RecipeIngredient(recipeId, "tomatoes")));
                break;
            case "Avocado Toast":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "bread"),
                        new RecipeIngredient(recipeId, "avocado"),
                        new RecipeIngredient(recipeId, "salt")));
                break;
            case "Lentil Soup":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "carrot"),
                        new RecipeIngredient(recipeId, "celery"),
                        new RecipeIngredient(recipeId, "lentils"),
                        new RecipeIngredient(recipeId, "tomatoes"),
                        new RecipeIngredient(recipeId, "vegetable stock")));
                break;
            case "Fruit Smoothie":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "banana"),
                        new RecipeIngredient(recipeId, "berries"),
                        new RecipeIngredient(recipeId, "yogurt"),
                        new RecipeIngredient(recipeId, "milk")));
                break;
            case "Cheese and Tomato Toastie":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "bread"),
                        new RecipeIngredient(recipeId, "cheese"),
                        new RecipeIngredient(recipeId, "tomato")));
                break;
            case "Vegetable Curry":
                ingredients.addAll(Arrays.asList(
                        new RecipeIngredient(recipeId, "onion"),
                        new RecipeIngredient(recipeId, "garlic"),
                        new RecipeIngredient(recipeId, "ginger"),
                        new RecipeIngredient(recipeId, "potatoes"),
                        new RecipeIngredient(recipeId, "carrots"),
                        new RecipeIngredient(recipeId, "coconut milk"),
                        new RecipeIngredient(recipeId, "rice")));
                break;
        }
        return ingredients;
    }
}
