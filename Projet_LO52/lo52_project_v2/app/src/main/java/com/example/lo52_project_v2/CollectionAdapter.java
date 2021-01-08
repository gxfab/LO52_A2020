package com.example.lo52_project_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CollectionAdapter extends FragmentStateAdapter {

    int item_count = 2;
    public Fragment[] fragments = new Fragment[item_count];
    public AddRacerTabbedFragment fragment_one;
    public ModifyRacerTabbedFragment fragment_two;

    public CollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return item_count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment =null;
        Bundle args = new Bundle();
        switch(position){
            case 0:
                fragment = new AddRacerTabbedFragment();
                args.putInt(AddRacerTabbedFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                fragment_one = (AddRacerTabbedFragment) fragment;
                break;
            case 1:
                fragment = new ModifyRacerTabbedFragment();
                args.putInt(ModifyRacerTabbedFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                fragment_two = (ModifyRacerTabbedFragment) fragment;
                break;
        }

        fragments[position] = fragment;

        return fragment;
    }
}
