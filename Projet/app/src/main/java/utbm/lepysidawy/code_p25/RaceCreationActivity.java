package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Race;
import utbm.lepysidawy.code_p25.entity.Runner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * In this activity, the user can create a race
 * by adding runners to it.
 */
public class RaceCreationActivity extends AppCompatActivity {

    private ArrayList<Runner> runnersList;
    private ArrayList<Runner> participantsList;

    private Spinner runners;
    private Spinner participants;
    private EditText courseName;
    private ArrayAdapter<Runner> runnersAdapter;
    private ArrayAdapter<Runner> participantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_creation);
        this.runners = (Spinner) findViewById(R.id.runners);
        this.participants = (Spinner) findViewById(R.id.participants);
        this.courseName = (EditText) findViewById(R.id.courseName);
        this.runnersList = new ArrayList<>();
        this.participantsList = new ArrayList<>();
        AppDatabase db = AppDatabase.getInstance(this);
        List<Runner> runners = db.runnerDAO().getAll();
        this.runnersList.addAll(runners);
        this.initializeSpinners();
    }

    /**
     * Method which initializes the runners spinner
     */
    public void initializeSpinners(){
        this.runnersAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                this.runnersList);
        this.runnersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.runners.setAdapter(this.runnersAdapter);
        this.participantsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                this.participantsList);
        this.participantsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.participants.setAdapter(this.participantsAdapter);
    }

    /**
     * Method launched when the plus button is clicked
     */
    public void onPlusClick(View view) {
        if (this.runners.getSelectedItem() != null) {
            Runner currentElement = (Runner)runners.getSelectedItem();
            this.participantsList.add(currentElement);
            this.participantsAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    this.participantsList);
            this.participants.setAdapter(this.participantsAdapter);
            this.runnersList.remove(currentElement);
            this.runnersAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    this.runnersList);
            this.runners.setAdapter(this.runnersAdapter);
        }
    }

    /**
     * Method launched when the minus button is clicked
     */
    public void onMinusClick(View view){
        if(this.participants.getSelectedItem() != null) {
            Runner currentElement = (Runner)participants.getSelectedItem();
            this.runnersList.add(currentElement);
            this.runnersAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    this.runnersList);
            this.runners.setAdapter(this.runnersAdapter);
            this.participantsList.remove(currentElement);
            this.participantsAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    this.participantsList);
            this.participants.setAdapter(this.participantsAdapter);
        }
    }

    /**
     * Method used to a validate a race
     * The user must pick a number of runners which is a multiple of 3
     * and is <= 36
     * @param view
     */
    public void validateRace(View view) {
        if(this.participantsList.size()%3 == 0 && this.participantsList.size() <= 36) {
            //DAO return a long for the ID, but ID will unlikely excess 2,147,483,647, we can allow to do an int cast
            int raceId = (int)this.createRace(this.courseName.getText().toString());
            this.createTeams(raceId);
            Intent intent = new Intent(this, TeamsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = new Bundle();
            b.putInt("raceId", raceId);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            this.showErrorMessage();
        }
    }

    /**
     * Method used to launch an error message in case
     * the user chooses the wrong number of runners
     */
    public void showErrorMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Attention !");
        alertDialog.setMessage("Le nombre de coureurs que vous avez sélectionné est incorrect. Veuillez sélectionner suffisamment de coureurs" +
                        " pour remplir de une à douze équipes de trois.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }

    /**
     * Copy teams ArrayList in a new ArrayList
     */
    private ArrayList<ArrayList<Runner>> copyTeams(ArrayList<ArrayList<Runner>> teams)
    {
        ArrayList<ArrayList<Runner>> res = new ArrayList<ArrayList<Runner>>();
        for(int i = 0; i<teams.size(); i++){
            res.add(new ArrayList<Runner>(teams.get(i)));
        }
        return res;
    }

    /**
     * Calculate level variance between teams
     */
    private double variance(ArrayList<ArrayList<Runner>> teams){
        ArrayList<Double> averages = new ArrayList<>();
        for(ArrayList<Runner> team : teams){
            int sum = 0;
            for(Runner runner : team){
                sum += runner.getNiveau();
            }
            averages.add(sum/(team.size() + 0.0));
        }

        float average = 0;
        for(double f : averages){
            average += f;
        }
        average /= (averages.size() + 0.0f);

        double deviation = 0;
        for(double f : averages){
            deviation += ((f - average) * (f-average));
        }
        return Math.sqrt((1/(teams.size() + 0.0f))*deviation);
    }

    /**
     * Method used to create the different teams
     */
    public void createTeams(int raceId) {
        AppDatabase db = AppDatabase.getInstance(this);
        ArrayList<ArrayList<Runner>> teams = new ArrayList<>();
        int nbTeams = this.participantsList.size()/3;
        for(int i = 0; i < nbTeams; i++) {
            teams.add(new ArrayList<Runner>());
            for(int j = 0; j < 3; j++) {
                Runner selected = this.participantsList.get(new Random().nextInt(this.participantsList.size()));
                teams.get(i).add(selected);
                this.participantsList.remove(selected);
            }
        }

        Random random = new Random();
        //Loop and try to minimise variance between teams by randomly swapping two players
        for(int i = 0; i<2000;i++){
            ArrayList<ArrayList<Runner>> test = copyTeams(teams);
            int swapTeam1 = random.nextInt(nbTeams);
            int swapPlayer1 = random.nextInt(3);
            int swapTeam2 = random.nextInt(nbTeams);
            int swapPlayer2 = random.nextInt(3);
            Runner save = test.get(swapTeam1).get(swapPlayer1);
            test.get(swapTeam1).set(swapPlayer1, test.get(swapTeam2).get(swapPlayer2));
            test.get(swapTeam2).set(swapPlayer2, save);
            if(variance(test) < variance(teams)) teams = test;
        }

        for(int i = 0; i < teams.size(); i++) {
            for(int j = 0; j < 3; j++) {
                ParticipateRace participation = new ParticipateRace(raceId, teams.get(i).get(j).getIdPlayer(), i+1, j+1);
                db.participateRaceDAO().insert(participation);
            }
        }
    }

    /**
     * Method used to create the race and insert it in the database
     * Returns the race ID
     */
    public long createRace(String raceName) {
        AppDatabase db = AppDatabase.getInstance(this);
        return db.raceDAO().insert(new Race(raceName));
    }


}