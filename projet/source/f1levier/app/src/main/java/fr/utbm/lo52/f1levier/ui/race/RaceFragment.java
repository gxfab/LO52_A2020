package fr.utbm.lo52.f1levier.ui.race;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.entity.Race;
import fr.utbm.lo52.f1levier.ui.stats.StatsActivity;
import fr.utbm.lo52.f1levier.ui.team.TeamActivity;
import fr.utbm.lo52.f1levier.viewmodel.RaceViewModel;

public class RaceFragment extends Fragment implements RaceListAdapter.InteractionListener {

    private static final String NEW_RACE_DIALOG_CODE = "new_race";

    private RaceViewModel raceViewModel;

    public RaceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        raceViewModel = ViewModelProviders.of(this).get(RaceViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race_list, container, false);

        Context context = view.getContext();


        RecyclerView notStartedRecyclerView = view.findViewById(R.id.not_started_races_list);
        final RaceListAdapter notStartedAdapter = new RaceListAdapter(this);
        notStartedRecyclerView.setAdapter(notStartedAdapter);
        notStartedRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        raceViewModel.getNotStartedRaces().observe(this,
                new Observer<List<Race>>() {
                    @Override
                    public void onChanged(@Nullable final List<Race> races) {
                        notStartedAdapter.setRaces(races);
                    }
                });

        DividerItemDecoration notStartedItemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        notStartedRecyclerView.addItemDecoration(notStartedItemDecoration);


        RecyclerView finishedRecyclerView = view.findViewById(R.id.finished_races_list);
        final RaceListAdapter finishedAdapter = new RaceListAdapter(this);
        finishedRecyclerView.setAdapter(finishedAdapter);
        finishedRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        raceViewModel.getFinishedRaces().observe(this,
                new Observer<List<Race>>() {
                    @Override
                    public void onChanged(@Nullable final List<Race> races) {
                        finishedAdapter.setRaces(races);
                    }
                });

        DividerItemDecoration finishedItemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        finishedRecyclerView.addItemDecoration(finishedItemDecoration);


        FloatingActionButton addFab = view.findViewById(R.id.fab_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                NewRaceDialog newRaceDialog = new NewRaceDialog();
                newRaceDialog.show(Objects.requireNonNull(getFragmentManager()),
                        NEW_RACE_DIALOG_CODE);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRaceItemClicked(Race race) {
        Context context = Objects.requireNonNull(getContext());

        if (race.startedAt == null) {
            Intent teamIntent = new Intent(context, TeamActivity.class);
            teamIntent.putExtra(TeamActivity.EXTRA_RACE_ID, race.id);
            context.startActivity(teamIntent);
        }

        if (race.finishedAt != null) {
            Intent teamIntent = new Intent(context, StatsActivity.class);
            teamIntent.putExtra(StatsActivity.EXTRA_RACE_ID, race.id);
            context.startActivity(teamIntent);
        }
    }
}
