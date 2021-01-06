package fr.utbm.lo52.f1levier.ui.runningrace;


import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamNameAndMemberIds;
import fr.utbm.lo52.f1levier.db.entity.MeasuredTime;

public class RunningTeamListAdapter extends RecyclerView.Adapter<RunningTeamListAdapter.ViewHolder>  {

    private List<TeamNameAndMemberIds> mTeams = Collections.emptyList();

    private InteractionListener mListener;

    private int finishedTeams = 0;

    RunningTeamListAdapter(InteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_running_team_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TeamNameAndMemberIds currentTeam = mTeams.get(position);
        holder.nameTextView.setText(currentTeam.name);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onRunningTeamItemClicked(currentTeam);

                if (currentTeam.currentRunner == currentTeam.memberIds.size()){
                    holder.cardView.setCardBackgroundColor(ResourcesCompat.getColor(
                            holder.mView.getResources(), R.color.colorAccent, null));
                    holder.cardView.setForeground(null);
                    finishedTeams += 1;
                    currentTeam.currentRunner +=1;
                }
            }
        });
    }

    void setTeams(List<TeamNameAndMemberIds> teams) {
        mTeams = teams;
        for (TeamNameAndMemberIds team: teams) {
            team.lapsQueue = new ArrayBlockingQueue(MeasuredTime.queue.size());
            team.lapsQueue.addAll(MeasuredTime.queue);
            team.currentLapType = (int)team.lapsQueue.poll();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final CardView cardView;
        private final View mView;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            nameTextView = view.findViewById(R.id.text_view_running_team_name);
            cardView = view.findViewById(R.id.card_view_running_team_container);
        }
    }

    public boolean haveAllTeamsFinished() {
        return finishedTeams == mTeams.size();
    }

    public interface InteractionListener {
        void onRunningTeamItemClicked(TeamNameAndMemberIds team);
    }
}
