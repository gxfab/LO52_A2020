package fr.utbm.runf1.view.statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.relations.TeamWithRunnersWithHistory;

/**
 * Created by Yosef B.I.
 */
public class StatisticsRecyclerViewAdapter extends RecyclerView.Adapter<StatisticsRecyclerViewAdapter.ViewHolder> {

    private List<TeamWithRunnersWithHistory> teamWithRunners = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public StatisticsRecyclerViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.activity_statistics_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.teamName.setText("Team " + (position + 1));
        StringBuilder runnerNames = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            runnerNames.append(runnerWithHistory.getRunner().getFirstName());
            runnerNames.append(" ");
            runnerNames.append(runnerWithHistory.getRunner().getLastName());
            runnerNames.append("\n");
        });
        if (runnerNames.length() > 1) runnerNames.setLength(runnerNames.length() - 1);
        holder.runnerName.setText(runnerNames);


        StringBuilder runnerTime1 = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            if (runnerWithHistory.getLastHistory() != null) {
                runnerTime1.append(this.getTimeInCorrectFormat(runnerWithHistory.getRunnerHistory().get(0).getTimeSprint1()));
            } else {
                runnerTime1.append("N/A");
            }
            runnerTime1.append("\n");
        });
        if (runnerNames.length() > 1) runnerTime1.setLength(runnerTime1.length() - 1);
        holder.runnerActivity1.setText(runnerTime1);


        StringBuilder runnerTime2 = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            if (runnerWithHistory.getLastHistory() != null) {
                runnerTime2.append(this.getTimeInCorrectFormat(runnerWithHistory.getRunnerHistory().get(0).getTimeObstacle1()));
            } else {
                runnerTime2.append("N/A");
            }
            runnerTime2.append("\n");
        });
        if (runnerNames.length() > 1) runnerTime2.setLength(runnerTime2.length() - 1);
        holder.runnerActivity2.setText(runnerTime2);


        StringBuilder runnerTime3 = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            if (runnerWithHistory.getLastHistory() != null) {
                runnerTime3.append(this.getTimeInCorrectFormat(runnerWithHistory.getRunnerHistory().get(0).getTimePitStop()));
            } else {
                runnerTime3.append("N/A");
            }
            runnerTime3.append("\n");
        });
        if (runnerNames.length() > 1) runnerTime3.setLength(runnerTime3.length() - 1);
        holder.runnerActivity3.setText(runnerTime3);


        StringBuilder runnerTime4 = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            if (runnerWithHistory.getLastHistory() != null) {
                runnerTime4.append(this.getTimeInCorrectFormat(runnerWithHistory.getRunnerHistory().get(0).getTimeSprint2()));
            } else {
                runnerTime4.append("N/A");
            }
            runnerTime4.append("\n");
        });
        if (runnerNames.length() > 1) runnerTime4.setLength(runnerTime4.length() - 1);
        holder.runnerActivity4.setText(runnerTime4);


        StringBuilder runnerTime5 = new StringBuilder();
        this.teamWithRunners.get(position).getRunnersWithHistory().forEach(runnerWithHistory -> {
            if (runnerWithHistory.getLastHistory() != null) {
                runnerTime5.append(this.getTimeInCorrectFormat(runnerWithHistory.getRunnerHistory().get(0).getTimeObstacle2()));
            } else {
                runnerTime5.append("N/A");
            }
            runnerTime5.append("\n");
        });
        if (runnerNames.length() > 1) runnerTime5.setLength(runnerTime5.length() - 1);
        holder.runnerActivity5.setText(runnerTime5);

    }

    public void setTeamWithRunners(List<TeamWithRunnersWithHistory> teamWithRunners) {
        this.teamWithRunners = teamWithRunners;
    }

    private StringBuilder getTimeInCorrectFormat(long timeInMilliseconds) {
        if (timeInMilliseconds == 0) return new StringBuilder("N/A");
        Duration d = Duration.ofMillis(timeInMilliseconds);
        long minutesPart = d.toMinutes();
        long secondsPart = d.minusMinutes( minutesPart ).getSeconds() ;
        long millisecondsPart = d.minusSeconds(secondsPart).minusMinutes(minutesPart).toMillis();

        StringBuilder finalString = new StringBuilder();

        if(minutesPart > 0) finalString.append(minutesPart).append("′");
        finalString.append(secondsPart).append("″");
        if(millisecondsPart > 0) finalString.append(millisecondsPart);

        return finalString;
    }

    @Override
    public int getItemCount() {
        return this.teamWithRunners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        TextView runnerName;
        TextView runnerActivity1;
        TextView runnerActivity2;
        TextView runnerActivity3;
        TextView runnerActivity4;
        TextView runnerActivity5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.statisticsRecyclerViewRowTeamName);
            runnerName = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerName);
            runnerActivity1 = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerActivity1);
            runnerActivity2 = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerActivity2);
            runnerActivity3 = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerActivity3);
            runnerActivity4 = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerActivity4);
            runnerActivity5 = itemView.findViewById(R.id.statisticsRecyclerViewRowRunnerActivity5);
        }
    }
}
