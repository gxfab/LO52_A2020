package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * This activity allows the user to see all teams
 * for a race and consult the order of the participants
 */
public class TeamsActivity extends AppCompatActivity {

    private int raceId;

    private Spinner teams;
    private ArrayAdapter<String> teamsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        this.teams = (Spinner) findViewById(R.id.teams);
        Bundle b = getIntent().getExtras();
        this.raceId = -1; // or other values
        if(b != null) {
            this.raceId = b.getInt("raceId");
        }
        this.initializeTeams();
    }

    /**
     * Method used to initialize the different teams of the race
     */
    public void initializeTeams() {
        AppDatabase db = AppDatabase.getInstance(this);
        int teamsNumber = db.participateRaceDAO().getTeamsNumber(raceId);
        ArrayList<String> teams = new ArrayList<>();
        for(int i = 0; i < teamsNumber; i++) {
            teams.add("Equipe " + String.valueOf(i+1));
        }
        this.teamsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                teams);
        this.teams.setAdapter(this.teamsAdapter);
    }

    /**
     * Method used to consult the members of a team
     * Opens TeamActivity
     * @param view
     */
    public void consultTeam(View view) {
        int teamNumber = Integer.parseInt(this.teams.getSelectedItem().toString().split(" ")[1]);
        Log.d("test",teamNumber + "");
        Intent intent = new Intent(this, TeamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putInt("raceId", this.raceId);
        b.putInt("teamNumber", teamNumber);
        intent.putExtras(b);
        startActivity(intent);
    }

    /**
     * Method used to start a race
     * @param view
     */
    public void startRace(View view) {
        Intent intent = new Intent(this, RaceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putInt("raceId", this.raceId);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}
}