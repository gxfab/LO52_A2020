package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.ParticipateRace;
import utbm.lepysidawy.code_p25.entity.Race;
import utbm.lepysidawy.code_p25.entity.Runner;

/**
 * Main screen of the application.
 * Allows the user to create a race, a runner, display the stats or leave the app
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getInstance(this);
        Log.d("App","Running");

        List<Runner> runners = db.runnerDAO().getAll();
        if(runners.size() == 0)
        {
            List<ParticipateRace> participations = db.participateRaceDAO().getAll();
            for(ParticipateRace p : participations){
                db.participateRaceDAO().delete(p);
            }
            for(Runner r : runners){
                db.runnerDAO().delete(r);
            }

            List<Race> races = db.raceDAO().getAll();
            for(Race r : races){
                db.raceDAO().delete(r);
            }

            Runner paulMcCartney = new Runner("Paul","McCartney",80);
            Runner johnLennon = new Runner("John","Lennon",40);
            Runner ringoStarr = new Runner("Ringo","Starr",90);
            Runner georgeHarrison = new Runner("George","Harrison",95);
            Runner mickJagger = new Runner("Mick","Jagger",70);
            Runner keithRichards = new Runner("Keith","Richards",90);
            Runner robertPlant = new Runner("Robert","Plant",75);
            Runner markKnopfler = new Runner("Mark","Knopfler",80);
            Runner davidGilmour = new Runner("David","Gilmour",78);
            Runner liamGallagher = new Runner("Liam","Gallagher",62);
            Runner noelGallagher = new Runner("Noël","Gallagher",33);
            Runner thomYorke = new Runner("Thom","Yorke",98);
            Runner davidBowie = new Runner("David","Bowie",53);
            Runner louReed = new Runner("Lou","Reed",47);
            Runner brianWilson = new Runner("Brian","Wilson",22);




            long[] runnerIds = db.runnerDAO().insertAll(paulMcCartney,johnLennon,ringoStarr, georgeHarrison, mickJagger, keithRichards, robertPlant, markKnopfler, davidGilmour,
                    liamGallagher, noelGallagher, thomYorke, davidBowie, louReed, brianWilson);
        }



        /**Random rd = new Random();
         for(int i = 0; i<30; i++){
         db.runnerDAO().insertAll(new Runner("A" + i, "B"+ i,rd.nextInt(100)));
         }**/
    }

    public void onRaceCreationClick(View view) {
        Intent intent = new Intent(this, RaceCreationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onRunnerCreationClick(View view) {
        Intent intent = new Intent(this, RunnerCreationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onStatsButtonClick(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void closeApp(View view) {
        this.finishAndRemoveTask();
    }

}