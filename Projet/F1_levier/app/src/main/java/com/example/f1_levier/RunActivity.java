package com.example.f1_levier;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.f1_levier.TeamActivity.teams;

public class RunActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        final Chronometer cdt = findViewById(R.id.chronometer);
        final Button start = findViewById(R.id.button_start);
        final ListView listView_run = (ListView) findViewById(R.id.listView_run);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.INVISIBLE);
                cdt.setBase(SystemClock.elapsedRealtime());
                cdt.start();
            }
        });

        final RunAdapter adapter = new RunAdapter(this, teams);
        listView_run.setAdapter(adapter);
        listView_run.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                teams.get(i).setNb_step(teams.get(i).getNb_step()+1); //click
                
            }
        });

    }

}
