package com.example.f1_levier;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;
import java.util.ArrayList;

import static com.example.f1_levier.MainActivity.participants;

public class TeamActivity extends AppCompatActivity implements TeamDialog.TeamDialogListener{

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
        Participant temp;
        switch(place) {
            case "1,3,2":
                temp =  new Participant(teams.get(item_selected).getParticipants().get(1));//sauv 2
                teams.get(item_selected).setParticipant(1,teams.get(item_selected).getParticipants().get(2));
                teams.get(item_selected).setParticipant(2,temp);
                break;
            case "2,1,3":
                temp = new Participant(teams.get(item_selected).getParticipants().get(0));//sauv 1
                teams.get(item_selected).setParticipant(0,teams.get(item_selected).getParticipants().get(1));
                teams.get(item_selected).setParticipant(1,temp);
                break;
            case "3,1,2":
                temp = new Participant(teams.get(item_selected).getParticipants().get(0));//sauv 1
                teams.get(item_selected).setParticipant(0,teams.get(item_selected).getParticipants().get(1));
                teams.get(item_selected).setParticipant(1,teams.get(item_selected).getParticipants().get(2));
                teams.get(item_selected).setParticipant(2,temp);
                break;
            case "3,2,1":
                temp = new Participant(teams.get(item_selected).getParticipants().get(0)); //sauv 1
                teams.get(item_selected).setParticipant(0,teams.get(item_selected).getParticipants().get(2));
                teams.get(item_selected).setParticipant(2,temp);
                break;
            case "2,3,1":
                temp = new Participant(teams.get(item_selected).getParticipants().get(0)); //sauv 1
                teams.get(item_selected).setParticipant(0,teams.get(item_selected).getParticipants().get(2));
                teams.get(item_selected).setParticipant(2,teams.get(item_selected).getParticipants().get(1));
                teams.get(item_selected).setParticipant(1,temp);
                break;
        }

    }

    /**
     * Creates 10 teams with a balanced level
     * @return ArrayList<Participant> the list of teams
     */
    private ArrayList<Team> teamCreation()
    {
        ArrayList<Team> result = new ArrayList<Team>();
        ArrayList<Participant> sortedList = new ArrayList<Participant>();

        // Sort the participants by level in sortedList
        for(Participant p : participants)
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

        ArrayList<ArrayList<Participant>> teamList = new ArrayList<ArrayList<Participant>>();
        //Put the best runner and the worst one together
        for(int i = 0; i < 10; i++)
        {
            ArrayList<Participant> participantList = new ArrayList<Participant>();
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
                ArrayList<Participant> team = teamList.get(j);
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

        // Creates the final result
        int id = 1;
        for(ArrayList<Participant> team : teamList)
        {
            result.add(new Team(id, team.get(0), team.get(1), team.get(2)));
            id++;
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

