package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class atv_intro extends AppCompatActivity {

    ViewPager vw_pager;
    adp_intro adapter;
    Button btn_next,btn_enter;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_intro);

        vw_pager=findViewById(R.id.vw_pager);

        adapter=new adp_intro(getSupportFragmentManager());
        vw_pager.setAdapter(adapter);
        vw_pager.setCurrentItem(i);

        vw_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                i=position;
                set_button_visiblity();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btn_next=findViewById(R.id.btn_next);
        btn_enter=findViewById(R.id.btn_enter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                vw_pager.setCurrentItem(i);
                set_button_visiblity();
            }
        });
    }

    private void set_button_visiblity()
    {
        if(i<2)
        {
            btn_next.setVisibility(View.VISIBLE);
            btn_enter.setVisibility(View.GONE);
        }
        else
        {
            btn_next.setVisibility(View.GONE);
            btn_enter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}