package com.example.lo52_project_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ParticipantsFragment extends Fragment {

    CollectionAdapter collectionAdapter;
    ViewPager2 viewPager;

    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_manage_participants,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        collectionAdapter = new CollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(collectionAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /*@Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }*/
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                if(position==1 && collectionAdapter.fragments[position]!=null){
                    ModifyRacerTabbedFragment fragment_two = (ModifyRacerTabbedFragment)collectionAdapter.fragments[position];
                    fragment_two.refresh_data();
                }

            }

        });

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        tabLayout.getTabAt(0).setText("Add Participant");
        tabLayout.getTabAt(1).setText("Modify Participant");

    }

}
