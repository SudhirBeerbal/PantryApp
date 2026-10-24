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
import com.example.pantryapp.adapters.RecipeAdapter;
import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.title_recipes));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        emptyText = findViewById(R.id.textEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadRecipes();
    }

    private void loadRecipes() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Recipe> recipes = AppDatabase.getDatabase(this).recipeDao().getAll();
            AppExecutors.getInstance().mainThread().execute(() -> {
                adapter.setRecipes(recipes);
                emptyText.setVisibility(recipes.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
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
