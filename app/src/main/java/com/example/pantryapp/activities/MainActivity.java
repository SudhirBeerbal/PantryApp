package com.example.pantryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.pantryapp.R;
import com.example.pantryapp.adapters.PantryAdapter;
import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PantryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_pantry));
        }

        FloatingActionButton addButton = findViewById(R.id.btnAddItem);
        recyclerView = findViewById(R.id.recyclerViewPantry);
        emptyText = findViewById(R.id.textEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditPantryActivity.class);
            startActivity(intent);
        });

        loadPantry();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void loadPantry() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<PantryItem> items = AppDatabase.getDatabase(this).pantryDao().getAll();
            AppExecutors.getInstance().mainThread().execute(() -> {
                adapter.setItems(items);
                emptyText.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    @Override
    public void onItemClick(PantryItem item) {
        Intent intent = new Intent(MainActivity.this, AddEditPantryActivity.class);
        intent.putExtra("itemId", item.id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recipes) {
            startActivity(new Intent(MainActivity.this, RecipeListActivity.class));
            return true;
        } else if (id == R.id.action_suggested) {
            startActivity(new Intent(MainActivity.this, SuggestedRecipesActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
