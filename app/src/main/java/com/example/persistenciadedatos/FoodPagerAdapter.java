package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FoodPagerAdapter extends FragmentStateAdapter {
    private Restaurant restaurant;

    public FoodPagerAdapter(@NonNull FragmentActivity fragmentActivity, Restaurant restaurant) {
        super(fragmentActivity);
        this.restaurant = restaurant;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FoodFragment.newInstance(restaurant, "food");
                break;
            case 1:
                fragment = FoodFragment.newInstance(restaurant, "drink");
                break;
            case 2:
                fragment = FoodFragment.newInstance(restaurant, "complement");
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
