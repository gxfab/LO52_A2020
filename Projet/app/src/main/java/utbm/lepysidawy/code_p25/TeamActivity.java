package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Runner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Activity in which the user
 * can change the order of the participants of a team
 */
public class TeamActivity extends AppCompatActivity {

    private int raceId;
    private int teamNumber;
    private ArrayList<Runner> runners;

    private Button firstPlayer;
    private Button secondPlayer;
    private Button thirdPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        this.firstPlayer = (Button) findViewById(R.id.firstPlayer);
        this.secondPlayer = (Button) findViewById(R.id.secondPlayer);
        this.thirdPlayer = (Button) findViewById(R.id.thirdPlayer);
        this.firstPlayer.setClickable(false);
        this.secondPlayer.setClickable(false);
        this.thirdPlayer.setClickable(false);
        this.runners = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        this.raceId = -1;
        this.teamNumber = -1;
        if(b != null) {
            this.raceId = b.getInt("raceId");
            this.teamNumber = b.getInt("teamNumber");
        }
        Log.i("info", String.valueOf(this.raceId));
        Log.i("info", String.valueOf(this.teamNumber));
        this.initializeRunners();
    }

    /**
     * Method used to initialize the runners
     */
    public void initializeRunners(){
        AppDatabase db = AppDatabase.getInstance(this);
        List<ParticipateRace> participations = db.participateRaceDAO().getParticipationsFromCourseAndTeamNumber(this.raceId, this.teamNumber);
        Collections.sort(participations, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Integer.compare(p1.getRunningOrder(), p2.getRunningOrder());
            }
        });
        for(int i = 0; i < participations.size(); i++) {
            Runner currentRunner = db.runnerDAO().getRunnerFromId(participations.get(i).getIdRunner());
            Log.i("info", currentRunner.toString());
            this.runners.add(currentRunner);
        }
        this.firstPlayer.setText(runners.get(0).toString());
        this.secondPlayer.setText(runners.get(1).toString());
        this.thirdPlayer.setText(runners.get(2).toString());
    }

    /**
     * Method invoked when the up arrow is clicked
     * @param view
     */
    public void upArrowClick(View view){
        Collections.swap(this.runners, 0, 1);
        this.firstPlayer.setText(runners.get(0).toString());
        this.secondPlayer.setText(runners.get(1).toString());
    }

    /**
     * Method invoked when the down arrow is clicked
     * @param view
     */
    public void downArrowClick(View view){
        Collections.swap(this.runners, 1, 2);
        this.secondPlayer.setText(runners.get(1).toString());
        this.thirdPlayer.setText(runners.get(2).toString());
    }

    /**
     * Method invoked when the current order of the team is validated
     * @param view
     */
    public void validateTeam(View view) {
        AppDatabase db = AppDatabase.getInstance(this);
        for(int i = 0; i < this.runners.size(); i++){
            int currentRunner = this.runners.get(i).getIdPlayer();
            db.participateRaceDAO().updateRunningOrder(this.raceId, currentRunner, i+1);
        }
        this.finish();
    }
}