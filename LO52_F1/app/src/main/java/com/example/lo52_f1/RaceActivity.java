package com.example.lo52_f1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RaceActivity extends AppCompatActivity {

    ArrayList<Team> teamList;
    ArrayList<ArrayList<Long>> timeList = new ArrayList<ArrayList<Long>>();
    ArrayList<Long> lastTimeList = new ArrayList<Long>();

    Handler handler = new Handler();
    long initTime=0L,timeInMilliSeconds=0L,timeSwapBuff=0L,updateTime=0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis()-initTime;
            updateTime=timeSwapBuff+timeInMilliSeconds;
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            TextView tv = (TextView) findViewById(R.id.raceTime);
            tv.setText(""+mins+":"+String.format("%02d",secs)+":"+String.format("%3d",milliseconds));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        teamList = (ArrayList<Team>) getIntent().getSerializableExtra("tL");
        for(int i = 0;i<teamList.size();i++)
            lastTimeList.add(0L);
        raceDisplayInit(teamList);
    }

    public void raceDisplayInit (ArrayList<Team> teamList) {
        String s;
        int id;
        TextView tv;
        for(int i = 0 ; i < teamList.size() ; i++){
            s = "runner" + (char) ((char)i+65);
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setText(teamList.get(i).getRunner().get(0).getFirstName() + " " + teamList.get(i).getRunner().get(0).getLastName());
            timeList.add(new ArrayList<Long>());
        }
    }

    public void results (View view) {
        try {
            String date;
            File file = new File(getFilesDir() + "/results.txt");
            // if file doesnt exists, then we create it
            if (!file.exists()){
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);

            // writing of separator, today's date and number of teams
            bw.write("-");
            bw.newLine();
            Date today = new Date();
            DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.MEDIUM);
            date = mediumDateFormat.format(today);
            bw.write(date);
            bw.newLine();
            if(teamList.size()!=10)
                bw.write((char) ((char) teamList.size()+48));
            else
                bw.write("10");
            bw.newLine();

            for(int i = 0 ; i < teamList.size() ; i++) {
                //Writing name and position of the team
                bw.write(teamList.get(i).getName());
                bw.newLine();
                if(teamList.get(i).getPosition()!=10)
                    bw.write((char) ((char) teamList.get(i).getPosition()+48));
                else
                    bw.write("10");
                bw.newLine();
                for (int j = 0; j < 3; j++) {
                    //Writing runners
                    bw.write(teamList.get(i).getRunner().get(j).getFirstName());
                    bw.newLine();
                    bw.write(teamList.get(i).getRunner().get(j).getLastName());
                    bw.newLine();
                    if(teamList.get(i).getRunner().get(j).getLevel()!=10)
                        bw.write((char) ((char) teamList.get(i).getRunner().get(j).getLevel()+48));
                    else
                        bw.write("10");
                    bw.newLine();
                }

                //Writing times
                for(int k = 0 ; k < 3 ; k++) {
                    for(int l = 0 ; l < 5 ; l++) {
                        bw.write(teamList.get(i).getRunner().get(k).getTimeList().get(l).toString());
                        bw.newLine();
                    }
                }
            }

            bw.flush();
            bw.close();

        } catch(Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("lastRaceOnly", true);
        startActivity(intent);

    }

    public void timeButton (View view) {
        Button b = (Button) view;
        String s = b.getText().toString();
        ImageView iv;
        TextView tv;
        int teamNumber = s.charAt(0) - 65;
        int id, step = timeList.get(teamNumber).size();
        step=0;
        for(int i = 0 ; i < 3 ; i++) {
            step += teamList.get(teamNumber).getRunner().get(i).getTimeList().size();
        }

        teamList.get(teamNumber).getRunner().get(step/5).getTimeList().add(updateTime - lastTimeList.get(teamNumber));
        lastTimeList.set(teamNumber,updateTime);

        step = 0;
        for(int i = 0 ; i < 3 ; i++) {
            step += teamList.get(teamNumber).getRunner().get(i).getTimeList().size();
        }

        s = "step" + (char) ((char) teamNumber + 65);
        id = getResources().getIdentifier(s, "id", getPackageName());
        iv = (ImageView) findViewById(id);
        s = "step" + step;
        id = getResources().getIdentifier(s, "drawable", getPackageName());
        iv.setImageDrawable(getResources().getDrawable(id));

        s = "image" + (char) ((char) teamNumber + 65);
        id = getResources().getIdentifier(s, "id", getPackageName());
        iv = (ImageView) findViewById(id);

        if (step != 15) {
            //Changement de l'image indiquant l'étape du tour actuel (sprint 1/2, obstacle 1/2, pitstop)
            switch (step % 5) {
                case 0:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.sprint1));
                    String fullName;
                    if (step == 5)
                        fullName = teamList.get(teamNumber).getRunner().get(1).getFirstName() + " " + teamList.get(teamNumber).getRunner().get(1).getLastName();
                    else
                        fullName = teamList.get(teamNumber).getRunner().get(2).getFirstName() + " " + teamList.get(teamNumber).getRunner().get(2).getLastName();
                    s = "runner" + (char) ((char) teamNumber + 65);
                    id = getResources().getIdentifier(s, "id", getPackageName());
                    tv = (TextView) findViewById(id);
                    tv.setText(fullName);
                    break;
                case 1:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.obstacle1));
                    break;
                case 2:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.pitstop));
                    break;
                case 3:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.sprint2));
                    break;
                case 4:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.obstacle2));
                    break;
                default:
                    break;
            }

            step++;
        }
        else //si l'équipe a terminé sa course (ses 15 étapes)
        {
            //Affichage de la position de l'équipe, du temps des trois cycles,...

            //enregistrement de la position finale de l'équipe
            teamList.get(teamNumber).setPosition(calculP());
            long teamRaceTime = 0;
            for(int i = 0 ; i < 3 ; i++) {
                for(int j = 0; j<5; j++) {
                    teamRaceTime += teamList.get(teamNumber).getRunner().get(i).getTimeList().get(j);
                }
            }

            s = "runner" + (char) ((char) teamNumber + 65);
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);
            tv.setText("finished P"+teamList.get(teamNumber).getPosition());

            switch (teamList.get(teamNumber).getPosition()) {
                case 1:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.first));
                    break;
                case 2:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.second));
                    break;
                case 3:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.third));
                    break;
                case 4:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.fourth));
                    break;
                case 5:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.fifth));
                    break;
                case 6:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.sixth));
                    break;
                case 7:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.seventh));
                    break;
                case 8:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.eighth));
                    break;
                case 9:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.ninth));
                    break;
                case 10:
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.tenth));
                    break;
                default:
                    break;
            }
            b.setVisibility(View.INVISIBLE);
            //si l'équipe est dernière donc si la course est terminée
            if(teamList.get(teamNumber).getPosition() == teamList.size()) {
                Button b2 = (Button) findViewById(R.id.resultsButton);
                b2.setVisibility(View.VISIBLE);
                tv = (TextView) findViewById(R.id.raceTime);
                tv.setVisibility(View.INVISIBLE);
            }

            s = "time" + (char) ((char) teamNumber + 65);
            id = getResources().getIdentifier(s, "id", getPackageName());
            tv = (TextView) findViewById(id);

            long time = teamList.get(teamNumber).teamTime();
            int secs=(int)(time/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(time%1000);
            tv.setText(""+mins+":"+String.format("%02d",secs)+":"+String.format("%3d",milliseconds));
            //time=0;
            tv.setVisibility(View.VISIBLE);
        }
    }

    public int calculP () {
        int pos = 0;
        //On récupère la position max pour l'ensemble des teams et on y ajoute 1
        //Exemple : pos max = 6 alors pos de la team en question = 6+1 = 7
        for(int i=0;i<teamList.size();i++){
            if(teamList.get(i).getPosition() > pos)
                pos = teamList.get(i).getPosition();
        }
        pos ++;

        return pos;
    }

    public long sumPrevTime (int teamNumber, int runnerNumber) {
        long res = 0;
        for(int i=0;i<teamList.get(teamNumber).getRunner().get(runnerNumber).getTimeList().size();i++){
            res += teamList.get(teamNumber).getRunner().get(runnerNumber).getTimeList().get(i);
        }
        return res;
    }

    public void startRace (View view){
        initTime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimerThread,0);
        
        TextView tv;
        ImageView iv;
        Button b = (Button) view;
        b.setVisibility(View.INVISIBLE);
        String s ="";

        int i=0, j, id;
        for(j=0;j<4;j++) {
            for (i = 0; i < teamList.size(); i++) {
                switch(j)
                {
                    case 0 :
                        s = "image" + (char) ((char) i + 65);
                        break;
                    case 1 :
                        s = "button" + (char) ((char) i + 65);
                        break;
                    case 2 :
                        s = "runner" + (char) ((char) i + 65);
                        break;
                    case 3 :
                        s = "label" + (char) ((char) i + 65);
                        break;
                    default:
                        break;
                }
                id = getResources().getIdentifier(s, "id", getPackageName());
                if(j==0) {
                    iv = (ImageView) findViewById(id);
                    iv.setVisibility(View.VISIBLE);
                }
                else if(j==1) {
                    b = (Button) findViewById(id);
                    b.setVisibility(View.VISIBLE);
                }
                else {
                    tv = (TextView) findViewById(id);
                    tv.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}