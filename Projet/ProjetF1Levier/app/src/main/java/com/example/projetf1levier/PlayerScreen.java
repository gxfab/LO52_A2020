package com.example.projetf1levier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayerScreen extends AppCompatActivity {

    int m_itemSelected;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private teamList teams = new teamList(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_screen);

        ListView listView = (ListView) findViewById(R.id.playerlistView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int position,
                                    long id) {

                m_itemSelected = position;

                ImageButton deleteIcon = (ImageButton) findViewById(R.id.deleteIcon);
                deleteIcon.setVisibility(view.VISIBLE);
                deleteIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageButton deleteIcon = (ImageButton) findViewById(R.id.deleteIcon);
                        deleteIcon.setVisibility(v.INVISIBLE);
                        deletePlayer();
                    }

                });
            }
        });

    }

    public void deletePlayer() {
        teams.removeplayer(m_itemSelected);
        listItems.remove(m_itemSelected);
        adapter.notifyDataSetChanged();

        TextView nbPlayer = (TextView) findViewById(R.id.nbPlayer);
        nbPlayer.setText(String.valueOf(teams.getNbPlayer()));
    }

    public void addClick(View view) {


        if (teams.getNbPlayer() == 30) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Erreur d'ajout");

            alertDialogBuilder
                    .setMessage("Il existe deja 30 players")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();

            //show it
            alertDialog.show();
        } else {


            EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
            String name = editText.getText().toString();

            editText = (EditText) findViewById(R.id.editTextTextPersonName2);
            String firstName = editText.getText().toString();

            if (name.trim().length() == 0 || firstName.trim().length() == 0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Erreur d'ajout");

                alertDialogBuilder
                        .setMessage("Attention, veuillez ajouter un nom et un prénom au joueur")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                TextView playerText = (TextView) findViewById(R.id.player_text);
                if (teams.getNbPlayer() > 1)
                    playerText.setText("players");
                AlertDialog alertDialog = alertDialogBuilder.create();

                //show it
                alertDialog.show();
            } else {
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                int level = Integer.parseInt(spinner.getSelectedItem().toString());

                teams.addPlayer(name, firstName, level);

                TextView nbPlayer = (TextView) findViewById(R.id.nbPlayer);
                nbPlayer.setText(String.valueOf(teams.getNbPlayer()));

                TextView playerText = (TextView) findViewById(R.id.player_text);
                if (teams.getNbPlayer() > 1)
                    playerText.setText("players");

                listItems.add(String.format("%s %s   lvl :%d", name, firstName, level));
                adapter.notifyDataSetChanged();

                ListView mListView = (ListView) findViewById(R.id.playerlistView);
            }
        }
    }

    public void endAddPlayer(View view) {

        if (teams.getNbPlayer() % 3 != 0 || teams.getNbPlayer() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Erreur sur l'ajout d'un joueur");
            alertDialogBuilder
                    .setMessage("Le nombre de joueur doit etre un multiple de 3")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            //show it
            alertDialog.show();
        } else {
            //toRemove

            teams.makeTeam();
            Intent intent = new Intent(this, teamView.class);
            intent.putExtra("teamList", teams);
            startActivity(intent);
        }
    }
}