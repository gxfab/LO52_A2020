package com.dayetfracso.codep25.utils;

import android.content.Context;

import com.dayetfracso.codep25.entity.Team;

import java.util.Comparator;

public class TeamTimeComparator implements Comparator<Team>{
    private Context context;
    private long raceId;
    public TeamTimeComparator(Context context, long raceId) {
        this.context = context;
        this.raceId = raceId;
    }


    @Override
    public int compare(Team a, Team b){
        long diff = a.getGlobalTimeOnRace(context, raceId) - b.getGlobalTimeOnRace(context, raceId);
        if(diff > 0)
            return 1;
        else if(diff < 0)
            return -1;
        else
            return (int)(a.getGlobalTimeOnRace(context,raceId)-b.getGlobalTimeOnRace(context,raceId));
    }
}

