package com.example.pantryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pantryapp.R;
import com.example.pantryapp.database.AppDatabase;
import com.example.pantryapp.database.AppExecutors;
import com.example.pantryapp.database.PantryItem;

public class AddEditPantryActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave, btnDelete;
    private int itemId = -1;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pantry);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiryDate);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        itemId = getIntent().getIntExtra("itemId", -1);
        isEdit = itemId != -1;

        if (isEdit) {
            setTitle(getString(R.string.edit_item));
            if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            btnDelete.setVisibility(View.VISIBLE);
            loadItem(itemId);
        } else {
            setTitle(getString(R.string.add_item));
            if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> saveItem());
        btnDelete.setOnClickListener(v -> confirmDelete());
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadItem(int id) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            PantryItem item = AppDatabase.getDatabase(this).pantryDao().getById(id);
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (item != null) {
                    etName.setText(item.ingredientName);
                    etQuantity.setText(item.quantity);
                    etUnit.setText(item.unit);
                    etExpiry.setText(item.expiryDate == null ? "" : item.expiryDate);
                }
            });
        });
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.missing_name));
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantity)) {
            etQuantity.setError(getString(R.string.missing_quantity));
            etQuantity.requestFocus();
            return;
        }

        PantryItem item = new PantryItem(name, quantity, unit, TextUtils.isEmpty(expiry) ? null : expiry);

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isEdit) {
                item.id = itemId;
                AppDatabase.getDatabase(this).pantryDao().update(item);
            } else {
                AppDatabase.getDatabase(this).pantryDao().insert(item);
            }
            AppExecutors.getInstance().mainThread().execute(() -> {
                Toast.makeText(this, isEdit ? "Ingredient updated" : "Ingredient added", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton("Delete", (dialog, which) -> {
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        PantryItem item = new PantryItem();
                        item.id = itemId;
                        AppDatabase.getDatabase(this).pantryDao().delete(item);
                        AppExecutors.getInstance().mainThread().execute(() -> {
                            Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
