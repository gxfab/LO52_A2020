package com.example.lo52_f1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultsActivity extends AppCompatActivity {

    String raceName = " ";
    boolean lastRaceOnly = true;
    int noRace=0, nbTeams = 0;
    Spinner raceSpinner, recordSpinner, teamNameSpinner;
    ArrayList<String> raceList = new ArrayList<String>(), teamNameList = new ArrayList<String>();
    ArrayList<Team> teamList = new ArrayList<Team>();
    String[] typeOptions = {"Best sprint", "Best obstacle run", "Best pit-stop", "Best full lap"};
    ArrayAdapter aa, aa2, aa3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        raceSpinner = findViewById(R.id.racespinner);
        recordSpinner = findViewById(R.id.recordspinner);
        teamNameSpinner = findViewById(R.id.teamnamespinner);

        lastRaceOnly = (Boolean) getIntent().getSerializableExtra("lastRaceOnly");

        try{
            raceSpinnerInit();
        } catch (Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }

        if(lastRaceOnly){
            raceSpinner.setVisibility(View.INVISIBLE);
            raceSpinner.setSelection(0);
            noRace = raceList.size()-1;
        }

        //ArrayAdapter
        aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, raceList);
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting the ArrayAdapter data on the Spinner
        raceSpinner.setAdapter(aa3);
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                raceName = raceSpinner.getSelectedItem().toString();
                noRace = pos;
                try {
                    retrieve();
                    rankingDisplay();
                    teamNameList.clear();
                    for(int i = 0;i<teamList.size();i++)
                        teamNameList.add(Character.toString(teamList.get(i).getName()));
                    spinnerUpdate();
                } catch (Exception e) {
                    // if any I/O error occurs
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
        raceSpinner.setSelection(0);

        try {
            //raceSpinnerInit();
            retrieve();
            rankingDisplay();
            recordDisplay(0);
            overallDisplay(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinnerUpdate();
    }

    void raceSpinnerInit() throws FileNotFoundException {
        try {
            File file = new File(getFilesDir() + "/results.txt");

            if (!file.exists()){
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            try {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.equals("-")){
                        raceList.add(br.readLine());
                    }
                }
            } catch(Exception e) {
                // if any I/O error occurs
                e.printStackTrace();
            }

            br.close();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    void retrieve() throws FileNotFoundException {
        File file = new File(getFilesDir() + "/results.txt");
        FileReader fr = new FileReader(file.getAbsoluteFile());
        BufferedReader br = new BufferedReader(fr);

        try {
            char teamName;
            String line=" ", sep = "-", tmpfn, tmpln;
            int tmpl,nbl=0,tmppos;
            long tmpLong;

            teamList = new ArrayList<Team>();

            line = br.readLine();
            try {
                //if (nbl == noRace)
                line = br.readLine();
                while (nbl != noRace) {
                    if(line.equals(sep)){
                        nbl++;
                    }
                    line = br.readLine();
                }
            } catch(Exception e) {
                // if any I/O error occurs
                e.printStackTrace();
            }

            raceName = line;
            line = br.readLine();
            nbTeams = Integer.parseInt(line);

            ArrayList<Runner> ar = new ArrayList<Runner>();

            for(int i=0 ; i<nbTeams; i++){
                //writing of the name of the team
                teamName =  br.readLine().charAt(0);
                line = br.readLine();
                tmppos = Integer.parseInt(line);

                for(int j=0 ; j<3; j++){
                    //creation of each runner of a team
                    tmpfn = br.readLine();
                    tmpln = br.readLine();
                    line = br.readLine();
                    tmpl = Integer.parseInt(line);
                    ar.add(new Runner(tmpfn,tmpln,tmpl));
                }

                //creation of the team with the name and the runners
                teamList.add(new Team(teamName,ar.get(0),ar.get(1),ar.get(2),tmppos));

                //cleaning the list
                for(int k=0 ; k<3; k++){
                    ar.remove(0);
                }

                //writing of the 15 times of the team (3 runners x 5 times)
                for(int l = 0 ; l < 3 ; l++) {
                    for(int m = 0 ; m < 5 ; m++) {
                        tmpLong = Long.parseLong(br.readLine());
                        teamList.get(i).getRunner().get(l).getTimeList().add(tmpLong);
                    }
                }

                Collections.sort(teamList, new Comparator<Team>() {
                    public int compare(Team t1, Team t2) {
                        return ((Integer) t1.position).compareTo(t2.position);
                    }
                });
            }

            br.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try { if (br!=null) br.close(); } catch (Exception e) {}
        }
    }

    void rankingDisplay(){
        TextView tv;
        ImageView iv;
        String s;
        int id;
        long time=0;

        for(int i=0; i<nbTeams; i++){
            s = "image" + (i+1);
            id = getResources().getIdentifier(s, "id", getPackageName());
            iv = (ImageView) findViewById(id);
            iv.setVisibility(View.VISIBLE);

            s="teamname" + (i+1);
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setText(Character.toString(teamList.get(i).getName()));
            tv.setVisibility(View.VISIBLE);

            s="teamtime" + (i+1);
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);

            time = teamList.get(i).teamTime();
            int secs=(int)(time/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(time%1000);
            tv.setText(""+mins+":"+String.format("%02d",secs)+":"+String.format("%3d",milliseconds));
            tv.setVisibility(View.VISIBLE);

        }

        for(int l=10; l>nbTeams; l--){
            s = "image" + l;
            id = getResources().getIdentifier(s, "id", getPackageName());
            iv = (ImageView) findViewById(id);
            iv.setVisibility(View.INVISIBLE);

            s= "teamname" + l;
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setVisibility(View.INVISIBLE);

            s= "teamtime" + l;
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setVisibility(View.INVISIBLE);
        }
    }

    void recordDisplay(int type){
        Team t = teamList.get(0);
        for(int i = 1;i<teamList.size();i++){
            if(t.fastestRunner(type).bestTime(type) > teamList.get(i).fastestRunner(type).bestTime(type))
                t = teamList.get(i);
        }

        ImageView iv = (ImageView) findViewById(R.id.imagerecord);
        switch (type) {
            case 0:
                iv.setImageDrawable(getResources().getDrawable(R.drawable.sprint));
                break;
            case 1:
                iv.setImageDrawable(getResources().getDrawable(R.drawable.obstacle));
                break;
            case 2:
                iv.setImageDrawable(getResources().getDrawable(R.drawable.pitstop));
                break;
            case 3:
                iv.setImageDrawable(getResources().getDrawable(R.drawable.lap));
                break;
            default:
                break;
        }

        int id = getResources().getIdentifier("recordname", "id", getPackageName());
        TextView tv = (TextView) findViewById(id);
        tv.setText(t.getName() + " - " + t.fastestRunner(type).getFirstName() + " " + t.fastestRunner(type).getLastName());

        id = getResources().getIdentifier("recordtime", "id", getPackageName());
        tv = (TextView) findViewById(id);
        tv.setText(t.getName() + " - " + t.fastestRunner(type).getFirstName() + " " + t.fastestRunner(type).getLastName());

        long time = t.fastestRunner(type).bestTime(type);
        int secs=(int)(time/1000);
        int mins=secs/60;
        secs%=60;
        int milliseconds=(int)(time%1000);
        tv.setText(""+mins+":"+String.format("%02d",secs)+":"+String.format("%3d",milliseconds));
    }

    void overallDisplay(int teamNumber){
        int id;
        TextView tv;

        for(int i = 0;i<3;i++){
            id = getResources().getIdentifier("runner" + (i+1), "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setText(teamList.get(teamNumber).getRunner().get(i).getFirstName() + " " + teamList.get(teamNumber).getRunner().get(i).getLastName() + " - " + teamList.get(teamNumber).getRunner().get(i).getLevel());
            tv.setVisibility(View.VISIBLE);

            for(int j = 1;j<=5;j++) {
                id = getResources().getIdentifier("runner" + (i+1) + j, "id", getPackageName());
                tv = (TextView) findViewById(id);

                long time = teamList.get(teamNumber).getRunner().get(i).getTimeList().get(j-1);
                int secs=(int)(time/1000);
                int mins=secs/60;
                secs%=60;
                int milliseconds=(int)(time%1000);
                tv.setText(""+mins+":"+String.format("%02d",secs)+":"+String.format("%3d",milliseconds));
                tv.setVisibility(View.VISIBLE);
            }
        }
    }

    void spinnerUpdate(){
        aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, teamNameList);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamNameSpinner.setAdapter(aa2);
        teamNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                try {
                    overallDisplay(pos);
                } catch (Exception e) {
                    // if any I/O error occurs
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });

        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeOptions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recordSpinner.setAdapter(aa);
        recordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                try {
                    recordDisplay(pos);
                } catch (Exception e) {
                    // if any I/O error occurs
                    e.printStackTrace();
                }
            }
            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
    }

    public void toHome(View view) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteResults(View view) {
        File file = new File(getFilesDir() + "/results.txt");
        file.delete();  //returns Boolean value
        toHome(view);
    }
}