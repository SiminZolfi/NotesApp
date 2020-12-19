package com.example.notesapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class app_font extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Estedad.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
