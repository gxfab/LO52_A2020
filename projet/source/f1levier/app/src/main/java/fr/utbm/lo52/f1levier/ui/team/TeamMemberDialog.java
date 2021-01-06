package fr.utbm.lo52.f1levier.ui.team;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;
import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamMember;
import fr.utbm.lo52.f1levier.db.entity.TeamMemberJoin;
import fr.utbm.lo52.f1levier.viewmodel.TeamViewModel;

public class TeamMemberDialog extends AppCompatDialogFragment
        implements TeamMemberListAdapter.InteractionListener {

    private static final String ARG_RACE_ID = "race-id";
    private static final String ARG_TEAM_ID = "team-id";
    private TeamViewModel teamViewModel;
    private int raceId = -1;
    private int teamId = -1;

    public TeamMemberDialog() {
    }

    public static TeamMemberDialog newInstance(int raceId, int teamId) {
        TeamMemberDialog fragment = new TeamMemberDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_RACE_ID, raceId);
        args.putInt(ARG_TEAM_ID, teamId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            raceId = getArguments().getInt(ARG_RACE_ID);
            teamId = getArguments().getInt(ARG_TEAM_ID);
        }

        if (raceId == -1 || teamId == -1) {
            dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_team_member, null);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.member_list);
        final TeamMemberListAdapter adapter = new TeamMemberListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        teamViewModel.getOtherMembersNotPickedByRaceId(teamId, raceId).observe(this,
                new Observer<List<TeamMember>>() {
                    @Override
                    public void onChanged(@Nullable List<TeamMember> members) {
                        adapter.setMembers(members);
                    }
                });

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        builder.setView(view)
                .setTitle(R.string.title_team_members)
                .setNegativeButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onMemberItemClicked(TeamMember member) {
        if (member.picked) {
            teamViewModel.deleteTeamMemberAsync(teamId, member.id);
        }
        else {
            TeamMemberJoin teamMemberJoin = new TeamMemberJoin(teamId, member.id);
            teamViewModel.insertTeamMemberAsync(teamMemberJoin);
        }
    }
}
