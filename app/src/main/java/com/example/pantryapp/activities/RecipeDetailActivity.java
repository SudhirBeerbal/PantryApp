package com.example.pantryapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pantryapp.R;
import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.Recipe;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.title_recipe_detail));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int recipeId = getIntent().getIntExtra("recipeId", -1);
        TextView tvName = findViewById(R.id.tvRecipeName);
        TextView tvServings = findViewById(R.id.tvServings);
        TextView tvIngredients = findViewById(R.id.tvIngredients);
        TextView tvMethod = findViewById(R.id.tvMethod);

        AppExecutors.getInstance().diskIO().execute(() -> {
            Recipe recipe = AppDatabase.getDatabase(this).recipeDao().getById(recipeId);
            List<String> ingredients = AppDatabase.getDatabase(this).recipeDao().getIngredientsForRecipe(recipeId);

            AppExecutors.getInstance().mainThread().execute(() -> {
                if (recipe != null) {
                    tvName.setText(recipe.name);
                    tvServings.setText(getString(R.string.servings) + " " + recipe.servings);
                    StringBuilder sb = new StringBuilder();
                    for (String ing : ingredients) {
                        sb.append("• ").append(ing).append("\n");
                    }
                    tvIngredients.setText(getString(R.string.ingredients) + "\n" + sb.toString());
                    tvMethod.setText(getString(R.string.method) + "\n" + recipe.preparationSteps);
                }
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
