package com.dayetfracso.codep25.ui.race;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dayetfracso.codep25.R;
import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.entity.Race;
import com.dayetfracso.codep25.entity.Runner;
import com.dayetfracso.codep25.entity.RunnerStats;
import com.dayetfracso.codep25.entity.Team;
import com.dayetfracso.codep25.entity.TeamWithRunners;
import com.dayetfracso.codep25.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TeamResultFragment extends Fragment {
    AppDatabase database;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_team_result, container, false);

        database = AppDatabase.getDatabase(getContext());

        long raceID = getArguments().getLong("raceId");
        long teamID = getArguments().getLong("teamId");

        TeamWithRunners teamWithRunners = database.teamDao().getTeamWithRunners(teamID);
        Team team = teamWithRunners.team;
        List<Runner> runners = teamWithRunners.runners;
        Race race = database.raceDao().getRace(raceID);


        // Set action bar title
//        getSupportActionBar().setTitle(getResources().getString(R.string.results_of_team) + " \"" +team.getName()+"\"");

        LinearLayout bigContainer = root.findViewById(R.id.bigContainer);
        bigContainer.setOrientation(LinearLayout.VERTICAL);




        for (final Runner runner : runners) {

            // Runner Name
            TextView textViewRunnerName = new TextView(getContext());
            textViewRunnerName.setText(runner.getFullName() + " (" + runner.getLevel() + ")");
            bigContainer.addView(textViewRunnerName);
            textViewRunnerName.setGravity(Gravity.CENTER);

            // Grid for results
            GridLayout grid = new GridLayout(getContext());
            grid.setColumnCount(6);

            // Set headers
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Sprint) + " 1"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Fract_) + " 1"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Pit_stop)));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Sprint) + " 2"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Fract_) + " 2"));
            grid.addView(getTextViewHeaderCell(getResources().getString(R.string.Total)));

            // Arrays to compute averages
            List<Long> listTimeSprint1 = new ArrayList<>();
            List<Long> listTimeFractionned1 = new ArrayList<>();
            List<Long> listTimePitStop = new ArrayList<>();
            List<Long> listTimeSprint2 = new ArrayList<>();
            List<Long> listTimeFractionned2 = new ArrayList<>();
            List<Long> listTimeGlobal = new ArrayList<>();

            //  -----------------------------------------------------

            TextView tvBestSprint1 = null;
            long bestSprint1 = Long.MAX_VALUE;

            TextView tvBestFractionned1 = null;
            long bestFractionned1 = Long.MAX_VALUE;

            TextView tvBestSprint2 = null;
            long bestSprint2 = Long.MAX_VALUE;

            TextView tvBestPitStop = null;
            long bestPitStop = Long.MAX_VALUE;

            TextView tvBestFractionned2 = null;
            long bestFractionned2 = Long.MAX_VALUE;

            TextView tvBestGlobal = null;
            long bestGlobal = Long.MAX_VALUE;

            List<RunnerStats> runnerStats1 = database.runnerStatsDao().getAllRunnerStats(runner.getRunnerId());

            for (RunnerStats runnerStats : runnerStats1) {
                TextView tv;

                // Sprint 1
                listTimeSprint1.add(runnerStats.getSprint1());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getSprint1()));
                if (runnerStats.getSprint1() < bestSprint1) {
                    bestSprint1 = runnerStats.getSprint1();
                    tvBestSprint1 = tv;
                }
                grid.addView(tv);

                // Fractionned 1
                listTimeFractionned1.add(runnerStats.getObstacle1());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getObstacle1()));
                if (runnerStats.getObstacle1() < bestFractionned1) {
                    bestFractionned1 = runnerStats.getObstacle1();
                    tvBestFractionned1 = tv;
                }
                grid.addView(tv);

                // Pit stop
                listTimePitStop.add(runnerStats.getPitstop());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getPitstop()));
                if (runnerStats.getPitstop() < bestPitStop) {
                    bestPitStop = runnerStats.getPitstop();
                    tvBestPitStop = tv;
                }
                grid.addView(tv);

                // Sprint 2
                listTimeSprint2.add(runnerStats.getSprint2());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getSprint2()));
                if (runnerStats.getSprint2() < bestSprint2) {
                    bestSprint2 = runnerStats.getSprint2();
                    tvBestSprint2 = tv;
                }
                grid.addView(tv);

                // Fractionned 2
                listTimeFractionned2.add(runnerStats.getObstacle2());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getObstacle2()));
                if (runnerStats.getObstacle2() < bestFractionned2) {
                    bestFractionned2 = runnerStats.getObstacle2();
                    tvBestFractionned2 = tv;
                }
                grid.addView(tv);

                // Global
                listTimeGlobal.add(runnerStats.getGlobalTime());
                tv = getTextViewNormalCell(Utils.formatTime(runnerStats.getGlobalTime()));
                if (runnerStats.getGlobalTime() < bestGlobal) {
                    bestGlobal = runnerStats.getGlobalTime();
                    tvBestGlobal = tv;
                }
                grid.addView(tv);
            }

            //-------------------------------------------------------------------------------------------


            // Average Sprint 1
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimeSprint1))));

            // Average Fractionned 1
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimeFractionned1))));

            // Average Pit stop
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimePitStop))));

            // Average Sprint 2
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimeSprint2))));

            // Average Fractionned 2
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimeFractionned2))));

            // Average Global
            grid.addView(getTextViewMeanCell(Utils.formatTime(Utils.average(listTimeGlobal))));

            bigContainer.addView(grid);


            if (tvBestSprint1 != null) tvBestSprint1.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
            if (tvBestFractionned1 != null) tvBestFractionned1.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
            if (tvBestPitStop != null) tvBestPitStop.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
            if (tvBestSprint2 != null) tvBestSprint2.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
            if (tvBestFractionned2 != null) tvBestFractionned2.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
            if (tvBestGlobal != null) tvBestGlobal.setTextColor(ContextCompat.getColor(

                    getContext(), R.color.colorAccent));
        }

        return root;
    }

    @SuppressLint("ResourceType")
    TextView getTextViewHeaderCell(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setPadding(5, 1, 5, 1);
        tv.setBackgroundResource(R.layout.grid_items_border_with_bg);
        tv.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        }
        return tv;
    }

    @SuppressLint("ResourceType")
    TextView getTextViewNormalCell(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setPadding(5, 1, 5, 1);
        tv.setBackgroundResource(R.layout.grid_items_border);
        tv.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        }
        return tv;
    }

    @SuppressLint("ResourceType")
    TextView getTextViewMeanCell(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setPadding(5, 1, 5, 1);
        tv.setBackgroundResource(R.layout.grid_items_border);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.meanCellColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tv.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
        }
        return tv;
    }
}