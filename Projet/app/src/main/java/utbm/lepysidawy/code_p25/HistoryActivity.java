package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.Race;

public class HistoryActivity extends AppCompatActivity {

    /**
     * Initializes the races views
     */
    private void initializeView()
    {
        LinearLayout v = findViewById(R.id.history_layout);
        AppDatabase db = AppDatabase.getInstance(this);
        List<Race> races = db.raceDAO().getAll();

        for(Race r : races)
        {
            ViewRace vr = new ViewRace(getApplicationContext(),r, null);
            v.addView(vr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initializeView();
    }

    /**
     * Method used to finish the activity and go back to the main screen
     * @param view
     */
    public void finish (View view) {
        super.finish();
    }

    /**
     * Method starting the detailed stats history activity
     * @param view
     */
    public void detailedStats(View view){
        Intent intent = new Intent(this, DetailedStatsHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}