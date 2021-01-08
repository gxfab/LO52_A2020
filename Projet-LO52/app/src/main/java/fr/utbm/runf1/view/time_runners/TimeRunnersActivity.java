package fr.utbm.runf1.view.time_runners;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;

public class TimeRunnersActivity extends AppCompatActivity{

    private TimeRunnersViewModel timeRunnersViewModel;
    private RecyclerView recyclerView;
    private TimeRunnersRecycleViewAdapter adapter;
    private Chronometer chronometer;
    private boolean isChronometerRunning;
    private List<TeamWithRunnersWithHistory> teamWithRunnersList;
    private boolean reload = true;
    private ImageButton playButton;
    private ImageButton stopButton;
    private Race race;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureViewModel();
        setContentView(R.layout.activity_time_runners);
        this.setTitle("Time runners");
        this.chronometer = findViewById(R.id.timeRunnersChronometer);
        this.recyclerView = findViewById(R.id.timeRunnersRecyclerView);
        this.playButton = findViewById(R.id.timeRunnersStartChronometer);
        this.stopButton = findViewById(R.id.timeRunnersStopChronometer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.stopButton.setEnabled(isChronometerRunning);
        Race newRace = new Race();
        newRace.setDate(Calendar.getInstance().getTime());
        this.timeRunnersViewModel.insertRace(newRace);
        this.timeRunnersViewModel.getLastInsertedRace().observe(this, r -> race = r);
        this.timeRunnersViewModel.getAllTeamsWithRunnersWithHistory().observe(this, this::assignTeamsWithRunners);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.timeRunnersViewModel = new ViewModelProvider(this, viewModelFactory).get(TimeRunnersViewModel.class);
    }

    private void assignTeamsWithRunners(List<TeamWithRunnersWithHistory> teamWithRunnersList ) {
        if (reload) {
            this.adapter = new TimeRunnersRecycleViewAdapter(this, teamWithRunnersList.stream().filter(t -> !t.getRunnersWithHistory().isEmpty()).collect(Collectors.toList()), chronometer, this.timeRunnersViewModel, this.race);
            this.teamWithRunnersList = teamWithRunnersList;
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(
                    this,
                    getResources().getConfiguration().orientation ==  Configuration.ORIENTATION_PORTRAIT ? 2 : 4));
            reload = false; //Since there is nothing to be observed, we don't need to reload
        }
    }

    public void startChronometer(View view) {
        if(!isChronometerRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isChronometerRunning = true;
            reloadAdapterView();
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            this.adapter.setChronometerRunning(isChronometerRunning);
        }
    }


    public void reloadAdapterView() {
        reload = true;
        assignTeamsWithRunners(this.teamWithRunnersList);
    }

    public void stopChronometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        isChronometerRunning = false;
        reloadAdapterView();
        playButton.setEnabled(true);
        stopButton.setEnabled(false);
        this.adapter.setChronometerRunning(isChronometerRunning);
    }
}