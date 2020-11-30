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
        teams = new ArrayList<Team>();
        int id = 1;
        for (int i = 0; i < participants.size(); i = i + 3) {
            teams.add(new Team(id, participants.get(i), participants.get(i + 1), participants.get(i + 2)));
            id++;
        }
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
}

