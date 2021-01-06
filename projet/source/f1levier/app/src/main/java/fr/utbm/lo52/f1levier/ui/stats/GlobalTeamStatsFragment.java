package fr.utbm.lo52.f1levier.ui.stats;



import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.dao.QueryResult.TeamStat;
import fr.utbm.lo52.f1levier.viewmodel.StatsViewModel;

public class GlobalTeamStatsFragment extends Fragment implements StatsUtils.TeamStatsGetHandler {

    private static final String ARG_RACE_ID = "race-id";

    private int raceId = -1;

    private StatsViewModel statsViewModel;

    private GlobalTeamStatsAdapter adapter;

    public GlobalTeamStatsFragment() {
    }

    public static GlobalTeamStatsFragment newInstance(int raceId) {
        GlobalTeamStatsFragment fragment = new GlobalTeamStatsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RACE_ID, raceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            raceId = getArguments().getInt(ARG_RACE_ID);
        }
        statsViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_team_stats, container, false);

        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.global_team_stats);
        adapter = new GlobalTeamStatsAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        new StatsUtils.getTeamStatsAsyncTask(statsViewModel, this).execute(raceId);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    @Override
    public void onTeamStatsGetPostExecute(List<TeamStat> teamStats) {
        adapter.setTeamStats(teamStats);
    }
}
