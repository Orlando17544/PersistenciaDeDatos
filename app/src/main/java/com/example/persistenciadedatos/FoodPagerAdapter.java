package com.example.persistenciadedatos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FoodPagerAdapter extends FragmentStateAdapter {
    private int restaurantId;

    public FoodPagerAdapter(@NonNull FragmentActivity fragmentActivity, int restaurantId) {
        super(fragmentActivity);
        this.restaurantId = restaurantId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FoodFragment.newInstance(restaurantId, "food");
                break;
            case 1:
                fragment = FoodFragment.newInstance(restaurantId, "drink");
                break;
            case 2:
                fragment = FoodFragment.newInstance(restaurantId, "complement");
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
