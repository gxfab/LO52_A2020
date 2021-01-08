package com.example.lo52_project_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.lo52_project_v2.model.bo.Participant;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class TeamsFragment extends Fragment {

    static class ViewHolder{
        TextView tv_Name;
        TextView tv_Level;
    }

    TeamFragmentCollectionAdapter TFCollectionAdapter;
    ViewPager2 team_viewPager;

    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_teams,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        TFCollectionAdapter = new TeamFragmentCollectionAdapter(this);
        team_viewPager = view.findViewById(R.id.teams_pager);
        team_viewPager.setAdapter(TFCollectionAdapter);

        team_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageSelected(int position){
               super.onPageSelected(position);
               if(position==1 && TFCollectionAdapter.fragments[position]!=null){
                   ModifyTeamsTabbedFragment fragment_two = (ModifyTeamsTabbedFragment)TFCollectionAdapter.fragments[position];
                   fragment_two.refresh_data();
               }
           }
        });

        TabLayout TeamstabLayout = view.findViewById(R.id.teams_tab_layout);
        new TabLayoutMediator(TeamstabLayout, team_viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        TeamstabLayout.getTabAt(0).setText("Generate Teams");
        TeamstabLayout.getTabAt(1).setText("Modify Team Order");

    }
}
