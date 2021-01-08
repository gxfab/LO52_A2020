package com.djira.f1levier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.djira.f1levier.adapter.CourseAdapter;
import com.djira.f1levier.adapter.EquipeAdapter;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private static Chronometer chronometer;
    private static long pauseOffset;
    private static boolean running;
    EditText editText;
    View viewEquipe;
    RecyclerView recyclerView;

    List<Equipe> dataList = new ArrayList<>();
    List<Participant> participants = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    AppDB database;

    CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        chronometer = findViewById(R.id.chronometer);

//        //chronometer.setFormat("Time: %s");
//        chronometer.setBase(SystemClock.elapsedRealtime());
//
//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
//                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
//                    chronometer.setBase(SystemClock.elapsedRealtime());
//                    Toast.makeText(CourseActivity.this, "Bing!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        recyclerView = findViewById(R.id.recycler_view_equipe_participant);

        // Initialize database
        database = AppDB.getInstance(this);

        String equipeName = getIntent().getStringExtra("EQUIPE_NAME");

        Equipe equipe = database.equipeDao().getByNom(equipeName);


//        Equipe eq = new Equipe();
//        eq.setName("Equipe 1");

        //database.equipeDao().insert(eq);


//        database.participantDao().deleteFromEquipe();
//        database.equipeDao().reset(dataList);


        //System.out.println("****************** " +equipeId + " size equipe " + dataList.size());
//

        participants = database.participantDao().getAllByEquipe(equipe.getName());

//        if (!participants.isEmpty()) {
//            System.out.println("******************" + participants.get(0).getName());
//        } else {
//            System.out.println("****************** vide" );
//
//        }

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CourseAdapter(this,participants);
        recyclerView.setAdapter(adapter);
    }

    public static void startChronometer(View v) {

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public static int getTime(View v) {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        int h   = (int)(time /3600000);
        int m = (int)(time - h*3600000)/60000;
        return (int)(time)/1000;
    }

    public static void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void saveStep(View v) {

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
}