package com.example.calcv3;

import androidx.appcompat.widget.AppCompatImageView;

public enum AppTheme {

    WINTER(R.style.WinterTheme, "winter"),
    SUMMER(R.style.SummerTheme, "summer");

    AppTheme (int resource, String key){
        this.resource = resource;
        this.key = key;
    }

    private int resource;

    private String key;

    public int getResource() {
        return resource;
    }

    public String getKey() {
        return key;
    }
}
