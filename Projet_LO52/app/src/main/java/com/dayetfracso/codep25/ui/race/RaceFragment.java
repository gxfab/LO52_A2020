package com.dayetfracso.codep25.ui.race;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dayetfracso.codep25.R;
import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.entity.Race;
import com.dayetfracso.codep25.entity.RaceTeamCrossRef;
import com.dayetfracso.codep25.entity.Runner;
import com.dayetfracso.codep25.entity.RunnerStats;
import com.dayetfracso.codep25.entity.TeamWithRunners;
import com.dayetfracso.codep25.utils.SegmentedProgressDrawable;
import com.dayetfracso.codep25.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RaceFragment extends Fragment {
    private AppDatabase database;
    private List<TeamWithRunners> teamsWithRunners;

    private ArrayList<Button> listTriggerButtons;
    private Race race;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {

        // Initializing DB
        database = AppDatabase.getDatabase(getContext());
        teamsWithRunners = database.teamDao().getTeamsWithRunners();

        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_race, container, false);

        //=================================================================
        //              Chronometer AND start/stop button creation
        //=================================================================

        // Retrieve chronometer from XML file
        final Chronometer chronometer = (Chronometer) root.findViewById(R.id.chronometer);

        // Retrieve START/STOP button from XML file
        final Button buttonChronometer = (Button) root.findViewById(R.id.buttonChronometer);
        buttonChronometer.setText("START");
        buttonChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If started
                if(buttonChronometer.getText()=="START"){

                    // Set stop
                    buttonChronometer.setText("STOP");
//                    Drawable rightButtonIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_pause);
//                    buttonChronometer.setCompoundDrawablesWithIntrinsicBounds(null,null , rightButtonIcon, null);

                    // Enable all buttons
                    for (Button b : listTriggerButtons)
                        b.setEnabled(true);

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();

                }
                // If stoped
                else
                {
                    chronometer.stop();
                    // Go to result activity
                    goToResults();
                }
            }
        });

        //=================================================================
        //              Get data
        //=================================================================

        // Create a course and put its primary key here
        Long raceID = database.raceDao().insertRace(new Race());
        race = database.raceDao().getRace(raceID);

        for(TeamWithRunners teamWithRunners : teamsWithRunners)
        {
            database.raceDao().insertRaceTeamCrossRef(new RaceTeamCrossRef(raceID,teamWithRunners.team.getTeamId()));
        }

        // Set action bar title
//        getSupportActionBar().setTitle(getResources().getString(R.string.race) + " \"" + race.getName() + "\"");


//        Import all of our teams and put them here
        final List<TeamWithRunners> teamsListWithRunners = database.teamDao().getTeamsWithRunners();
//        final LapTimeDAO lapTimeDAO=new LapTimeDAO(this);

        //====================================================================
        //              Rows :
        // - Button to trigger the chronometer
        // - Progress bar to display the current lap progression
        // - Individuals progress bar to display the progression of each runners
        //====================================================================

        LinearLayout linearLayoutContainer = (LinearLayout) root.findViewById(R.id.linearLayout);

        listTriggerButtons = new ArrayList<Button>();

        final AtomicInteger nbTeamFinished = new AtomicInteger(0);

        for(final TeamWithRunners teamWithRunners : teamsListWithRunners){
            // Create a ROW linearLayout (Horizontal)
            LinearLayout teamLinearLayoutRow = new LinearLayout(getContext());
            teamLinearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            teamLinearLayoutRow.setGravity(Gravity.CENTER);
            teamLinearLayoutRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            teamLinearLayoutRow.setPadding(0,10,0,10);

            // Add it to the linearLayoutContainer
            linearLayoutContainer.addView(teamLinearLayoutRow);

            // Create button
            Button teamTriggerButton = new Button(getContext());
            teamTriggerButton.setText("Team " + Integer.toString(teamWithRunners.team.getTeamId()));
            teamTriggerButton.setEnabled(false);
            teamTriggerButton.setWidth(Utils.mapPXtoDP(getContext(),100));

            // Add it to the teamLinearLayoutRow and to the list of trigger buttons
            teamLinearLayoutRow.addView(teamTriggerButton);
            listTriggerButtons.add(teamTriggerButton);

            // Create a widget for each player  (Vertical)
            LinearLayout teamPlayerLinearLayout = new LinearLayout(getContext());
            teamPlayerLinearLayout.setOrientation(LinearLayout.VERTICAL);
            teamPlayerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

            // Add it to the teamLinearLayoutRow
            teamLinearLayoutRow.addView(teamPlayerLinearLayout);

            // Create a frame layout for the progressbar + labels on overlay
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            teamPlayerLinearLayout.addView(frameLayout);

            // Create Progress bar to display current progression ("Sprint", "Obstacle 1", "Pit Stop", "Sprint", "Obstacle 2")
            final ProgressBar racePhaseProgressBar = createProgressBarCurrentLap();
            // Create a grid layout holding the legend
            GridLayout gridLayout = createGridLegend();

            frameLayout.addView(racePhaseProgressBar);
            frameLayout.addView(gridLayout);

            // Create a SUB SUB linearLayout (Horizontal)
            LinearLayout linearLayoutSubSub = new LinearLayout(getContext());
            linearLayoutSubSub.setGravity(Gravity.CENTER_VERTICAL);
            linearLayoutSubSub.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutSubSub.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

            // Add it to the teamPlayerLinearLayout
            teamPlayerLinearLayout.addView(linearLayoutSubSub);


            final List<ProgressBar> runnersProgressBarsList = new ArrayList<ProgressBar>();
            final List<RunnerStats> runnerStatsList = new ArrayList<>();

            for(Runner runner : teamWithRunners.runners)
            {
                // Create a text view with the name of the runner
                TextView textViewRunnerLabel = new TextView(getContext());
                textViewRunnerLabel.setText(runner.getFullName());
                textViewRunnerLabel.setPadding(20,0,20,0);
                // Add it to the linearLayoutSubSub
                linearLayoutSubSub.addView(textViewRunnerLabel);

                // Create a progressbar to display individual progression of each runners
                final ProgressBar runnerProgressBar = createRunnerProgressBar(5);

                // Add it to the linearLayoutSubSub
                linearLayoutSubSub.addView(runnerProgressBar);

                // Add it to a list
                runnersProgressBarsList.add(runnerProgressBar);

                RunnerStats runnerStats = new RunnerStats();
                runnerStats.setRunnerId(runner.getRunnerId());
                runnerStats.setRaceId(raceID);
                runnerStatsList.add(runnerStats);
            }

            final AtomicLong previousTime = new AtomicLong(SystemClock.elapsedRealtime() - chronometer.getBase());

            teamTriggerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    List<Integer> listProgressOnEachBars = new ArrayList<Integer>();

                    int currentRunnerIndex = 0;
                    for(ProgressBar p : runnersProgressBarsList)
                    {
                        int individualProgress = p.getProgress();
                        if(individualProgress == 5) {
                            currentRunnerIndex++;
                            continue;
                        }
                        else
                            break;

                    }

                    int lapProgress = racePhaseProgressBar.getProgress() + 1;
                    if(lapProgress > 5)
                        lapProgress = 1;

                    // Get the index of the minimum value the most on the left
//                    int runnerIndex = listProgressOnEachBars.indexOf(Collections.min(listProgressOnEachBars));

                    // We can get the current lap
                    RunnerStats runnerStats = runnerStatsList.get(currentRunnerIndex);

                    // And the individual progressbar
                    ProgressBar currentRunnerProgressBar = runnersProgressBarsList.get(currentRunnerIndex);

                    // Get the elapsed time
                    long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase() - previousTime.get();

                    switch (lapProgress)
                    {
                        case 1:
                            runnerStats.setSprint1(elapsedMillis);
                            currentRunnerProgressBar.setProgress(currentRunnerProgressBar.getProgress()+1);
                            break;
                        case 2:
                            runnerStats.setObstacle1(elapsedMillis);
                            currentRunnerProgressBar.setProgress(currentRunnerProgressBar.getProgress()+1);
                            break;
                        case 3:
                            runnerStats.setPitstop(elapsedMillis);
                            currentRunnerProgressBar.setProgress(currentRunnerProgressBar.getProgress()+1);
                            break;
                        case 4:
                            runnerStats.setSprint2(elapsedMillis);
                            currentRunnerProgressBar.setProgress(currentRunnerProgressBar.getProgress()+1);
                            break;
                        case 5:
                            runnerStats.setObstacle2(elapsedMillis);

                            // Add lap time to database
                            database.runnerStatsDao().insertRunnerStats(runnerStats);

                            // Increment individual progress
                            currentRunnerProgressBar.setProgress(currentRunnerProgressBar.getProgress()+1);
                            break;
                    }

                    // Increment current lap progression on the progress bar
                    racePhaseProgressBar.setProgress(lapProgress);

                    // If all the runners of the time have finished
                    if(currentRunnerIndex==2 && lapProgress==5)
                    {
                        // Disable the button of the team
                        listTriggerButtons.get(teamsListWithRunners.indexOf(teamWithRunners)).setEnabled(false);

                        // Check if everyone have finished
                        if(nbTeamFinished.incrementAndGet() >= teamsListWithRunners.size()){
                            // Start the result activity
                            goToResults();
                        }
                    }
                    previousTime.set(elapsedMillis + previousTime.get());
                }
            });

        }

        return root;
    }

    public void goToResults(){
        // Create new fragment and transaction
        Fragment resultFragment = new ResultFragment();
        // consider using Java coding conventions (upper first char class names!!!)
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.nav_host_fragment, resultFragment);
        transaction.addToBackStack("race");

        // Commit the transaction
        transaction.commit();
    }

    public ProgressBar createRunnerProgressBar(int nbOfSegments) {
        ProgressBar progressBarIndividual = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
        progressBarIndividual.setProgressDrawable(new SegmentedProgressDrawable(ContextCompat.getColor(getContext(),R.color.colorProgressFg),ContextCompat.getColor(getContext(),R.color.colorProgressBg), nbOfSegments));
        progressBarIndividual.setMax(nbOfSegments);
        progressBarIndividual.setProgress(0);
        progressBarIndividual.setPadding(0,10,0,10);
        // Calculate and apply progressBar size according to the number of runner in the current team
        progressBarIndividual.setLayoutParams(new LinearLayout.LayoutParams(nbOfSegments * Utils.mapPXtoDP(getContext(), 10),LinearLayout.LayoutParams.WRAP_CONTENT));
        return progressBarIndividual;
    }

    public ProgressBar createProgressBarCurrentLap() {
        ProgressBar progressBarCurrentLap = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
        progressBarCurrentLap.setProgressDrawable(new SegmentedProgressDrawable(ContextCompat.getColor(getContext(),R.color.colorProgressFg),ContextCompat.getColor(getContext(),R.color.colorProgressBg)));
        progressBarCurrentLap.setMax(5);
        progressBarCurrentLap.setProgress(0);
        progressBarCurrentLap.setPadding(0,0,0,0);
        progressBarCurrentLap.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return progressBarCurrentLap;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public GridLayout createGridLegend(){
        // Create a grid layout to hold the 5 labels ("Sprint", "Obstacle 1", "Pit Stop", "Sprint", "Obstacle 2")
        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setColumnCount(5);
        gridLayout.setRowCount(1);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        gridLayout.setForegroundGravity(Gravity.CENTER);

        List<String> list = Arrays.asList("Sprint", "Obstacle 1", "Pit Stop", "Sprint", "Obstacle 2");
        for(String s : list)
        {
            TextView textViewLegend = new TextView(getContext());
            textViewLegend.setText(s);
            textViewLegend.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            textViewLegend.setTypeface(Typeface.DEFAULT_BOLD);
            textViewLegend.setTextColor(Color.WHITE);
            gridLayout.addView(textViewLegend);
            GridLayout.LayoutParams paramsLegend= new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED,GridLayout.FILL,1f),
                    GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f));
            paramsLegend.setGravity(Gravity.CENTER);
            textViewLegend.setLayoutParams(paramsLegend);
        }
        return gridLayout;
    }
}