package Adapter;

import example.android.jacky.fatigue.ActivitiesFragment;
import example.android.jacky.fatigue.FatigueFragment;
import example.android.jacky.fatigue.SettingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jacky on 11/01/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Settings fragment activity
                return new SettingsFragment();
            case 1:
                // Activities fragment activity
                return new ActivitiesFragment();
            case 2:
                // Fatigue fragment activity
                return new FatigueFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
