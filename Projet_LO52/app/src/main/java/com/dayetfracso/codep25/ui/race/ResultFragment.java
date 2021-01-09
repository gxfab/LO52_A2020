package com.dayetfracso.codep25.ui.race;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dayetfracso.codep25.R;
import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.entity.RaceWithTeams;
import com.dayetfracso.codep25.entity.Team;
import com.dayetfracso.codep25.utils.TeamTimeComparator;
import com.dayetfracso.codep25.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ResultFragment extends Fragment {
    private AppDatabase database;
    private ArrayList<Button> listTriggerButtons;
    private RaceWithTeams raceWithTeams;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_result, container, false);

        // Initializing DB
        database = AppDatabase.getDatabase(getContext());

        raceWithTeams = database.raceDao().getLastRaceWithTeams();

        List<Team> teamsList = raceWithTeams.teams;
        LinearLayout linearLayoutContainer = root.findViewById(R.id.myLayout);
        Collections.sort(teamsList, new TeamTimeComparator(getContext(), raceWithTeams.race.getRaceId()));
        LinearLayout linearLayoutRowl = new LinearLayout(getContext());
        linearLayoutRowl.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutRowl.setGravity(Gravity.CENTER);
        TextView placel = new TextView(getContext());
        placel.setGravity(Gravity.CENTER);
        placel.setWidth(Utils.mapPXtoDP(getContext(),90));
        placel.append("Classement");
        linearLayoutRowl.addView(placel);
        TextView teaml = new TextView(getContext());
        teaml.setGravity(Gravity.CENTER);
        teaml.setWidth(Utils.mapPXtoDP(getContext(),150));
        teaml.append("Equipe");
        linearLayoutRowl.addView(teaml);
        TextView timel = new TextView(getContext());
        timel.append("Chrono");
        timel.setGravity(Gravity.CENTER);
        timel.setWidth(Utils.mapPXtoDP(getContext(),90));
        linearLayoutRowl.addView(timel);
        linearLayoutContainer.addView(linearLayoutRowl);

        Integer i = 1;
        for(final Team team : teamsList) {
            // Create a ROW linearLayout (Horizontal)
            LinearLayout linearLayoutRow = new LinearLayout(getContext());
            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutRow.setGravity(Gravity.CENTER);
            // Add it to the linearLayoutContainer

            TextView place = new TextView(getContext());
            place.setGravity(Gravity.CENTER);
            place.setWidth(Utils.mapPXtoDP(getContext(),90));
            place.append("" + i);
            linearLayoutRow.addView(place);
            Button triggerButton = new Button(getContext());
            triggerButton.setText("Team " + team.getTeamId());
            triggerButton.setWidth(Utils.mapPXtoDP(getContext(),150));
            triggerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Create new fragment and transaction
                    Fragment teamResultFragment = new TeamResultFragment();
                    // consider using Java coding conventions (upper first char class names!!!)
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putLong("raceId", raceWithTeams.race.getRaceId());
                    bundle.putLong("teamId", team.getTeamId());
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.nav_host_fragment, teamResultFragment);
                    transaction.addToBackStack("race results");
                    teamResultFragment.setArguments(bundle);
                    // Commit the transaction
                    transaction.commit();
                }
            });
            linearLayoutRow.addView(triggerButton);
            TextView time = new TextView(getContext());
            time.append("" + Utils.formatTime(team.getGlobalTimeOnRace(getContext(), raceWithTeams.race.getRaceId())));
            time.setGravity(Gravity.CENTER);
            time.setWidth(Utils.mapPXtoDP(getContext(),90));
            linearLayoutRow.addView(time);
            linearLayoutContainer.addView(linearLayoutRow);
            i++;
        }
        return root;
    }
}