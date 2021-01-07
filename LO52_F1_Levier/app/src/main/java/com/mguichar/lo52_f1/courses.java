package com.mguichar.lo52_f1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class courses extends AppCompatActivity {

    // navbar variables
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private Activity context;
    public static int minutes = 0, seconds = 0, startMinutes = 0, startSeconds = 0;

    // course variables
    RoomDB database;
    RecyclerView recyclerView, timerRecyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout timerLinearLayout;
    CourseAdapter courseAdapter;
    CoureurAdapter coureurAdapter;
    //EquipeAdapter equipeAdapter
    List<Equipe> list = new ArrayList<>();
    List<Coureur> cList = new ArrayList<>();


    // timer variables
    TextView timerView,hiddenTimerView;
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;


            timerView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        recyclerView = findViewById(R.id.recycler_course_view);

        database = RoomDB.getInstance(this);
        list = database.equipeDao().getAll();
        cList = database.coureurDao().getAll();
        coureurAdapter = new CoureurAdapter(courses.this, cList);
        //equipeAdapter = new EquipeAdapter(courses.this, list);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        courseAdapter = new CourseAdapter(courses.this, list, coureurAdapter);
        recyclerView.setAdapter(courseAdapter);

        timerView = (TextView) findViewById(R.id.timerText);
        hiddenTimerView = (TextView) findViewById(R.id.hiddenTimerText);
        ImageView i = (ImageView) findViewById(R.id.start);

        Button b = (Button) findViewById(R.id.startTimer);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });

        Button stats = (Button) findViewById(R.id.statsEquipes);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(courses.this);
                dialog.setContentView(R.layout.dialog_equipe_stats);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                final TextView bestcycle = dialog.findViewById(R.id.bestcycle);
                final TextView averagecycletime = dialog.findViewById(R.id.averagecycletime);
                final TextView bestsprint = dialog.findViewById(R.id.bestsprint);
                final TextView bestobstacle = dialog.findViewById(R.id.bestobstacle);
                final TextView bestpitstop = dialog.findViewById(R.id.bestpitstop);

                int[] bestas = new int[5];
                int sprint = 0, cycle = 0, obstacle = 0, pitstop = 0;
                bestas[1] = 0;

                System.out.println(cList.size() + " EEEEEEEEEEEEEEE");

                for(int i = 0; i < cList.size(); ++i){

                    Coureur c = cList.get(i);

                    if(c.getHasRun()) {
                        System.out.println("youhou " + c.getSprint1() + " " + c.getObstacle1() + " " + c.getPitstop() + " " + c.getObstacle2() + " " + c.getSprint2());
                        bestas[1] += c.getTotalTime();

                        if (i == 0) {
                            bestas[0] = c.getCoureur_ID();
                            bestas[2] = c.getCoureur_ID();
                            bestas[3] = c.getCoureur_ID();
                            bestas[4] = c.getCoureur_ID();
                            cycle = c.getTotalTime();
                            sprint = c.getSprint1() + c.getSprint2();
                            obstacle = c.getObstacle1() + c.getObstacle2();
                            pitstop = c.getPitstop();
                        } else {
                            if (c.getTotalTime() < cycle) {
                                bestas[0] = c.getCoureur_ID();
                                cycle = c.getTotalTime();
                            }
                            if (c.getSprint1() + c.getSprint2() < sprint) {
                                bestas[2] = c.getCoureur_ID();
                                sprint = c.getSprint1() + c.getSprint2();
                            }
                            if (c.getObstacle1() + c.getObstacle2() < obstacle) {
                                bestas[3] = c.getCoureur_ID();
                                obstacle = c.getObstacle1() + c.getObstacle2();
                            }
                            if (c.getPitstop() < pitstop) {
                                bestas[4] = c.getCoureur_ID();
                                pitstop = c.getPitstop();
                            }
                        }
                    }

                }

                for(int i = 0; i < 5; ++i)
                    System.out.println(bestas[i]);

                bestcycle.setText("Meilleur cycle : " + database.coureurDao().getCoureurById(bestas[0]).get(0).getNom_equipe() + "(" + String.valueOf(cycle) + "s)");
                averagecycletime.setText("Temps moyen : " + String.valueOf(bestas[1]/cList.size()) + "s");
                bestsprint.setText("Meilleur sprint : " + database.coureurDao().getCoureurById(bestas[2]).get(0).getNom_equipe() + "(" + String.valueOf(sprint) + "s)");
                bestobstacle.setText("Meilleur obstacle : " + database.coureurDao().getCoureurById(bestas[3]).get(0).getNom_equipe() + "(" + String.valueOf(obstacle) + "s)");
                bestpitstop.setText("Meilleur pitstop : " + database.coureurDao().getCoureurById(bestas[4]).get(0).getNom_equipe() + "(" + String.valueOf(pitstop) + "s)");

                dialog.show();
            }
        });


        // navbar code
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Ouvrir, R.string.Fermer);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.coureurs){

                    Intent gestion_coureurs = new Intent(courses.this, gestion_coureurs.class);
                    startActivity(gestion_coureurs);
                }
                if(id == R.id.equipes){

                    Intent gestion_equipes = new Intent(courses.this, gestion_equipes.class);
                    startActivity(gestion_equipes);
                }
                if(id == R.id.course){

                    Intent courses = new Intent(courses.this, courses.class);
                    startActivity(courses);
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}