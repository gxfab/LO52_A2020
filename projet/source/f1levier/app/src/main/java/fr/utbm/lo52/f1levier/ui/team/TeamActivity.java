package fr.utbm.lo52.f1levier.ui.team;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamDetail;
import fr.utbm.lo52.f1levier.ui.runningrace.RunningRaceActivity;
import fr.utbm.lo52.f1levier.viewmodel.TeamViewModel;

public class TeamActivity extends AppCompatActivity implements TeamListAdapter.InteractionListener {

    private static final String NEW_TEAM_DIALOG_CODE = "new_team";

    static final String TEAM_MEMBER_DIALOG_CODE = "team_member";

    public static final String EXTRA_RACE_ID = "race_id";

    private int raceId;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        raceId = getIntent().getIntExtra(EXTRA_RACE_ID, -1);
        if (raceId == -1) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.team_list);
        final TeamListAdapter adapter = new TeamListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teamViewModel.getAllDetailedTeamsByRaceId(raceId).observe(this,
                new Observer<List<TeamDetail>>() {
                    @Override
                    public void onChanged(@Nullable final List<TeamDetail> teams) {
                        adapter.setTeams(teams);
                    }
                });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        FloatingActionButton nextFab = findViewById(R.id.fab_next);
        nextFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : check team constraints and show dialog if not valid

                if (adapter.getItemCount() == 0) {
                    Toast toast = Toast.makeText(TeamActivity.this,
                            getString(R.string.create_one_team_at_least), Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    new checkConstraintsAsyncTask(teamViewModel, TeamActivity.this, raceId)
                            .execute();
                }

                /*if (false) {
                    // show dialog
                }
                else {
                    Intent runningRaceIntent = new Intent(TeamActivity.this,
                            RunningRaceActivity.class);
                    runningRaceIntent.putExtra(RunningRaceActivity.EXTRA_RACE_ID, raceId);
                    startActivity(runningRaceIntent);
                }*/
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_team, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_team:
                NewTeamDialog newTeamDialog = NewTeamDialog.newInstance(raceId);
                newTeamDialog.show(Objects.requireNonNull(getSupportFragmentManager()),
                        NEW_TEAM_DIALOG_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTeamItemClicked(TeamDetail team) {
        TeamMemberDialog teamMemberDialog = TeamMemberDialog.newInstance(raceId, team.id);
        teamMemberDialog.show(getSupportFragmentManager(), TEAM_MEMBER_DIALOG_CODE);
    }


    private static class checkConstraintsAsyncTask extends AsyncTask<Void, Void, Integer> {

        private TeamViewModel teamViewModel;

        private WeakReference<Context> contextWeakReference;

        private int raceId;

        checkConstraintsAsyncTask(TeamViewModel teamViewModel, Context context, int raceId) {
            this.teamViewModel = teamViewModel;
            this.contextWeakReference = new WeakReference<>(context);
            this.raceId = raceId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return teamViewModel.checkConstraintsSync(raceId);
        }

        @Override
        protected void onPostExecute(Integer count) {
            Log.d("count", String.valueOf(count));
            Context context = contextWeakReference.get();
            if (context != null) {
                if (count > 0) {
                    Toast toast = Toast.makeText(context,
                            context.getString(R.string.each_team_must_contain_three_members),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Intent runningRaceIntent = new Intent(context, RunningRaceActivity.class);
                    runningRaceIntent.putExtra(RunningRaceActivity.EXTRA_RACE_ID, raceId);
                    context.startActivity(runningRaceIntent);
                }
            }
            else {
                // TODO : Find a way to resolve the context reference lost problem
            }
        }
    }
}
