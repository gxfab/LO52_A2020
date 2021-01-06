package fr.utbm.lo52.f1levier.ui.team;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamDetail;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {

    private List<TeamDetail> mTeams = Collections.emptyList();

    private InteractionListener mListener;

    TeamListAdapter(InteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TeamDetail currentTeam = mTeams.get(position);
        holder.nameTextView.setText(currentTeam.name);
        holder.membersCountTextView.setText(
                holder.mView.getContext().getResources().getString(
                        R.string.team_members_count_and_echelon, currentTeam.membersCount,
                        currentTeam.echelonsSum));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onTeamItemClicked(currentTeam);
            }
        });
    }

    void setTeams(List<TeamDetail> teams) {
        mTeams = teams;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView nameTextView;
        private final TextView membersCountTextView;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            nameTextView = view.findViewById(R.id.text_view_team_name);
            membersCountTextView = view.findViewById(R.id.text_view_team_member_count);
        }
    }

    public interface InteractionListener {
        void onTeamItemClicked(TeamDetail team);
    }
}
