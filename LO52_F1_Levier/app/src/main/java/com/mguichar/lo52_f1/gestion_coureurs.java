package com.mguichar.lo52_f1;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gestion_coureurs extends AppCompatActivity {

    // Navbar variables
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    // Activity variables
    EditText nom, prenom, niveau;
    Button add, clear, generate;
    RecyclerView recyclerView;

    List<Coureur> list = new ArrayList<>();
    List<Equipe> eList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    CoureurAdapter coureurAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_coureurs);


        // Navbar code
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

                    Intent gestion_coureurs = new Intent(gestion_coureurs.this, gestion_coureurs.class);
                    startActivity(gestion_coureurs);
                }
                if(id == R.id.equipes){

                    Intent gestion_equipes = new Intent(gestion_coureurs.this, gestion_equipes.class);
                    startActivity(gestion_equipes);
                }
                if(id == R.id.course){

                    Intent courses = new Intent(gestion_coureurs.this, courses.class);
                    startActivity(courses);
                }

                return true;
            }
        });


        // Coureurs code
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        niveau = findViewById(R.id.niveau);
        add = findViewById(R.id.ajouter);
        clear = findViewById(R.id.vider);
        generate = findViewById(R.id.generate);
        recyclerView = findViewById(R.id.recycler_coureurs_view);


        database = RoomDB.getInstance(this);
        list = database.coureurDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        coureurAdapter = new CoureurAdapter(gestion_coureurs.this, list);
        recyclerView.setAdapter(coureurAdapter);

        add.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               String sNom = nom.getText().toString().trim();
               String sPrenom = prenom.getText().toString().trim();
               int sNiveau = Integer.parseInt(niveau.getText().toString().trim());

               if(!sNom.equals("") && !sPrenom.equals("") && sNiveau > 0){
                   Coureur coureur = new Coureur();
                   coureur.setNom(sNom);
                   coureur.setPrenom(sPrenom);
                   coureur.setNiveau(sNiveau);

                   database.coureurDao().insert(coureur);
                   nom.setText("");
                   prenom.setText("");
                   niveau.setText("");
                   list.clear();
                   list.addAll(database.coureurDao().getAll());
                   coureurAdapter.notifyDataSetChanged();
               }
           }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                eList = database.equipeDao().getAll();
                database.coureurDao().reset(list);
                database.equipeDao().reset(eList);

                eList.clear();
                list.clear();

                eList.addAll(database.equipeDao().getAll());
                list.addAll(database.coureurDao().getAll());
                coureurAdapter.notifyDataSetChanged();
            }
        });

        generate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                database.coureurDao().reset(list);
                list.clear();
                list.addAll(database.coureurDao().getAll());
                coureurAdapter.notifyDataSetChanged();

                /* ici on va isérer tous les 30 coureurs */
                for(int i = 0; i < 30; ++i)
                    database.coureurDao().insert(new Coureur("Nom" + i, "Prenom" + i, generateRandomLevel()));

                list.addAll(database.coureurDao().getAll());
                coureurAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public int generateRandomLevel(){

        Random r = new Random();
        return r.nextInt((3 - 1) + 1) + 1;
    }

}