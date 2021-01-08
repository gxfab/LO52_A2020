package com.example.lo52_f1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.VISIBLE;

public class RunnerAddActivity extends AppCompatActivity {

    private ArrayList<Runner> runnerList = new ArrayList<>();
    private Spinner mSpinner;
    private String[] mLevelOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private ListView lv;
    private List<String> ls = new ArrayList<String>();
    private int selectedPosition = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_add);

        mSpinner = findViewById(R.id.spinner_runneradd);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mLevelOptions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //setting the ArrayAdapter data on the Spinner
        mSpinner.setAdapter(aa);

        lv = (ListView) findViewById(R.id.listview_runneradd);
    }

    /**
     * Called when the user taps the Add button
     */
    public void addRunner(View view) {
        TextView tvo = (TextView) findViewById(R.id.labeloutof30_runneradd);
        EditText etfn = (EditText) findViewById(R.id.editfirstname_runneradd);
        EditText etln = (EditText) findViewById(R.id.editlastname_runneradd);
        Spinner sl = (Spinner) findViewById(R.id.spinner_runneradd);
        String mFirstName = etfn.getText().toString();
        String mLastName = etln.getText().toString();
        int mLevel = Integer.parseInt(sl.getSelectedItem().toString());

        if (mFirstName.matches("") || mLastName.matches("")) {
            //erreur si nom ou prénom non saisi
            Toast.makeText(getApplicationContext(), "Incorrect runner name", Toast.LENGTH_SHORT).show();
        } else {
            Runner r = new Runner(mFirstName, mLastName, mLevel);
            runnerList.add(r);
            ls.add(Integer.toString(mLevel) + " - " + mFirstName + " " + mLastName);
            tvo.setText(Integer.toString(ls.size()));

            ArrayAdapter<String> aa = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    ls );

            lv.setAdapter(aa);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedFromList = (String) (lv.getItemAtPosition(position));
                    TextView tvs = (TextView) findViewById(R.id.selectedrunner_runneradd);
                    tvs.setText(selectedFromList);
                    selectedPosition = position;
                }});

            etfn.setText("");
            etln.setText("");

            Button b = (Button) findViewById(R.id.buttonok_runneradd);

            if(ls.size()%3 == 0 && ls.size()>=6)
                b.setVisibility(View.VISIBLE);
            else
                b.setVisibility(View.INVISIBLE);

            if (ls.size() == 30) {
                //On autorise le passage à la "suite" de l'application lorsque les 30 runners ont été saisies
                b = (Button) findViewById(R.id.buttonok_runneradd);
                b.setVisibility(View.VISIBLE);
                b = (Button) findViewById(R.id.buttonadd_runneradd);
                b.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void ok(View view){
        //Tri croissant des runners par level
        Collections.sort(runnerList, new Comparator<Runner>() {
            public int compare(Runner r1, Runner r2) {
                return ((Integer) r1.level).compareTo(r2.level);
            }
        });
        //Formation des équipes suivant le principe du serpentin
        ArrayList<Team> teamList = new ArrayList<>();

        for(int i=0 ; i<ls.size()/3 ; i++){
            teamList.add(new Team((char) (i+65),runnerList.get(i),runnerList.get(ls.size()/3*2-1-i),runnerList.get(ls.size()/3*2+i)));
        }

        Intent intent = new Intent(this, TeamsView.class);
        intent.putExtra("teamList", teamList);
        startActivity(intent);
    }

    public void removeRunner (View view) {
        if(selectedPosition >= ls.size()){
            //si aucun runner n'a été sélectionné pour être supprimé
            Toast.makeText(getApplicationContext(), "No runner has been selected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //suppression totale du runner
            runnerList.remove(selectedPosition);
            ls.remove(selectedPosition);
            TextView tv = (TextView) findViewById(R.id.selectedrunner_runneradd);
            tv.setText("");
            tv = (TextView) findViewById(R.id.labeloutof30_runneradd);
            tv.setText(Integer.toString(ls.size()));
            ArrayAdapter<String> aa = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    ls );
            lv.setAdapter(aa);
            selectedPosition = 99; // on s'assure qu'il n'y ait pas de suppression non voulue par la suite

            Button b = (Button) findViewById(R.id.buttonok_runneradd);
            if(ls.size()%3 == 0)
                b.setVisibility(View.VISIBLE);
            else
                b.setVisibility(View.INVISIBLE);
            b = (Button) findViewById(R.id.buttonadd_runneradd);
            b.setVisibility(View.VISIBLE);
        }
    }

}