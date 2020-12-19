package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref=getSharedPreferences("runned",MODE_PRIVATE);
                int run=pref.getInt("run",0);
                if(run==0)
                {
                    startActivity(new Intent(atv_splash.this,atv_intro.class));
                }
                else
                {
                    startActivity(new Intent(atv_splash.this,atv_login.class));
                }
            }
        },3000);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}