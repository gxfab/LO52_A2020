package fr.utbm.lo52.f1levier.ui.team;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamMember;

public class TeamMemberListAdapter extends RecyclerView.Adapter<TeamMemberListAdapter.ViewHolder> {

    private List<TeamMember> mMembers = Collections.emptyList();

    private InteractionListener mListener;

    private int pickedMembersCount = 0;

    TeamMemberListAdapter(InteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_member_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final TeamMember currentMember = mMembers.get(position);
        holder.nameTextView.setText(currentMember.name);
        holder.echelonTextView.setText(String.valueOf(currentMember.echelon));
        holder.checkBox.setChecked(currentMember.picked);
        if (currentMember.picked) pickedMembersCount++;

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickedMembersCount < 3 || currentMember.picked) {
                    if (mListener != null) {
                        mListener.onMemberItemClicked(currentMember);
                        if (currentMember.picked) pickedMembersCount--;
                    }
                }
                else {
                    Toast alert = Toast.makeText(holder.mView.getContext(),
                            R.string.toat_team_member_limit_reached, Toast.LENGTH_SHORT);
                    alert.show();
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.callOnClick();
            }
        });
    }

    void setMembers(List<TeamMember> participants) {
        mMembers = participants;
        pickedMembersCount = 0;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private final CheckBox checkBox;
        private final TextView nameTextView;
        private final TextView echelonTextView;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            checkBox = view.findViewById(R.id.check_box_member);
            nameTextView = view.findViewById(R.id.text_view_member_name);
            echelonTextView = view.findViewById(R.id.text_view_member_echelon);
        }
    }

    public interface InteractionListener {
        void onMemberItemClicked(TeamMember member);
    }
}
