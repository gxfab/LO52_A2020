package com.example.f1_levier.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.R;
import com.example.f1_levier.BDD.entity.Team;
import com.example.f1_levier.adapter.TeamAdapter;

import java.util.ArrayList;

import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

public class TeamActivity extends AppCompatActivity implements TeamDialog.TeamDialogListener {

    static ArrayList<Team> teams;
    static TeamAdapter adapter;
    static int item_selected;

    //Constructeur
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        /*Team creation*/
        teams = teamCreation();

        /*list view*/
        final ListView listView = (ListView) findViewById(R.id.listView_team);
        // Create the adapter to convert the array to views
        adapter = new TeamAdapter(this, teams);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                item_selected = i;
                openDialog();
            }
        });
    }
    public void openDialog() {
        TeamDialog TeamDialog = new TeamDialog();
        TeamDialog.show(getSupportFragmentManager(), "Passage");
    }
    @Override
    public void applyTexts(String place) {
        permutation(place);
        adapter.notifyDataSetChanged();
    }
    
    void permutation(String place){
        int temp;
        switch(place) {
            case "1,3,2":
                temp =  teams.get(item_selected).getSecondRunnerId();//sauv 2
                teams.get(item_selected).setSecondRunnerId(teams.get(item_selected).getThirdRunnerId());
                teams.get(item_selected).setThirdRunnerId(temp);
                break;
            case "2,1,3":
                temp = teams.get(item_selected).getFirstRunnerId();//sauv 1
                teams.get(item_selected).setFirstRunnerId(teams.get(item_selected).getSecondRunnerId());
                teams.get(item_selected).setSecondRunnerId(temp);
                break;
            case "3,1,2":
                temp = teams.get(item_selected).getFirstRunnerId();//sauv 1
                teams.get(item_selected).setFirstRunnerId(teams.get(item_selected).getThirdRunnerId());
                teams.get(item_selected).setThirdRunnerId(teams.get(item_selected).getSecondRunnerId());
                teams.get(item_selected).setSecondRunnerId(temp);
                break;
            case "3,2,1":
                temp = teams.get(item_selected).getFirstRunnerId(); //sauv 1
                teams.get(item_selected).setFirstRunnerId(teams.get(item_selected).getThirdRunnerId());
                teams.get(item_selected).setThirdRunnerId(temp);
                break;
            case "2,3,1":
                temp = teams.get(item_selected).getFirstRunnerId(); //sauv 1
                teams.get(item_selected).setFirstRunnerId(teams.get(item_selected).getSecondRunnerId());
                teams.get(item_selected).setSecondRunnerId(teams.get(item_selected).getThirdRunnerId());
                teams.get(item_selected).setThirdRunnerId(temp);
                break;
        }
        db.teamDAO().updateTeam(teams.get(item_selected));
    }

    /**
     * Creates 10 teams with a balanced level
     * @return ArrayList<Participant> the list of teams
     */
    private ArrayList<Team> teamCreation()
    {
        ArrayList<Team> result = new ArrayList<Team>();
        ArrayList<Runner> sortedList = new ArrayList<Runner>();

        // Sort the participants by level in sortedList
        for(Runner p : runnerList)
        {
            if (sortedList.isEmpty())
                sortedList.add(p);
            else {
                int i = 0;
                while (sortedList.size() > i && sortedList.get(i).getLevel() < p.getLevel())
                    i++;
                sortedList.add(i, p);
            }
        }

        ArrayList<ArrayList<Runner>> teamList = new ArrayList<ArrayList<Runner>>();
        //Put the best runner and the worst one together
        for(int i = 0; i < 10; i++)
        {
            ArrayList<Runner> participantList = new ArrayList<Runner>();
            participantList.add(sortedList.get(i));
            participantList.add(sortedList.get(29 - i));
            teamList.add(participantList);
        }

        // Adds the other runners to the teams, putting the best one with the team with the lowest level, the worst one with the team with the
        // highest level
        for(int i = 19; i > 9; i--)
        {
            // Find the worst team in the list
            int minLevel = 1000000;
            int worstTeamForTheMoment = -1;
            for (int j = 0; j < 10; j++)
            {
                ArrayList<Runner> team = teamList.get(j);
                int level = team.get(0).getLevel() + team.get(1).getLevel();
                if (team.size() == 2 && level < minLevel)
                {
                    minLevel = level;
                    worstTeamForTheMoment = j;
                }
            }

            // Adds the worst player remaining in with this team
            teamList.get(worstTeamForTheMoment).add(sortedList.get(i));
        }

        int id = 0;
        // Creates the final result
        for(ArrayList<Runner> team : teamList)
        {
            int level = team.get(0).getLevel() + team.get(1).getLevel() + team.get(2).getLevel();
            result.add(new Team(id, level, team.get(0).getRunnerId(), team.get(1).getRunnerId(), team.get(2).getRunnerId()));
            id++;
        }

        for(Team t : result)
        {
            db.teamDAO().insertTeam(t);
        }
        return result;
    }


    /**
     * Indicates if the teams level are balanced with maximum + or - 15 in team global level
     * @param teams ArrayList<team> : the list of teams
     * @return boolean : true if the teams are balanced, false if not
     */
    private boolean areTeamBalanced(ArrayList<Team> teams)
    {
        int maxLevel = -1;
        int minLevel = 1000000;

        for(Team t : teams)
        {
            if(t.getLevel() > maxLevel)
                maxLevel = t.getLevel();
            else if(t.getLevel() < minLevel)
                minLevel = t.getLevel();
        }

        if(maxLevel - minLevel > 15)
            return false;
        return true;
    }

}

