package com.example.calcv3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    public static final String MY_THEME = "MY_THEME";

    private ThemeStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new ThemeStorage(this);

        setTheme(storage.getTheme().getResource());

        setContentView(R.layout.activity_settings);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MY_THEME, 0);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        findViewById(R.id.summer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setTheme(AppTheme.SUMMER);
                recreate();
            }
        });

        findViewById(R.id.winter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storage.setTheme(AppTheme.WINTER);
                recreate();
            }
        });

    }
}