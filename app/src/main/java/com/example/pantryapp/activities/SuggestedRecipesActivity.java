package com.example.pantryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantryapp.R;
import com.example.pantryapp.adapters.SuggestedRecipeAdapter;
import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.PantryItem;
import com.example.pantryapp.database.Recipe;
import com.example.pantryapp.util.StrictMatcher;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity implements SuggestedRecipeAdapter.OnSuggestedRecipeClickListener {

    private RecyclerView recyclerView;
    private SuggestedRecipeAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.title_suggested));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewSuggested);
        emptyText = findViewById(R.id.textEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuggestedRecipeAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadSuggestions();
    }

    private void loadSuggestions() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<PantryItem> pantry = AppDatabase.getDatabase(this).pantryDao().getAll();
            List<Recipe> allRecipes = AppDatabase.getDatabase(this).recipeDao().getAll();

            List<Recipe> matches = new ArrayList<>();
            for (Recipe recipe : allRecipes) {
                List<String> required = AppDatabase.getDatabase(this).recipeDao().getIngredientsForRecipe(recipe.id);
                if (StrictMatcher.canMake(pantry, required)) {
                    matches.add(recipe);
                }
            }

            AppExecutors.getInstance().mainThread().execute(() -> {
                adapter.setRecipes(matches);
                emptyText.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    @Override
    public void onSuggestedRecipeClick(Recipe recipe) {
        Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipeId", recipe.id);
        startActivity(intent);
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
