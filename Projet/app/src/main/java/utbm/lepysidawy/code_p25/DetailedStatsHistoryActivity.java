package utbm.lepysidawy.code_p25;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Race;
import utbm.lepysidawy.code_p25.entity.Runner;

/**
 * Activity presenting the detailed stats of the history activity
 */
public class DetailedStatsHistoryActivity extends AppCompatActivity {

    private List<ParticipateRace> allParticipations;
    private ArrayList<ArrayList<ParticipateRace>> participationsByTeams = new ArrayList<>();

    private TextView sprint1race;
    private TextView obstacle1race;
    private TextView pitstoprace;
    private TextView sprint2race;
    private TextView obstacle2race;
    private TextView sprint1;
    private TextView obstacle1;
    private TextView pitstop;
    private TextView sprint2;
    private TextView obstacle2;
    private TextView sprint1team;
    private TextView obstacle1team;
    private TextView pitstopteam;
    private TextView sprint2team;
    private TextView obstacle2team;
    private TextView sprint1runner;
    private TextView obstacle1runner;
    private TextView pitstoprunner;
    private TextView sprint2runner;
    private TextView obstacle2runner;

    /**
     * Method used to get all the data required for
     * the activity (teams, runners, times...)
     */
    public void initializeData(){
        this.allParticipations = new ArrayList<>();
        AppDatabase db = AppDatabase.getInstance(this);
        List<Race> races = db.raceDAO().getAll();
        allParticipations = db.participateRaceDAO().getAll();
        for(Race r : races){
            int teamsNumber = db.participateRaceDAO().getTeamsNumber(r.getIdRace());
            for(int i = 0; i < teamsNumber; i++) {
                this.participationsByTeams.add(new ArrayList<ParticipateRace>());
                List<ParticipateRace> participations = db.participateRaceDAO().getParticipationsFromCourseAndTeamNumber(r.getIdRace(), i+1);
                Collections.sort(participations, new Comparator<ParticipateRace>(){
                    public int compare(ParticipateRace p1, ParticipateRace p2) {
                        return Integer.compare(p1.getRunningOrder(), p2.getRunningOrder());
                    }
                });
                for(int j = 0; j < participations.size(); j++) {
                    this.participationsByTeams.get(i).add(participations.get(j));
                }
            }
        }
    }

    /**
     * Function used to convert the value of seconds (float)
     * into an HH:MM:SS format
     * @param seconds
     * @return HH:MM:SS time
     */
    private String convertSecondsToFullTime(float seconds) {
        int timeInSeconds = (int) seconds;
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int secs = timeInSeconds % 60;
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
    }

    /**
     * Method used to initialize the stats of the detailed history activity
     */
    public void initializeStats(){
        initializeData();
        AppDatabase db = AppDatabase.getInstance(this);
        List<ParticipateRace> participationsSprint1 = new ArrayList<>(allParticipations);
        List<ParticipateRace> participationsObstacle1 = new ArrayList<>(allParticipations);
        List<ParticipateRace> participationsPitStop = new ArrayList<>(allParticipations);
        List<ParticipateRace> participationsSprint2 = new ArrayList<>(allParticipations);
        List<ParticipateRace> participationsObstacle2 = new ArrayList<>(allParticipations);
        //Maybe replace these numerous query by a list ?
        for(ParticipateRace pr : participationsSprint1){
            if(pr.getRunningOrder() > 1){
                float time = pr.getSprint1() - db.participateRaceDAO().getParticipationsFromCourseAndTeamNumberAndRunningOrder(pr.getIdRace(),pr.getTeamNumber(),pr.getRunningOrder()-1).getObstacle2();
                pr.setSprint1(time);
            }
        }

        for(ParticipateRace pr : participationsObstacle1){
            float time = pr.getObstacle1() - db.participateRaceDAO().getParticipationsFromCourseAndTeamNumberAndRunningOrder(pr.getIdRace(),pr.getTeamNumber(),pr.getRunningOrder()).getSprint1();
            pr.setObstacle1(time);
        }

        for(ParticipateRace pr : participationsPitStop){
            float time = pr.getPitStop() - db.participateRaceDAO().getParticipationsFromCourseAndTeamNumberAndRunningOrder(pr.getIdRace(),pr.getTeamNumber(),pr.getRunningOrder()).getObstacle1();
            pr.setPitStop(time);
        }

        for(ParticipateRace pr : participationsSprint2){
            float time = pr.getSprint2() - db.participateRaceDAO().getParticipationsFromCourseAndTeamNumberAndRunningOrder(pr.getIdRace(),pr.getTeamNumber(),pr.getRunningOrder()).getPitStop();
            pr.setSprint2(time);
        }

        for(ParticipateRace pr : participationsObstacle2){
            float time = pr.getObstacle2() - db.participateRaceDAO().getParticipationsFromCourseAndTeamNumberAndRunningOrder(pr.getIdRace(),pr.getTeamNumber(),pr.getRunningOrder()).getSprint2();
            pr.setObstacle2(time);
        }

        Collections.sort(participationsSprint1, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Float.compare(p1.getSprint1(), p2.getSprint1());
            }
        });
        Collections.sort(participationsObstacle1, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Float.compare(p1.getObstacle1(), p2.getObstacle1());
            }
        });
        Collections.sort(participationsPitStop, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Float.compare(p1.getPitStop(), p2.getPitStop());
            }
        });
        Collections.sort(participationsSprint2, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Float.compare(p1.getSprint2(), p2.getSprint2());
            }
        });
        Collections.sort(participationsObstacle2, new Comparator<ParticipateRace>(){
            public int compare(ParticipateRace p1, ParticipateRace p2) {
                return Float.compare(p1.getObstacle2(), p2.getObstacle2());
            }
        });
        
        ParticipateRace best =  participationsSprint1.get(0);
        String raceName = db.raceDAO().getRaceName(best.getIdRace());
        this.sprint1race.setText(raceName);
        this.sprint1.setText(this.convertSecondsToFullTime(best.getSprint1()));
        this.sprint1team.setText(Integer.toString(best.getTeamNumber()));
        Runner bestRunner = db.runnerDAO().getRunnerFromId(best.getIdRunner());
        this.sprint1runner.setText(bestRunner.getFirstName() + " " + bestRunner.getLastName());

        best =  participationsObstacle1.get(0);
        this.obstacle1race.setText(db.raceDAO().getRaceName(best.getIdRace()));
        this.obstacle1.setText(this.convertSecondsToFullTime(best.getObstacle1()));
        this.obstacle1team.setText(Integer.toString(best.getTeamNumber()));
        bestRunner = db.runnerDAO().getRunnerFromId(best.getIdRunner());
        this.obstacle1runner.setText(bestRunner.getFirstName() + " " + bestRunner.getLastName());

        best =  participationsPitStop.get(0);
        this.pitstoprace.setText(db.raceDAO().getRaceName(best.getIdRace()));
        this.pitstop.setText(this.convertSecondsToFullTime(best.getPitStop()));
        this.pitstopteam.setText(Integer.toString(best.getTeamNumber()));
        bestRunner = db.runnerDAO().getRunnerFromId(best.getIdRunner());
        this.pitstoprunner.setText(bestRunner.getFirstName() + " " + bestRunner.getLastName());

        best =  participationsSprint2.get(0);
        this.sprint2race.setText(db.raceDAO().getRaceName(best.getIdRace()));
        this.sprint2.setText(this.convertSecondsToFullTime(best.getSprint2()));
        this.sprint2team.setText(Integer.toString(best.getTeamNumber()));
        bestRunner = db.runnerDAO().getRunnerFromId(best.getIdRunner());
        this.sprint2runner.setText(bestRunner.getFirstName() + " " + bestRunner.getLastName());

        best =  participationsObstacle2.get(0);
        this.obstacle2race.setText(db.raceDAO().getRaceName(best.getIdRace()));
        this.obstacle2.setText(this.convertSecondsToFullTime(best.getObstacle2()));
        this.obstacle2team.setText(Integer.toString(best.getTeamNumber()));
        bestRunner = db.runnerDAO().getRunnerFromId(best.getIdRunner());
        this.obstacle2runner.setText(bestRunner.getFirstName() + " " + bestRunner.getLastName());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_stats_history);

        this.sprint1race = (TextView) findViewById(R.id.sprint1race);
        this.obstacle1race = (TextView) findViewById(R.id.obstacle1race);
        this.pitstoprace = (TextView) findViewById(R.id.pitstoprace);
        this.sprint2race = (TextView) findViewById(R.id.sprint2race);
        this.obstacle2race = (TextView) findViewById(R.id.obstacle2race);
        this.sprint1 = (TextView) findViewById(R.id.sprint1);
        this.obstacle1 = (TextView) findViewById(R.id.obstacle1);
        this.pitstop = (TextView) findViewById(R.id.pitstop);
        this.sprint2 = (TextView) findViewById(R.id.sprint2);
        this.obstacle2 = (TextView) findViewById(R.id.obstacle2);
        this.sprint1team = (TextView) findViewById(R.id.sprint1team);
        this.obstacle1team = (TextView) findViewById(R.id.obstacle1team);
        this.pitstopteam = (TextView) findViewById(R.id.pitstopteam);
        this.sprint2team = (TextView) findViewById(R.id.sprint2team);
        this.obstacle2team = (TextView) findViewById(R.id.obstacle2team);
        this.sprint1runner = (TextView) findViewById(R.id.sprint1runner);
        this.obstacle1runner = (TextView) findViewById(R.id.obstacle1runner);
        this.pitstoprunner = (TextView) findViewById(R.id.pitstoprunner);
        this.sprint2runner = (TextView) findViewById(R.id.sprint2runner);
        this.obstacle2runner = (TextView) findViewById(R.id.obstacle2runner);

        initializeStats();
    }

    /**
     * Method used to finish the activity and go back to the main screen
     * @param view
     */
    public void finish (View view) {
        super.finish();
    }
}