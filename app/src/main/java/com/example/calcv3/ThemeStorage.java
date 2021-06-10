package com.example.calcv3;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ThemeStorage {

    private final static String KEY_APP_THEME = "KEY_APP_THEME";

    private SharedPreferences sharedPreferences;

    public ThemeStorage(Context context){
        sharedPreferences = context.getSharedPreferences("app_themes", MODE_PRIVATE);
    }

    public AppTheme getTheme(){
        String key = sharedPreferences.getString(KEY_APP_THEME, AppTheme.SUMMER.getKey());

        for (AppTheme theme : AppTheme.values()){
            if (theme.getKey().equals(key)){
                return theme;
            }
        }
        throw new IllegalStateException("Wrong theme");
    }

    public void setTheme(AppTheme theme){
        sharedPreferences.edit().putString(KEY_APP_THEME, theme.getKey()).apply();
    }
}