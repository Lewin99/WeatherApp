package org.me.gcu.weatherapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.fragment.app.Fragment;


public class FragmentsAdapter extends FragmentStateAdapter {

    public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GlasgowFragment();
            case 1:
                return new LondonFragment();
            case 2:
                return new NewYorkFragment();
            case 3:
                return new OmanFragment();
            case 4:
                return new MauritiusFragment();
            case 5:
                return new BangladeshFragment();
            default:
                return new GlasgowFragment(); // Default fragment
        }
    }

    @Override
    public int getItemCount() {
        return 6; // Number of fragments
    }
}

