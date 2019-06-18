package com.yojoo.skincancerclassifier.adabter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;

import com.yojoo.skincancerclassifier.fragments.HomeFragment;
import com.yojoo.skincancerclassifier.fragments.InboxFragment;
import com.yojoo.skincancerclassifier.fragments.ResultListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment home = new HomeFragment();
                return home;
            case 1:
                ResultListFragment result = new ResultListFragment();
                return result;
            case 2:
                InboxFragment inbox = new InboxFragment();
                return inbox;

        }
        throw new RuntimeException("This Position Not Supported.");
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
