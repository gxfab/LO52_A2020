package fr.utbm.lo52.f1levier.ui.stats;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.ParticipantStat;
import fr.utbm.lo52.f1levier.utils.Chronometer;

public class GlobalParticipantStatsAdapter extends RecyclerView.Adapter<GlobalParticipantStatsAdapter.ViewHolder>  {

    private List<ParticipantStat> mParticipantStats = Collections.emptyList();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stats_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ParticipantStat currentStat = mParticipantStats.get(position);
        holder.rankTextView.setText(String.valueOf(position+1));
        holder.nameTextView.setText(currentStat.name + " : " + currentStat.team);
        holder.timeTextView.setText(Chronometer.format(currentStat.time));
    }

    void setParticipantStats(List<ParticipantStat> teamStats) {
        mParticipantStats = teamStats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mParticipantStats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView rankTextView;
        private final TextView nameTextView;
        private final TextView timeTextView;

        private ViewHolder(View view) {
            super(view);
            rankTextView = view.findViewById(R.id.text_view_rank);
            nameTextView = view.findViewById(R.id.text_view_name);
            timeTextView = view.findViewById(R.id.text_view_time);
        }
    }
}
