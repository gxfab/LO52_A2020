package com.example.f1_levier.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.R;
import com.example.f1_levier.adapter.ParticipantAdapter;
import com.example.f1_levier.adapter.RankAdapter;
import com.example.f1_levier.adapter.TimeAdapter;

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
    TextView te_pit;
    TextView te_id_pit;
    TextView te_time_pit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        te_rank = findViewById(R.id.tv_rank);

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

        te_pit = findViewById(R.id.textView_name_pit);
        te_id_pit = findViewById(R.id.textView_team_pit);
        te_time_pit = findViewById(R.id.textView_time_pit);

        Button b_participant = findViewById(R.id.button_rank_participant);
        Button b_team = findViewById(R.id.button_time_team);

        /*rating*/
        rating();

        /*best runner*/
        best_runner();

        /*best sprinteur*/
        best_sp();

        /*best coureur d'obstacle*/
        best_ob();

        /*best pit*/
        best_pit();

        /*dialog pour classement indiv*/
        b_participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rank();
            }
        });

        /*dialog pour la liste des temps par equipe*/
        b_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team_time();
            }
        });

    }

    private void rank(){
        List<Runner> sortedList = db.getRunnersOrderedByRating(runnerList);
        AlertDialog.Builder d_rank = new AlertDialog.Builder(StatActivity.this);
        d_rank.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        d_rank.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_rank, null);
        d_rank.setView(view);
        ListView listView = (ListView) view.findViewById(R.id.listView_rank_participant);
        RankAdapter adapter = new RankAdapter(this, sortedList);
        listView.setAdapter(adapter);
        d_rank.show();
    }

    private void team_time(){
        List<List<String>> averageTime = db.getAverageTeamTime(teams);
        AlertDialog.Builder d_time = new AlertDialog.Builder(StatActivity.this);
        d_time.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        View view = getLayoutInflater().inflate(R.layout.dialog_time, null);
        d_time.setView(view);
        ListView listView = (ListView) view.findViewById(R.id.listView_time_team);
        TimeAdapter adapter = new TimeAdapter(this, averageTime);
        listView.setAdapter(adapter);
        d_time.show();
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
        List<String> runner = db.getBestTimeAsString(runnerList, teams,0);
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

    private void best_pit(){
        List<String> pit = db.getBestTimeAsString(runnerList, teams,3);
        te_pit.setText(pit.get(1)+" "+pit.get(2));
        te_id_pit.setText(pit.get(0));
        te_time_pit.setText(pit.get(3));
    }
}
