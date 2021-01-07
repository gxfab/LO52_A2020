package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Runner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Activity presenting the data of a race
 */
public class StatsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner teamsSpinner;
    private Spinner runnersSpinner;
    private ArrayAdapter<String> teamsAdapter;
    private ArrayAdapter<Runner> runnersAdapter;
    private TextView sprint1;
    private TextView obstacle1;
    private TextView pitstop;
    private TextView sprint2;
    private TextView obstacle2;
    private TextView sprint1Time;
    private TextView obstacle1Time;
    private TextView pitstopTime;
    private TextView sprint2Time;
    private TextView obstacle2Time;

    private int raceId;
    private int teamsNumber;
    private ArrayList<ArrayList<Runner>> teams;
    private ArrayList<ArrayList<ParticipateRace>> participations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        this.teamsSpinner = (Spinner) findViewById(R.id.teamsSpinner);
        this.runnersSpinner = (Spinner) findViewById(R.id.runnersSpinner);
        this.sprint1 = (TextView) findViewById(R.id.sprint1);
        this.obstacle1 = (TextView) findViewById(R.id.obstacle1);
        this.pitstop = (TextView) findViewById(R.id.pitstop);
        this.sprint2 = (TextView) findViewById(R.id.sprint2);
        this.obstacle2 = (TextView) findViewById(R.id.obstacle2);
        this.sprint1Time = (TextView) findViewById(R.id.sprint1time);
        this.obstacle1Time = (TextView) findViewById(R.id.obstacle1time);
        this.pitstopTime = (TextView) findViewById(R.id.pitstoptime);
        this.sprint2Time = (TextView) findViewById(R.id.sprint2time);
        this.obstacle2Time = (TextView) findViewById(R.id.obstacle2time);
        this.teamsSpinner.setOnItemSelectedListener(this);
        this.runnersSpinner.setOnItemSelectedListener(this);
        this.teamsNumber = 0;
        this.teams = new ArrayList<>();
        this.participations = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        this.raceId = -1;
        if(b != null) {
            this.raceId = b.getInt("raceId");
        }
        this.initializeData();
        this.initializeTeamsSpinner();
        this.initializeRunnersSpinner();
        this.updateStepTimes(0, 0);
    }

    /**
     * Method used to get all the data required for
     * the activity (teams, runners, times...)
     */
    public void initializeData(){
        AppDatabase db = AppDatabase.getInstance(this);
        this.teamsNumber = db.participateRaceDAO().getTeamsNumber(this.raceId);
        for(int i = 0; i < this.teamsNumber; i++) {
            this.teams.add(new ArrayList<Runner>());
            this.participations.add(new ArrayList<ParticipateRace>());
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
                this.participations.get(i).add(participations.get(j));
            }
        }
        Log.i("info", this.participations.get(0).get(0).toString());
    }

    /**
     * Method used to initialize the teams spinner
     */
    public void initializeTeamsSpinner() {
        ArrayList<String> teamsSpinner = new ArrayList();
        for(int i = 0; i < this.teamsNumber; i++) {
            teamsSpinner.add("Equipe " + String.valueOf(i+1));
        }
        this.teamsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                teamsSpinner);
        this.teamsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.teamsSpinner.setAdapter(this.teamsAdapter);
    }

    /**
     * Method used to initialize the runners spinner
     */
    public void initializeRunnersSpinner() {
        this.runnersAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                this.teams.get(0));
        this.runnersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.runnersSpinner.setAdapter(this.runnersAdapter);
    }

    /**
     * Method used to update the step times
     * using the id of the team and the position of the player in the team
     */
    public void updateStepTimes(int teamId, int playerPos) {
        //Update chrono tip times
        String time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getSprint1());
        this.sprint1.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getObstacle1());
        this.obstacle1.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getPitStop());
        this.pitstop.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getSprint2());
        this.sprint2.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getObstacle2());
        this.obstacle2.setText(time);
        //Update step times
        if(playerPos > 0){
            time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getSprint1() - this.participations.get(teamId).get(playerPos-1).getObstacle2());
        }else{
            time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getSprint1());
        }
        this.sprint1Time.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getObstacle1() - this.participations.get(teamId).get(playerPos).getSprint1());
        this.obstacle1Time.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getPitStop() - this.participations.get(teamId).get(playerPos).getObstacle1());
        this.pitstopTime.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getSprint2() - this.participations.get(teamId).get(playerPos).getPitStop());
        this.sprint2Time.setText(time);
        time = this.convertSecondsToFullTime(this.participations.get(teamId).get(playerPos).getObstacle2() - this.participations.get(teamId).get(playerPos).getSprint2());
        this.obstacle2Time.setText(time);
    }

    /**
     * Function used to convert the value of seconds (float)
     * into an HH:MM:SS format
     * @param seconds
     * @return HH:MM:SS time
     */
    public String convertSecondsToFullTime(float seconds) {
        Log.i("info", String.valueOf(seconds));
        int timeInSeconds = (int) seconds;
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int secs = timeInSeconds % 60;
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
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
     * Method used to finish the activity and go back to the main screen
     * @param view
     */
    public void finish (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Method used to launch the detailed stats activity of the race
     * @param view
     */
    public void detailedStats(View view){
        Intent intent = new Intent(this, DetailedStatsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putInt("raceId", raceId);
        intent.putExtras(b);
        startActivity(intent);
    }

    /**
     * Method used to launch the detailed stats activity of the race
     * @param view
     */
    public void ranking(View view){
        Intent intent = new Intent(this, RankingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putInt("raceId", raceId);
        intent.putExtras(b);
        startActivity(intent);
    }

    /**
     * Method launched when an item is selected in one of the spinners
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.i("info", String.valueOf(pos));
        if(parent.getSelectedItem().toString().startsWith("Equipe")){
            this.runnersAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                this.teams.get(pos));
             this.runnersSpinner.setAdapter(this.runnersAdapter);
        } else {
            Runner currentRunner = (Runner) parent.getSelectedItem();
            int teamId = this.teamsSpinner.getSelectedItemPosition();
            Log.i("info", String.valueOf(teamId));
            int runnerId = this.getIdOfRunnerFromName(currentRunner.getLastName(), teamId);
            this.updateStepTimes(teamId, runnerId);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onBackPressed() {}
}