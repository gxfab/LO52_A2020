package com.example.lo52_f1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamsView extends AppCompatActivity {
    ArrayList<Team> teamList;
    ArrayList<String> teamNameList = new ArrayList<String>();
    String[] orderOptions = {"1 - 3 - 2", "2 - 1 - 3", "2 - 3 - 1", "3 - 1 - 2", "3 - 2 - 1"};
    Spinner teamSpinner, orderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_view);

        teamList = (ArrayList<Team>) getIntent().getSerializableExtra("teamList");
        display(teamList);

        for(int i = 0; i<teamList.size(); i++)
            teamNameList.add(Character.toString(teamList.get(i).getName()));

        teamSpinner = (Spinner) findViewById(R.id.teamspinner);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, teamNameList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(aa);

        orderSpinner = (Spinner) findViewById(R.id.orderspinner);
        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, orderOptions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(aa);

    }

    public void okteamsview(View view){
        Intent intent = new Intent(this, RaceActivity.class);
        intent.putExtra("tL", teamList);
        startActivity(intent);
    }

    public void orderChangeOK(View view){
        teamList.get(teamSpinner.getSelectedItemPosition()).orderChange(orderSpinner.getSelectedItemPosition());
        display(teamList);
    }

    public void display(ArrayList<Team> teamList) {
        String s;
        int id;
        TextView tv;

        for (int i = 0 ; i < teamList.size() ; i++) {
            for (int j = 1 ; j < 4 ; j++){
                s = "runner" + ((char) ((char) i + 65)) + j;
                id = getResources().getIdentifier(s, "id", getPackageName());
                tv = (TextView) findViewById(id);
                tv.setText(teamList.get(i).getRunner().get(j-1).getFirstName() + " " + teamList.get(i).getRunner().get(j-1).getLastName());
                tv.setVisibility(View.VISIBLE);

                s = "level" + ((char) ((char) i + 65)) + j;
                id = getResources().getIdentifier(s, "id", getPackageName());
                tv = (TextView) findViewById(id);
                tv.setText(Integer.toString(teamList.get(i).getRunner().get(j-1).getLevel()));
                tv.setVisibility(View.VISIBLE);

            }
            s = "label" + ((char) ((char) i + 65));
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setVisibility(View.VISIBLE);

            s = "teamLevel" + ((char) ((char) i + 65));
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setText(Integer.toString(teamList.get(i).getRunner().get(0).getLevel() + teamList.get(i).getRunner().get(1).getLevel() + teamList.get(i).getRunner().get(2).getLevel()));
            tv.setVisibility(View.VISIBLE);
        }
    }
}