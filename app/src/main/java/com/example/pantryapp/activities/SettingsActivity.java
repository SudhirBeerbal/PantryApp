package com.example.pantryapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.pantryapp.R;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.title_settings));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        switchAlerts = findViewById(R.id.switchExpiringAlerts);

        SharedPreferences prefs = getSharedPreferences("pantry_prefs", MODE_PRIVATE);
        switchAlerts.setChecked(prefs.getBoolean("expiring_soon_alerts", false));

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("expiring_soon_alerts", isChecked).apply();
            Toast.makeText(this, "Expiring-soon alerts " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
