package fr.utbm.runf1.view.time_runners;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.entities.RunnerHistory;
import fr.utbm.runf1.entities.relations.RunnerWithHistory;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;

/**
 * Created by Yosef B.I.
 */
public class TimeRunnersRecycleViewAdapter  extends RecyclerView.Adapter<TimeRunnersRecycleViewAdapter.ViewHolder> {

    private List<TeamWithRunnersWithHistory> teamsWithRunnersList;
    private LayoutInflater layoutInflater;
    private Race race;
    private boolean isChronometerRunning;
    private Chronometer chronometer;
    TimeRunnersViewModel timeRunnersViewModel;
    private final int NUMBER_OF_ACTIVITIES = 5;
    private final String[] activityNames = new String[] {"Sprint 1", "Obstacle 1", "Pit Stop", "Sprint 2", "Obstacle 2", "Next"};

    public TimeRunnersRecycleViewAdapter(Context context, List<TeamWithRunnersWithHistory> teamsWithRunnersList, Chronometer chronometer, TimeRunnersViewModel timeRunnersViewModel, Race race) {
        this.layoutInflater = LayoutInflater.from(context);
        this.teamsWithRunnersList = teamsWithRunnersList;
        this.chronometer = chronometer;
        this.timeRunnersViewModel = timeRunnersViewModel;
        this.race = race;
    }

    @NonNull
    @Override
    public TimeRunnersRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_time_runners_row, parent, false);
        return new TimeRunnersRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeRunnersRecycleViewAdapter.ViewHolder holder, int position) {
        TeamWithRunnersWithHistory teamWithRunners = teamsWithRunnersList.get(position);

        holder.race = race;
        holder.nextActivityTextView.setText(this.activityNames[0]);
        holder.teamWithRunnersWithHistory = teamWithRunners;
        holder.currentRunnerWithHistory = teamWithRunners.getRunnersWithHistory().get(0);
        holder.teamSize = teamWithRunners.getRunnersWithHistory().size();
        holder.chronometer = chronometer;
        holder.enableButtons(this.isChronometerRunning);
        holder.timeRunnersViewModel = this.timeRunnersViewModel;
        holder.timeButton.setText(teamWithRunners.getRunnersWithHistory().get(0).getRunner().getFirstName() + " " + 1 + "/" + teamWithRunners.getRunnersWithHistory().size());
        if(holder.hasNextRunnerInTeam(holder.currentRunnerWithHistory)) holder.nextRunnerTextView.setText(Objects.requireNonNull(holder.nextRunnerInTeam(holder.currentRunnerWithHistory).getRunner()).getFirstName());
    }


    @Override
    public int getItemCount() {
        return this.teamsWithRunnersList.size();
    }

    public void setChronometerRunning(boolean isChronometerStarted) {
        this.isChronometerRunning = isChronometerStarted;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TimeRunnersViewModel timeRunnersViewModel;
        TextView nextRunnerTextView;
        TextView nextActivityTextView;
        TeamWithRunnersWithHistory teamWithRunnersWithHistory;
        int teamSize;
        RunnerWithHistory currentRunnerWithHistory;
        RunnerHistory runnerHistory = new RunnerHistory();
        int timeRecording = 0; //represents what we are recording (sprint, obstacle, pit-stop, ...)*
        Button timeButton;
        boolean isChronometerStarted;
        Chronometer chronometer;
        ProgressBar progressBar;
        Race race;
        boolean registerAll = false;
        int runnerCount = 1;

        ViewHolder(View itemView) {
            super(itemView);
            timeButton = itemView.findViewById(R.id.timeRunnersTime);
            nextRunnerTextView = itemView.findViewById(R.id.timeRunnersRecyclerViewNextRunner);
            nextActivityTextView = itemView.findViewById(R.id.timeRunnersRecyclerViewNextActivity);
            progressBar = itemView.findViewById(R.id.progressBar);
            timeButton.setEnabled(this.isChronometerStarted);
            timeButton.setOnClickListener(view -> goToNextStep(this.timeRecording));
            this.progressBar.setMax(NUMBER_OF_ACTIVITIES + 1);
        }

        public void enableButtons(boolean enable) {
            timeButton.setEnabled(enable);
        }

        private void goToNextStep(int timeRecording) {
            if (timeRecording < NUMBER_OF_ACTIVITIES) {
                switch (timeRecording) {
                    case 0: runnerHistory.setTimeSprint1(SystemClock.elapsedRealtime() - this.chronometer.getBase());
                    case 1: runnerHistory.setTimeObstacle1(SystemClock.elapsedRealtime() - this.chronometer.getBase());
                    case 2: runnerHistory.setTimePitStop(SystemClock.elapsedRealtime() - this.chronometer.getBase());
                    case 3: runnerHistory.setTimeSprint2(SystemClock.elapsedRealtime() - this.chronometer.getBase());
                    case 4: runnerHistory.setTimeObstacle2(SystemClock.elapsedRealtime() - this.chronometer.getBase());
                }
                this.timeRecording++;
                nextActivityTextView.setText(activityNames[this.timeRecording]);

            } else {
                if (hasNextRunnerInTeam(currentRunnerWithHistory)) { //There is a next runner
                    runnerHistory.setRunnerId(currentRunnerWithHistory.getRunner().getId());
                    runnerHistory.setRaceId(race.getId());
                    timeRunnersViewModel.insertRunnerHistory(runnerHistory);
                    runnerHistory = new RunnerHistory();
                    currentRunnerWithHistory = nextRunnerInTeam(currentRunnerWithHistory);
                    runnerCount++;
                    timeButton.setText(currentRunnerWithHistory.getRunner().getFirstName() + " " + runnerCount + "/" + teamWithRunnersWithHistory.getRunnersWithHistory().size());
                    this.timeRecording = 0;
                    nextActivityTextView.setText(activityNames[this.timeRecording]);
                    this.setNextRunnerText(currentRunnerWithHistory);
                } else { //End of list
                    Duration duration = Duration.ofMillis(runnerHistory.getTimeObstacle2());
                    nextActivityTextView.setText("");
                    runnerHistory.setRunnerId(currentRunnerWithHistory.getRunner().getId());
                    runnerHistory.setRaceId(race.getId());
                    timeRunnersViewModel.insertRunnerHistory(runnerHistory);
                    timeButton.setText("Done in " + duration.toMinutes() + ":" + duration.getSeconds());
                    timeButton.setEnabled(false);
                    this.timeRecording++; //For the progressbar
                }
            }
            this.progressBar.setProgress(this.timeRecording);
        }

        private void setNextRunnerText(RunnerWithHistory currentRunnerWithHistory) {
            if (hasNextRunnerInTeam(currentRunnerWithHistory)) {
                this.nextRunnerTextView.setText(Objects.requireNonNull(this.nextRunnerInTeam(this.currentRunnerWithHistory).getRunner()).getFirstName());
            } else {
                this.nextRunnerTextView.setText("");
            }
        }

        private RunnerWithHistory nextRunnerInTeam(RunnerWithHistory runnerWithHistory) {
            int currentRunnerInList = teamWithRunnersWithHistory.getRunnersWithHistory().indexOf(runnerWithHistory);
            if (hasNextRunnerInTeam(runnerWithHistory)) {
                currentRunnerInList++;
                return teamWithRunnersWithHistory.getRunnersWithHistory().get(currentRunnerInList);
            }
            else return null;
        }

        private boolean hasNextRunnerInTeam(RunnerWithHistory runnerWithHistory) {
            return teamWithRunnersWithHistory.getRunnersWithHistory().indexOf(runnerWithHistory) < this.teamSize - 1;
        }
    }
}
