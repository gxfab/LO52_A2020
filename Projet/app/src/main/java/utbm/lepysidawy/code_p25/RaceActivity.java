package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Runner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Activity presenting the displaying of a race
 */
public class RaceActivity extends AppCompatActivity {

    private int seconds;
    private boolean running;
    private int raceId;
    private ArrayList<ArrayList<Runner>> teams;

    //ArrayList containing the views for the current runner of a team
    //and its step in the race
    private ArrayList<ArrayList<TextView>> teamInfoViews;
    private ArrayList<LinearLayout> teamsLayout;
    private TextView counter;
    private TextView courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        this.seconds = 0;
        this.running = false;
        this.teams = new ArrayList<>();
        this.teamInfoViews = new ArrayList<>();
        this.teamsLayout = new ArrayList<>();
        this.counter = (TextView)findViewById(R.id.counter);
        this.courseName = (TextView)findViewById(R.id.courseName);
        Bundle b = getIntent().getExtras();
        this.raceId = -1;
        if(b != null) {
            this.raceId = b.getInt("raceId");
        }
        this.initializeData();
        this.runTimer();
    }

    /**
     * Method used to initialize the different data used for the race
     * (courseName, runners...)
     */
    public void initializeData() {
        AppDatabase db = AppDatabase.getInstance(this);
        this.courseName.setText(db.raceDAO().getRaceName(this.raceId));
        int teamsNumber = db.participateRaceDAO().getTeamsNumber(this.raceId);
        for(int i = 0; i < teamsNumber; i++) {
            this.teams.add(new ArrayList<Runner>());
            List<ParticipateRace> participations = db.participateRaceDAO().getParticipationsFromCourseAndTeamNumber(this.raceId, i+1);
            Collections.sort(participations, new Comparator<ParticipateRace>(){
                public int compare(ParticipateRace p1, ParticipateRace p2) {
                    return Integer.compare(p1.getRunningOrder(), p2.getRunningOrder());
                }
            });
            for(int j = 0; j < participations.size(); j++) {
                Runner currentRunner = db.runnerDAO().getRunnerFromId(participations.get(j).getIdRunner());
                Log.i("info", currentRunner.toString());
                Log.i("info", participations.get(j).toString());
                this.teams.get(i).add(currentRunner);
            }
        }
        this.initializeLayouts(teamsNumber);
        for(int i = 0; i < this.teamInfoViews.size(); i++) {
            this.teamInfoViews.get(i).get(0).setText(this.teams.get(i).get(0).getLastName());
        }
    }

    /**
     * Method used to initialize the team layouts
     * according to the number of teams for the current race
     */
    public void initializeLayouts(int teamsNumber) {
        for(int i = 0; i < teamsNumber; i++) {
            switch(i) {
                case 0 :
                    LinearLayout firstLayout = (LinearLayout) findViewById(R.id.firstTeam);
                    Log.i("info", String.valueOf(firstLayout.getChildCount()));
                    this.teamsLayout.add(firstLayout);
                    this.addLayoutChildrenToViews(firstLayout);
                    firstLayout.setVisibility(View.VISIBLE);
                    break;
                case 1 :
                    LinearLayout secondLayout = (LinearLayout) findViewById(R.id.secondTeam);
                    this.teamsLayout.add(secondLayout);
                    this.addLayoutChildrenToViews(secondLayout);
                    secondLayout.setVisibility(View.VISIBLE);
                    break;
                case 2 :
                    LinearLayout thirdLayout = (LinearLayout) findViewById(R.id.thirdTeam);
                    this.teamsLayout.add(thirdLayout);
                    this.addLayoutChildrenToViews(thirdLayout);
                    thirdLayout.setVisibility(View.VISIBLE);
                    break;
                case 3 :
                    LinearLayout fourthLayout = (LinearLayout) findViewById(R.id.fourthTeam);
                    this.teamsLayout.add(fourthLayout);
                    this.addLayoutChildrenToViews(fourthLayout);
                    fourthLayout.setVisibility(View.VISIBLE);
                    break;
                case 4 :
                    LinearLayout fifthLayout = (LinearLayout) findViewById(R.id.fifthTeam);
                    this.teamsLayout.add(fifthLayout);
                    this.addLayoutChildrenToViews(fifthLayout);
                    fifthLayout.setVisibility(View.VISIBLE);
                    break;
                case 5 :
                    LinearLayout sixthLayout = (LinearLayout) findViewById(R.id.sixthTeam);
                    this.teamsLayout.add(sixthLayout);
                    this.addLayoutChildrenToViews(sixthLayout);
                    sixthLayout.setVisibility(View.VISIBLE);
                    break;
                case 6 :
                    LinearLayout seventhLayout = (LinearLayout) findViewById(R.id.seventhTeam);
                    this.teamsLayout.add(seventhLayout);
                    this.addLayoutChildrenToViews(seventhLayout);
                    seventhLayout.setVisibility(View.VISIBLE);
                    break;
                case 7 :
                    LinearLayout eightLayout = (LinearLayout) findViewById(R.id.eightTeam);
                    this.teamsLayout.add(eightLayout);
                    this.addLayoutChildrenToViews(eightLayout);
                    eightLayout.setVisibility(View.VISIBLE);
                    break;
                case 8 :
                    LinearLayout ninthLayout = (LinearLayout) findViewById(R.id.ninthTeam);
                    this.teamsLayout.add(ninthLayout);
                    this.addLayoutChildrenToViews(ninthLayout);
                    ninthLayout.setVisibility(View.VISIBLE);
                    break;
                case 9 :
                    LinearLayout tenthLayout = (LinearLayout) findViewById(R.id.tenthTeam);
                    this.teamsLayout.add(tenthLayout);
                    this.addLayoutChildrenToViews(tenthLayout);
                    tenthLayout.setVisibility(View.VISIBLE);
                    break;
                case 10 :
                    LinearLayout eleventhLayout = (LinearLayout) findViewById(R.id.eleventhTeam);
                    this.teamsLayout.add(eleventhLayout);
                    this.addLayoutChildrenToViews(eleventhLayout);
                    eleventhLayout.setVisibility(View.VISIBLE);
                    break;
                case 11 :
                    LinearLayout twelthLayout = (LinearLayout) findViewById(R.id.twelthTeam);
                    this.teamsLayout.add(twelthLayout);
                    this.addLayoutChildrenToViews(twelthLayout);
                    twelthLayout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    /**
     * Add text layouts to the list of views
     */
    public void addLayoutChildrenToViews(LinearLayout layout) {
        this.teamInfoViews.add(new ArrayList<TextView>());
        for(int j = 1 ; j < layout.getChildCount(); j++) {
            this.teamInfoViews.get(this.teamInfoViews.size()-1).add((TextView) layout.getChildAt(j));
        }
    }

    /**
     * Method called when the stopwatch is clicked
     */
    public void activateStopwatch(View view){
        this.running = !this.running;
        if(this.running) {
            for(int i = 0; i < this.teamsLayout.size(); i++) {
                this.teamsLayout.get(i).setClickable(true);
            }
        } else {
            this.showValidationMessage();
        }
    }

    /**
     * Method used to handle the stopwatch
     */
    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                counter.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    /**
     * Method used to change the race step of a team
     * @param v
     */
    public void onTeamClick(View v) {
        switch(v.getId()) {
            case(R.id.firstTeam) :
                this.changeStep(0);
                break;
            case(R.id.secondTeam) :
                this.changeStep(1);
                break;
            case(R.id.thirdTeam) :
                this.changeStep(2);
                break;
            case(R.id.fourthTeam) :
                this.changeStep(3);
                break;
            case(R.id.fifthTeam) :
                this.changeStep(4);
                break;
            case(R.id.sixthTeam) :
                this.changeStep(5);
                break;
            case(R.id.seventhTeam) :
                this.changeStep(6);
                break;
            case(R.id.eightTeam) :
                this.changeStep(7);
                break;
            case(R.id.ninthTeam) :
                this.changeStep(8);
                break;
            case(R.id.tenthTeam) :
                this.changeStep(9);
                break;
            case(R.id.eleventhTeam) :
                this.changeStep(10);
                break;
            case(R.id.twelthTeam) :
                this.changeStep(11);
                break;
        }
    }

    /**
     * Method calculating a step change
     */
    public void changeStep(int teamId) {
        String currentStep = this.teamInfoViews.get(teamId).get(1).getText().toString();
        int runnerId = this.getIdOfRunnerFromName(this.teamInfoViews.get(teamId).get(0).getText().toString(), teamId);
        AppDatabase db = AppDatabase.getInstance(this);
        switch(currentStep) {
            case "SPRINT 1" :
                Log.i("info", "SPRINT1 " + (float)this.seconds);
                db.participateRaceDAO().updateSprint1(this.raceId, this.teams.get(teamId).get(runnerId).getIdPlayer(), (float)this.seconds);
                this.teamInfoViews.get(teamId).get(1).setText("OBSTACLE 1");
                break;
            case "OBSTACLE 1" :
                Log.i("info", "OBSTACLE1 " + (float)this.seconds);
                db.participateRaceDAO().updateObstacle1(this.raceId, this.teams.get(teamId).get(runnerId).getIdPlayer(), (float)this.seconds);
                this.teamInfoViews.get(teamId).get(1).setText("PIT STOP");
                break;
            case "PIT STOP" :
                Log.i("info", "PITSTOP " + (float)this.seconds);
                db.participateRaceDAO().updatePitStop(this.raceId, this.teams.get(teamId).get(runnerId).getIdPlayer(), (float)this.seconds);
                this.teamInfoViews.get(teamId).get(1).setText("SPRINT 2");
                break;
            case "SPRINT 2" :
                Log.i("info", "SPRINT2 " + (float)this.seconds);
                db.participateRaceDAO().updateSprint2(this.raceId, this.teams.get(teamId).get(runnerId).getIdPlayer(), (float)this.seconds);
                this.teamInfoViews.get(teamId).get(1).setText("OBSTACLE 2");
                break;
            case "OBSTACLE 2" :
                Log.i("info", "OBSTACLE2 " + (float)this.seconds);
                db.participateRaceDAO().updateObstacle2(this.raceId, this.teams.get(teamId).get(runnerId).getIdPlayer(), (float)this.seconds);
                if(runnerId < 2) {
                    runnerId++;
                    this.teamInfoViews.get(teamId).get(1).setText("SPRINT 1");
                    this.teamInfoViews.get(teamId).get(0).setText(this.teams.get(teamId).get(runnerId).getLastName());
                } else {
                    this.teamInfoViews.get(teamId).get(1).setText("FINI !");
                }
                break;
        }
    }

    /**
     * Method used to get the id of a runner from its name
     * and teamId
     */
    public int getIdOfRunnerFromName(String name, int teamId){
        boolean nameFound = false;
        int counter = 0;
        while(!nameFound && counter < this.teams.get(teamId).size()){
            if(this.teams.get(teamId).get(counter).getLastName().equals(name)) {
                nameFound = true;
            } else {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Method used to show a validation message
     * in case the arbitrator wants to end the race
     */
    public void showValidationMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Validation de course");
        alertDialog.setMessage("Souhaitez-vous achever la course ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getBaseContext(), StatsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Bundle b = new Bundle();
                        b.putInt("raceId", raceId);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {}

}