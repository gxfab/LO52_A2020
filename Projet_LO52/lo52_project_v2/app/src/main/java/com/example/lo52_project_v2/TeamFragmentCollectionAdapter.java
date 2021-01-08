package com.example.lo52_project_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TeamFragmentCollectionAdapter extends FragmentStateAdapter {

    int item_count = 2;
    public Fragment[] fragments = new Fragment[item_count];

    public TeamFragmentCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return item_count;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                fragment = new GenerateTeamsTabbedFragment();
                args.putInt(GenerateTeamsTabbedFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new ModifyTeamsTabbedFragment();
                args.putInt(ModifyTeamsTabbedFragment.ARG_OBJECT, position + 1);
                fragment.setArguments(args);
                break;
        }

        fragments[position] = fragment;

        return fragment;

    }
}
