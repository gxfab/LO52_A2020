package fr.utbm.lo52.f1levier.ui.runningrace;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamNameAndMemberIds;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;
import fr.utbm.lo52.f1levier.ui.stats.StatsActivity;
import fr.utbm.lo52.f1levier.utils.Chronometer;
import fr.utbm.lo52.f1levier.viewmodel.RunningRaceViewModel;

public class RunningRaceActivity extends AppCompatActivity
        implements View.OnClickListener, Chronometer.ChronometerRunner,
        RunningRaceUtils.TeamGetHandler, RunningTeamListAdapter.InteractionListener {

    public static final String EXTRA_RACE_ID = "race_id";

    private int raceId;

    private TextView chronometerTextView;

    private Chronometer chronometer;
    private Thread threadChronometer;

    private RunningRaceViewModel runningRaceViewModel;

    RunningTeamListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        raceId = getIntent().getIntExtra(EXTRA_RACE_ID, -1);
        if (raceId == -1) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_race);

        chronometerTextView = findViewById(R.id.text_view_chronometer);
        chronometerTextView.setOnClickListener(this);

        chronometer = new Chronometer(this);
        threadChronometer = new Thread(chronometer);

        RecyclerView recyclerView = findViewById(R.id.running_team_list);
        adapter = new RunningTeamListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        runningRaceViewModel = ViewModelProviders.of(this).get(RunningRaceViewModel.class);

        new RunningRaceUtils.TeamsGetAsyncTask(runningRaceViewModel, this).execute(raceId);

        FloatingActionButton finishFab = findViewById(R.id.fab_finish);
        finishFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_chronometer:
                if (!chronometer.isRunning()) {
                    chronometer.start();
                    threadChronometer.start();
                    runningRaceViewModel.startRaceAsync(raceId);
                }
                break;
            case R.id.fab_finish:
                if (chronometer.isRunning()) {
                    if (adapter.haveAllTeamsFinished()) {
                        finishRace();
                    }
                    else {
                        Toast toast = Toast.makeText(this,
                                getString(R.string.some_teams_have_not_finished),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                break;
        }
    }

    public void updateTimerText(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chronometerTextView.setText(time);
            }
        });
    }

    @Override
    public void onTeamGetPostExecute(List<TeamNameAndMemberIds> teams) {
        if (teams.size() == 0)
            finish();
        adapter.setTeams(teams);
    }

    @Override
    public void onRunningTeamItemClicked(TeamNameAndMemberIds team) {
        if (chronometer.isRunning() && team.currentRunner < team.memberIds.size()) {
            long clickTime = chronometer.getSince();
            long elapsed = clickTime - team.lastTime;
            team.lastTime = clickTime;

            runningRaceViewModel.insertMeasuredTimeAsync(
                    new MeasuredTime(team.memberIds.get(team.currentRunner).intValue(), raceId,
                            team.step, team.currentLapType, elapsed));

            Toast toast = Toast.makeText(this, getString(R.string.team_member_lap_and_time,
                    team.name, team.currentRunner+1, team.step,
                    chronometer.format(elapsed)), Toast.LENGTH_SHORT);
            toast.show();

            if (team.lapsQueue.size() > 0) {
                team.currentLapType = (int) team.lapsQueue.poll();
                team.step += 1;
            }
            else {
                team.currentRunner += 1;
                if (team.currentRunner < team.memberIds.size()) {
                    team.lapsQueue.addAll(MeasuredTime.queue);
                    team.currentLapType = (int) team.lapsQueue.poll();
                    team.step = 1;
                }
            }
        }
    }

    private void finishRace() {
        new FinishRaceAsyncTask(runningRaceViewModel, this).execute(raceId);
        chronometer.stop();
        threadChronometer.interrupt();
    }


    private static class FinishRaceAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private RunningRaceViewModel runningRaceViewModel;

        private WeakReference<Context> contextWeakReference;

        FinishRaceAsyncTask(RunningRaceViewModel runningRaceViewModel, Context context) {
            this.runningRaceViewModel = runningRaceViewModel;
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            runningRaceViewModel.finishRaceSync(params[0]);
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer raceId) {
            Context context = contextWeakReference.get();
            if (context != null) {
                Intent teamIntent = new Intent(context, StatsActivity.class);
                teamIntent.putExtra(StatsActivity.EXTRA_RACE_ID, raceId);
                context.startActivity(teamIntent);
            }
            else {
                // TODO : Find a way to resolve the context reference lost problem
            }
        }
    }
}
