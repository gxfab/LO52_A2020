package fr.utbm.runf1.view.manage_teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Team;
import fr.utbm.runf1.entities.relations.TeamWithRunners;

/**
 * Created by Yosef B.I.
 */
public class ManageTeamsRecycleViewAdapter extends RecyclerView.Adapter<ManageTeamsRecycleViewAdapter.ViewHolder> {

    private List<TeamWithRunners> teamsWithRunnersList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;

    public ManageTeamsRecycleViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_manage_teams_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamWithRunners teamWithRunners = teamsWithRunnersList.get(position);
        StringBuilder team = new StringBuilder();

        for (int i = 0; i < teamWithRunners.getRunners().size(); i++) {
            team.append(" &#8226; " + teamWithRunners.getRunners().get(i).getFirstName());
            if(i != teamWithRunners.getRunners().size()-1) team.append("<br/>");
        }
        holder.myTextView.setText(HtmlCompat.fromHtml(team.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        holder.teamLevel.setText("Team level : " + teamWithRunners.getTeamLevel());
        holder.team = teamWithRunners.getTeam();
    }

    public void setTeamsWithRunnersList(List<TeamWithRunners> teamsWithRunnersList) {
        this.teamsWithRunnersList = teamsWithRunnersList;
    }

    @Override
    public int getItemCount() {
        return this.teamsWithRunnersList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        TextView teamLevel;
        Team team;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.manageTeamsRecyclerViewRowTextView);
            teamLevel = itemView.findViewById(R.id.manageTeamsRecyclerViewRowTextViewLevel);
            Button editButton = itemView.findViewById(R.id.manageTeamsEditButton);
            editButton.setOnClickListener(view -> itemClickListener.editTeam(view, team.getId()));
        }
    }

    public interface ItemClickListener {
        void editTeam(View view, int teamId);
    }
}
