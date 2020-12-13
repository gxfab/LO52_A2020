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

import java.util.ArrayList;

import static com.example.f1_levier.MainActivity.participants;

public class TeamActivity extends AppCompatActivity{

    ArrayList<Team> teams;
    static String[] selected;
    static int id_selected;
    Spinner s_p1;
    Spinner s_p2;
    Spinner s_p3;

    //Constructeur
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        /*Team creation*/
        teams = teamCreation();

        /*list view*/
        final ListView listView = (ListView) findViewById(R.id.listView_team);
        // Create the adapter to convert the array to views
        final TeamAdapter adapter = new TeamAdapter(this, teams);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id_selected = i;
                AlertDialog.Builder d_builder = new AlertDialog.Builder(TeamActivity.this);
                View d_order = getLayoutInflater().inflate(R.layout.dialog_order,null);

                TextView name_p1 = (TextView) d_order.findViewById(R.id.textView_name_p1);
                TextView fname_p1 = (TextView) d_order.findViewById(R.id.textView_fname_p1);
                TextView name_p2 = (TextView) d_order.findViewById(R.id.textView_name_p2);
                TextView fname_p2 = (TextView) d_order.findViewById(R.id.textView_fname_p2);
                TextView name_p3 = (TextView) d_order.findViewById(R.id.textView_name_p3);
                TextView fname_p3 = (TextView) d_order.findViewById(R.id.textView_fname_p3);

                s_p1 = (Spinner) d_order.findViewById(R.id.spinner_p1);
                s_p2 = (Spinner) d_order.findViewById(R.id.spinner_p2);
                s_p3 = (Spinner) d_order.findViewById(R.id.spinner_p3);

                name_p1.setText(teams.get(i).getParticipants().get(0).getName());
                name_p2.setText(teams.get(i).getParticipants().get(1).getName());
                name_p3.setText(teams.get(i).getParticipants().get(2).getName());

                fname_p1.setText(teams.get(i).getParticipants().get(0).getFirstName());
                fname_p2.setText(teams.get(i).getParticipants().get(1).getFirstName());
                fname_p3.setText(teams.get(i).getParticipants().get(2).getFirstName());

                final String[] table_ordre = new String[]{"1", "2", "3"};
                selected = new String[3];
                ArrayAdapter<String> ad_sp1 = new ArrayAdapter<String>(TeamActivity.this,
                        android.R.layout.simple_spinner_item, table_ordre);
                ad_sp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s_p1.setAdapter(ad_sp1);

                s_p1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected[0] = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {
                        // can leave this empty
                    }
                });

                d_builder.setPositiveButton("Validez", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if((Integer.parseInt(s_p1.getSelectedItem().toString())+
                                Integer.parseInt(s_p2.getSelectedItem().toString())+
                                Integer.parseInt(s_p3.getSelectedItem().toString()) != 5)&&(
                                (Integer.parseInt(s_p1.getSelectedItem().toString())) == 3 ||
                                (Integer.parseInt(s_p2.getSelectedItem().toString())) == 3 ||
                                (Integer.parseInt(s_p3.getSelectedItem().toString())) == 3)){

                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                d_builder.setView(d_order);
                AlertDialog dialog = d_builder.create();
                dialog.show();
            }
        });

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

