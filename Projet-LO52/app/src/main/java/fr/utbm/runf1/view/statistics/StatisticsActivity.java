package fr.utbm.runf1.view.statistics;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.RunnerHistory;
import fr.utbm.runf1.entities.relations.RunnerWithHistory;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsViewModel manageTeamsViewModel;
    private StatisticsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        this.configureViewModel();

        RecyclerView recyclerView = findViewById(R.id.statisticsRecyclerView);
        this.manageTeamsViewModel.getAllTeamsWithRunners().observe(this, this::assignTeamsWithRunners);
        this.adapter = new StatisticsRecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void assignTeamsWithRunners(List<TeamWithRunnersWithHistory> teamWithRunners) {
        int raceId = getIntent().getIntExtra("EXTRA_RACE_ID", -1);
        teamWithRunners = teamWithRunners.stream().filter(t -> !t.getRunnersWithHistory().isEmpty()).collect(Collectors.toList());
        for (TeamWithRunnersWithHistory teamWithRunnersWithHistory : teamWithRunners){
            for (RunnerWithHistory runnerWithHistory : teamWithRunnersWithHistory.getRunnersWithHistory()){
                RunnerHistory temp = runnerWithHistory.getRunnerHistory().stream().filter(runnerHistory -> runnerHistory.getRaceId() == raceId).findFirst().orElse(new RunnerHistory());
                runnerWithHistory.setRunnerHistory(new ArrayList<>());
                runnerWithHistory.getRunnerHistory().add(temp);
            }
        }
        this.adapter.setTeamWithRunners(teamWithRunners);
        this.adapter.notifyDataSetChanged();
    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageTeamsViewModel = new ViewModelProvider(this, viewModelFactory).get(StatisticsViewModel.class);
    }
}