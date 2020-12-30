package com.example.f1_levier.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.f1_levier.R;

import java.util.List;

import static com.example.f1_levier.view.TeamActivity.teams;
import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

public class StatActivity extends AppCompatActivity {

    TextView te_rank;
    TextView te_run;
    TextView te_id_run;
    TextView te_time_run;
    TextView te_sp1;
    TextView te_id_sp1;
    TextView te_time_sp1;
    TextView te_ob1;
    TextView te_id_ob1;
    TextView te_time_ob1;
    TextView te_sp2;
    TextView te_id_sp2;
    TextView te_time_sp2;
    TextView te_ob2;
    TextView te_id_ob2;
    TextView te_time_ob2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        te_rank = findViewById(R.id.textView_rank);

        te_run = findViewById(R.id.textView_name_run);
        te_id_run = findViewById(R.id.textView_team_run);
        te_time_run = findViewById(R.id.textView_time_run);

        te_sp1 = findViewById(R.id.textView_name_sp1);
        te_id_sp1 = findViewById(R.id.textView_team_sp1);
        te_time_sp1 =findViewById(R.id.textView_time_sp1);

        te_ob1 = findViewById(R.id.textView_name_ob1);
        te_id_ob1 = findViewById(R.id.textView_team_ob1);
        te_time_ob1 = findViewById(R.id.textView_time_ob1);

        te_sp2 = findViewById(R.id.textView_name_sp2);
        te_id_sp2 = findViewById(R.id.textView_team_sp2);
        te_time_sp2 = findViewById(R.id.textView_time_sp2);

        te_ob2 = findViewById(R.id.textView_name_ob2);
        te_id_ob2 = findViewById(R.id.textView_team_ob2);
        te_time_ob2 = findViewById(R.id.textView_time_ob2);

        /*rating*/
        rating();

        /*best runner*/
        best_runner();

        best_sp();

        best_ob();
    }

    private void rating() {
        StringBuilder temp = new StringBuilder();
        int i = 0;
        int rank = 1;
        while (temp.length() < 20) {
            if (i == 10) {
                i = 0;
            }
            int test = db.getTeamFromId(teams, i).getRating();
            if (test == rank) {
                temp.append(i + 1).append(" ");
                rank++;
            }
            i++;
        }
        te_rank.setText(temp);
    }

    private void best_runner(){
        List<String> runner = db.getBestTimeAsString(runnerList, teams,6); //TODO ici
        te_run.setText(runner.get(1)+" "+runner.get(2));
        te_id_run.setText(runner.get(0));
        te_time_run.setText(runner.get(3));
    }

    private void best_sp(){
        List<String> sp1 = db.getBestTimeAsString(runnerList, teams,1);
        te_sp1.setText(sp1.get(1)+" "+sp1.get(2));
        te_id_sp1.setText(sp1.get(0));
        te_time_sp1.setText(sp1.get(3));
        List<String>  sp2 = db.getBestTimeAsString(runnerList, teams,4);
        te_sp2.setText(sp2.get(1)+" "+sp2.get(2));
        te_id_sp2.setText(sp2.get(0));
        te_time_sp2.setText(sp2.get(3));
    }

    private void best_ob(){
        List<String> ob1 = db.getBestTimeAsString(runnerList, teams,2);
        te_ob1.setText(ob1.get(1)+" "+ob1.get(2));
        te_id_ob1.setText(ob1.get(0));
        te_time_ob1.setText(ob1.get(3));
        List<String>  ob2 = db.getBestTimeAsString(runnerList, teams,5);
        te_ob2.setText(ob2.get(1)+" "+ob2.get(2));
        te_id_ob2.setText(ob2.get(0));
        te_time_ob2.setText(ob2.get(3));
    }

}
