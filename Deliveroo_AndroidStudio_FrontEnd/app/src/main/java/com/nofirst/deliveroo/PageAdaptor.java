package com.nofirst.deliveroo;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdaptor extends FragmentStatePagerAdapter {

    static int c;


    public PageAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        FoodFragment foodFragment = new FoodFragment();
        c = position;
        Bundle bundle = new Bundle();
        bundle.putInt("pos",position);
        foodFragment.setArguments(bundle);
        Log.e("......Correct........",position+"");



        return foodFragment;
    }

    @Override
    public int getCount() {
        return FoodActivity.categories.length;
    }
}

