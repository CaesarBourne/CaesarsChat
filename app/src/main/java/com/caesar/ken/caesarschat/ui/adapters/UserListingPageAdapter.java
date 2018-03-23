package com.caesar.ken.caesarschat.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.caesar.ken.caesarschat.ui.fragments.UsersFragment;

/**
 * Created by e on 3/8/2018.
 */

public class UserListingPageAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments = new Fragment[]{
            UsersFragment.newInstance()};
    private static final String[] sTitles = new String[]{
            "All Users"};
//the fragment manager from the attached fragment

    public UserListingPageAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return sFragments[position];
    }

    @Override
    public int getCount() {
        return sFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTitles[position];
    }
}
