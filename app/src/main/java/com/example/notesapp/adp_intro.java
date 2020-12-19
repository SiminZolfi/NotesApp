package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class adp_intro extends FragmentPagerAdapter {

    public adp_intro(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new frg_intro_01();
            case 1:
                return new frg_intro_02();
            case 2:
                return new frg_intro_03();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

