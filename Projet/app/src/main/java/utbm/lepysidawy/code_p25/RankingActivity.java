package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Runner;

public class RankingActivity extends AppCompatActivity {

    private class ChronoTeams
    {
        private int teamNumber;
        private float chrono;
        public ChronoTeams(int teamNumber, float chrono)
        {
            this.teamNumber = teamNumber;
            this.chrono = chrono;
        }
        public int getTeamNumber(){return teamNumber;}
        public float getChrono(){return chrono;}
    }

    private LinearLayout teamsLayout;
    private LinearLayout chronosLayout;
    private LinearLayout rankLayout;
    private int raceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        this.teamsLayout = (LinearLayout) findViewById(R.id.rankingTeams);
        this.chronosLayout = (LinearLayout) findViewById(R.id.rankingChronos);
        this.rankLayout = (LinearLayout) findViewById(R.id.rankingRank);
        this.raceId = -1;
        Bundle b = getIntent().getExtras();
        if(b != null) {
            this.raceId = b.getInt("raceId");
        }
        initializeData();
    }

    /**
     * Method used to finish the activity and go back to the main screen
     * @param view
     */
    public void finish (View view) {
        super.finish();
    }


    public void initializeData(){

        ArrayList<ChronoTeams> chronos = new ArrayList<>();
        AppDatabase db = AppDatabase.getInstance(this);
        int teamsNumber = db.participateRaceDAO().getTeamsNumber(this.raceId);
        for(int i = 0; i < teamsNumber; i++) {
            List<ParticipateRace> runners = db.participateRaceDAO().getParticipationsFromCourseAndTeamNumber(this.raceId, i+1);
            Collections.sort(runners, new Comparator<ParticipateRace>(){
                public int compare(ParticipateRace p1, ParticipateRace p2) {
                    return Float.compare(p2.getRunningOrder(), p1.getRunningOrder());
                }
            });
            float cumuledTime = runners.get(0).getObstacle2();

            Log.d("iii",cumuledTime + "");


            chronos.add(new ChronoTeams(i+1, cumuledTime));
        }

        Collections.sort(chronos, new Comparator<ChronoTeams>(){
            public int compare(ChronoTeams p1, ChronoTeams p2) {
                return Float.compare(p1.getChrono(), p2.getChrono());
            }
        });

        Typeface face = Typeface.create("monospace", Typeface.NORMAL);
        int i = 0;
        for(ChronoTeams ct : chronos)
        {
            i++;
            TextView tvTeam = new TextView(new ContextThemeWrapper(getBaseContext(), R.style.textView), null, 0);
            tvTeam.setTextAppearance(R.style.textView);
            tvTeam.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            tvTeam.setText("Equipe " + ct.teamNumber);
            tvTeam.setGravity(Gravity.CENTER);
            tvTeam.setTypeface(face);
            teamsLayout.addView(tvTeam);

            TextView tvChrono = new TextView(new ContextThemeWrapper(getBaseContext(), R.style.textView), null, 0);
            tvChrono.setTextAppearance(R.style.textView);
            tvChrono.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            tvChrono.setText(ct.chrono + " sec.");
            tvChrono.setGravity(Gravity.CENTER);
            tvChrono.setTypeface(face);
            chronosLayout.addView(tvChrono);

            TextView tvRank = new TextView(new ContextThemeWrapper(getBaseContext(), R.style.textView), null, 0);
            tvRank.setTextAppearance(R.style.textView);
            tvRank.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            tvRank.setText(Integer.toString(i));
            tvRank.setGravity(Gravity.CENTER);
            tvRank.setTypeface(face);
            rankLayout.addView(tvRank);
        }
    }
}