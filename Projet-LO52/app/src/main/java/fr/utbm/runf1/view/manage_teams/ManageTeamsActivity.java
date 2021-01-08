package fr.utbm.runf1.view.manage_teams;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.Team;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;
import fr.utbm.runf1.view.manage_order_in_team.ManageOrderInTeamActivity;

public class ManageTeamsActivity extends AppCompatActivity implements ManageTeamsRecycleViewAdapter.ItemClickListener {

    private ManageTeamsViewModel manageTeamsViewModel;
    private RecyclerView recyclerView;
    private ManageTeamsRecycleViewAdapter adapter;
    private ArrayList<Runner> runnersList;
    private int numberOfRunnersPerTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_teams);
        this.configureViewModel();
        this.setTitle("Manage Teams");
        this.recyclerView = findViewById(R.id.manageTeamsRecyclerView);
        this.numberOfRunnersPerTeam = getResources().getInteger(R.integer.number_of_runners_per_team);
        this.manageTeamsViewModel.getAllTeamsWithRunners().observe(this, this::assignTeamsWithRunners);
        this.adapter = new ManageTeamsRecycleViewAdapter(this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter.setClickListener(this);
        this.recyclerView.setAdapter(adapter);
        this.manageTeamsViewModel.getAllRunners().observe(this, runners -> runnersList = (ArrayList<Runner>) runners);

    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageTeamsViewModel = new ViewModelProvider(this, viewModelFactory).get(ManageTeamsViewModel.class);
    }

    private void assignTeamsWithRunners(List<TeamWithRunners> teamWithRunnersList ) {

        this.adapter.setTeamsWithRunnersList(teamWithRunnersList.stream().filter(t -> !t.getRunners().isEmpty()).collect(Collectors.toList()));
        recyclerView.setLayoutManager(new GridLayoutManager(
                this,
                getResources().getConfiguration().orientation ==  Configuration.ORIENTATION_PORTRAIT ? 2 : 4)
        );
    }

    @Override
    public void editTeam(View view, int teamId) {
        Intent myIntent = new Intent(ManageTeamsActivity.this, ManageOrderInTeamActivity.class);
        myIntent.putExtra("TEAM_ID", teamId);
        startActivity(myIntent);
    }

    public void generateTeams(View view) {

        if (isNumberOfRunnersMultipleOf(this.runnersList, this.numberOfRunnersPerTeam)){
            showAlertDialog(this.numberOfRunnersPerTeam);
        } else {
            this.manageTeamsViewModel.getAllTeamsWithRunners().removeObservers(this);
            int numberOfTeams = this.runnersList.size() / numberOfRunnersPerTeam;
            ArrayList<Runner> tempRunnerList = (ArrayList<Runner>) this.runnersList
                    .stream()
                    .sorted(Comparator.comparingInt(Runner::getLevel))
                    .collect(Collectors.toList());

            ArrayList<TeamWithRunners> teamWithRunnersList = new ArrayList<>();
            for (int i = 0; i<numberOfTeams; i++) {
                TeamWithRunners teamWithRunners = new TeamWithRunners();
                teamWithRunners.setTeam(new Team());
                teamWithRunnersList.add(teamWithRunners);
            }

            for (int i = 0; i<tempRunnerList.size(); i++) {
                if ( i % numberOfTeams == 0 && (i != 0)) {
                    teamWithRunnersList = (ArrayList<TeamWithRunners>) teamWithRunnersList.stream().sorted(Comparator.comparingInt(TeamWithRunners::getTeamLevel)).collect(Collectors.toList());
                }
                Runner runner = tempRunnerList.get(tempRunnerList.size() - 1 - i);
                runner.setOrderWithinTeam(teamWithRunnersList.get(i % numberOfTeams).getRunners().size() + 1);
                teamWithRunnersList.get(i % numberOfTeams).getRunners().add(runner);
            }

            this.manageTeamsViewModel.insertTeamWithRunners(teamWithRunnersList);
        }
    }

    private void showAlertDialog(int multipleOf) {
        new AlertDialog.Builder(this)
                .setTitle("Problem number of runner")
                .setMessage("Number of runners (" + this.runnersList.size() + ") is not a multiple of " + multipleOf + ", please add or remove runners first.")
                .setCancelable(false)
                .setPositiveButton("OK",(dialog, whichButton) -> {})
                .create()
                .show();
    }

    private boolean isNumberOfRunnersMultipleOf(List<Runner> runnerList, int multipleOf) {
        return runnerList.size() % multipleOf != 0;
    }


}