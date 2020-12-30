package com.example.kyle.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    //public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(FragmentManager m) {
        super(m);
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("day", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
