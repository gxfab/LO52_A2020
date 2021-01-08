package fr.utbm.runf1.view.manage_order_in_team;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;

public class ManageOrderInTeamActivity extends AppCompatActivity implements ManageOrderInTeamRecycleviewAdapter.ItemClickListener{

    private ManageOrderInTeamViewModel manageOrderInTeamViewModel;
    private RecyclerView recyclerView;
    private TeamWithRunners teamWithRunners;
    Button acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_order_in_team);
        this.configureViewModel();
        this.setTitle("Edit Order");
        int teamId = getIntent().getIntExtra("TEAM_ID", -1);
        this.manageOrderInTeamViewModel.getRunnersInTeam(teamId).observe(this, this::assignTeamWithRunners);
        this.recyclerView = findViewById(R.id.manageOrderInTeamRecyclerView);
        this.manageOrderInTeamViewModel.getTeamWithRunners(teamId).observe(this, this::assignTeamsWithRunnersInRecyclerView);
        acceptButton = findViewById(R.id.manageOrderInTeamAcceptButton);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageOrderInTeamViewModel = new ViewModelProvider(this, viewModelFactory).get(ManageOrderInTeamViewModel.class);
    }

    private void assignTeamWithRunners(TeamWithRunners teamWithRunners) {
        this.teamWithRunners = teamWithRunners;
    }

    private void assignTeamsWithRunnersInRecyclerView(TeamWithRunners teamWithRunnersList ) {
        ManageOrderInTeamRecycleviewAdapter manageOrderInTeamRecycleviewAdapter = new ManageOrderInTeamRecycleviewAdapter(this, teamWithRunnersList.getRunners());
        manageOrderInTeamRecycleviewAdapter.setClickListener(this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new ManageOrderInTeamItemTouchHelper(manageOrderInTeamRecycleviewAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        manageOrderInTeamRecycleviewAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);

        this.recyclerView.setAdapter(manageOrderInTeamRecycleviewAdapter);
        acceptButton.setOnClickListener(view -> acceptOrder(view, manageOrderInTeamRecycleviewAdapter.getRunnerList()));
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void acceptOrder(View view, List<Runner> runnerList) {
        this.manageOrderInTeamViewModel.updateRunnersOrder(this.teamWithRunners, runnerList);
        finish();
    }
}