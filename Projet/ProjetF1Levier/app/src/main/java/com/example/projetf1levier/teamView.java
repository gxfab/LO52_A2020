package com.example.projetf1levier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;

public class teamView extends AppCompatActivity {

    //private teamList teams;
    teamList teams;
    TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);

        Intent intent = getIntent();

        teams = (teamList) intent.getSerializableExtra("teamList");

        GridView gridView = (GridView) findViewById(R.id.teamGridView);

        teamAdapter = new TeamAdapter(this, teams);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                teamOrder(parent, v, position, id);
            }
        });

        gridView.setAdapter(teamAdapter);
    }


    public void runClick(View view) {

        Intent intent = new Intent(this, Run.class);

        intent.putExtra("teamList", teams);

        startActivity(intent);
    }

    public void teamOrder(AdapterView<?> parent, View v, final int position, long id) {

        final int[] order = {1, 2, 3};

        team currentTeam = teams.getListOfTeam().get(position);


        String[] players = {currentTeam.getPlayerList().get(0).getFullName()
                , currentTeam.getPlayerList().get(1).getFullName()
                , currentTeam.getPlayerList().get(2).getFullName()};


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);


        alertDialogBuilder.setTitle("Choix du premier joueur")
                .setItems(players, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        teamOrder2(position, which);
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();

        //show it
        alertDialog.show();


    }

    public void teamOrder2(final int team, final int first) {
        String[] players2 = new String[2];

        final team currentTeam = teams.getListOfTeam().get(team);

        for (int p = 0, j = 0; p < 3; p++) {
            if (p != first) {
                players2[j++] = currentTeam.getPlayerList().get(p).getFullName();
            }
        }

        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);


        alertDialogBuilder2.setTitle("Choix du deuxieme joueur")
                .setItems(players2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        currentTeam.changeOrder(first, which);
                        teamAdapter.notifyDataSetChanged();

                    }
                });


        AlertDialog alertDialog2 = alertDialogBuilder2.create();

        //show it
        alertDialog2.show();
    }


}
