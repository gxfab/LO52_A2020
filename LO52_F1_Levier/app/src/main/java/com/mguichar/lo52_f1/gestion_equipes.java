package com.mguichar.lo52_f1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class gestion_equipes extends AppCompatActivity {
    // Navbar variables
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    // Activity variables
    RecyclerView recyclerView;
    Button former, clear;

    List<Equipe> list = new ArrayList<>();
    List<Coureur> cList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    EquipeAdapter equipeAdapter;
    CoureurAdapter coureurAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_equipes);


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

                    Intent gestion_coureurs = new Intent(gestion_equipes.this, gestion_coureurs.class);
                    startActivity(gestion_coureurs);
                }
                if(id == R.id.equipes){

                    Intent gestion_equipes = new Intent(gestion_equipes.this, gestion_equipes.class);
                    startActivity(gestion_equipes);
                }
                if(id == R.id.course){

                    Intent courses = new Intent(gestion_equipes.this, courses.class);
                    startActivity(courses);
                }

                return true;
            }
        });


        // Equipes code

        former = findViewById(R.id.former);
        clear = findViewById(R.id.clear);
        recyclerView = findViewById(R.id.recycler_equipes_view);

        database = RoomDB.getInstance(this);

        list = database.equipeDao().getAll();
        cList = database.coureurDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        equipeAdapter = new EquipeAdapter(gestion_equipes.this, list);
        coureurAdapter = new CoureurAdapter(gestion_equipes.this, cList);
        recyclerView.setAdapter(equipeAdapter);

        former.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



                /*Coureur c1 = cList.get(0);
                Coureur c2 = cList.get(1);
                Coureur c3 = cList.get(2);
                Coureur c4 = cList.get(3);
                Coureur c5 = cList.get(4);
                Coureur c6 = cList.get(5);*/

                /*database.equipeDao().insert(new Equipe("Equipe 1", c1.getCoureur_ID(), c2.getCoureur_ID(), c3.getCoureur_ID()));
                database.equipeDao().insert(new Equipe("Equipe 2", c4.getCoureur_ID(), c5.getCoureur_ID(), c6.getCoureur_ID()));

                database.coureurDao().setEquipeName(c1.getCoureur_ID(), "Equipe 1");
                database.coureurDao().setEquipeName(c2.getCoureur_ID(), "Equipe 1");
                database.coureurDao().setEquipeName(c3.getCoureur_ID(), "Equipe 1");
                database.coureurDao().setEquipeName(c4.getCoureur_ID(), "Equipe 2");
                database.coureurDao().setEquipeName(c5.getCoureur_ID(), "Equipe 2");
                database.coureurDao().setEquipeName(c6.getCoureur_ID(), "Equipe 2");*/

                if(list.isEmpty()) {

                    generateEquipe();

                    /*list.clear();
                    list.addAll(database.equipeDao().getAll());
                    equipeAdapter.notifyDataSetChanged();

                    cList.clear();
                    cList.addAll(database.coureurDao().getAll());
                    coureurAdapter.notifyDataSetChanged();*/
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                database.equipeDao().reset(list);
                list.clear();
                list.addAll(database.equipeDao().getAll());
                equipeAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public static ArrayList<Coureur> getMax(ArrayList<Coureur> set1, ArrayList<Coureur> set2, ArrayList<Coureur> set3){

        int max = Math.max(set1.size(), Math.max(set2.size(), set3.size()));
        if(max == set1.size())
            return set1;
        else if(max == set2.size())
            return set2;
        else
            return set3;
    }

    public void generateEquipe(){

        RoomDB database = RoomDB.getInstance(this);;

        //List<Equipe> list = database.equipeDao().getAll();
        //List<Coureur> cList = database.coureurDao().getAll();

        ArrayList<Coureur> level1 = new ArrayList<Coureur>();
        ArrayList<Coureur> level2 = new ArrayList<Coureur>();
        ArrayList<Coureur> level3 = new ArrayList<Coureur>();

        for(int i = 0; i < cList.size(); ++i){
            switch(cList.get(i).getNiveau()){
                case 1:
                    level1.add(cList.get(i));
                    break;
                case 2:
                    level2.add(cList.get(i));
                    break;
                case 3:
                    level3.add(cList.get(i));
                    break;
                default:
                    System.out.println("An error occured");
            }
        }

        int number_equipes = cList.size() / 3;
        int n = 3; // nombre de coureurs par équipe
        int a = level1.size();
        int b = level2.size();
        int c = level3.size();

        //System.out.println(a + " " + b +" " + c + " " + number_equipes);

        ArrayList<Coureur>[] equipes  = new ArrayList[number_equipes];
        for(int i = 0; i < number_equipes; ++i)
            equipes[i] = new ArrayList<Coureur>();

        /*ArrayList<Integer>set1 = new ArrayList<Integer>();
        ArrayList<Integer>set2 = new ArrayList<Integer>();
        ArrayList<Integer>set3 = new ArrayList<Integer>();

        for(int i = 0; i < a; ++i)
            set1.add(1);
        for(int i = 0; i < b; ++i)
            set2.add(2);
        for(int i = 0; i < c; ++i)
            set3.add(3);*/

        while(!getMax(level1, level2, level3).isEmpty()){

            int r = 0;
            while(!level3.isEmpty()){
                equipes[r].add(level3.get(0));
                level3.remove(0);
                ++r;

                if(r == number_equipes)
                    r = 0;
                System.out.println("taille " + level3.size());
                //System.out.println(rep[0].size() + " " + rep[1].size() + " " + rep[2].size());
            }
            int j = 0;
            while(!level1.isEmpty()){
                System.out.println("boucle 2 et taille " + level1.size());

                if(equipes[j].isEmpty()){
                    if(!level1.isEmpty()){
                        equipes[j].add(level1.get(0));
                        level1.remove(0);
                    }
                    else if(!level2.isEmpty()){
                        equipes[j].add(level2.get(0));
                        level2.remove(0);
                    }
                }
                else if(equipes[j].get(0).getNiveau() == 3 && !level1.isEmpty() && equipes[j].size() < 3){
                    equipes[j].add(level1.get(0));
                    level1.remove(0);
                }
                else if(equipes[j].get(0).getNiveau() == 1 && equipes[j].size() < 3){
                    equipes[j].add(level1.get(0));
                    level1.remove(0);
                }
                ++j;

                if(j == number_equipes)
                    j = 0;

            }
            j = 0;
            while(!level2.isEmpty()){
                if(equipes[j].size() < 3){
                    equipes[j].add(level2.get(0));
                    level2.remove(0);
                }
                ++j;

                if(j == number_equipes)
                    j = 0;
                System.out.println("boucle 3");
            }
        }


        for(int i = 0; i < number_equipes; ++i){
            database.equipeDao().insert(new Equipe("Equipe " + i, equipes[i].get(0).getCoureur_ID(), equipes[i].get(1).getCoureur_ID(), equipes[i].get(2).getCoureur_ID()));
        }

        for(int i = 0; i < number_equipes; ++i){
            for(int j = 0; j < n; ++j){
                database.coureurDao().setEquipeName(equipes[i].get(j).getCoureur_ID(), "Equipe " + i);
            }
        }

        cList.clear();
        cList.addAll(database.coureurDao().getAll());
        coureurAdapter.notifyDataSetChanged();

        list.clear();
        list.addAll(database.equipeDao().getAll());
        equipeAdapter.notifyDataSetChanged();
    }
}
