package com.example.projetf1levier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Results extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    private teamList teams;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();

        teams = (teamList) intent.getSerializableExtra("teamList");

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.entityValues, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.stepValues, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ListView listView = (ListView) findViewById(R.id.results_view);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        ArrayList<String> affichageResult = null;
        listItems.clear();
        affichageResult = getTimeByTeam();

        for (int i = 0; i < affichageResult.size(); i++) {
            listItems.add(affichageResult.get(i));
        }

        adapter.notifyDataSetChanged();
    }

    public void validateclic(View view) {
        Spinner spinnerEntity = (Spinner) findViewById(R.id.spinner2);
        Spinner spinnerStep = (Spinner) findViewById(R.id.spinner4);

        ArrayList<String> affichageResult = null;
        listItems.clear();
        if (spinnerEntity.getSelectedItemPosition() == 0) {
            switch (spinnerStep.getSelectedItemPosition()) {
                case 0: //all course
                    affichageResult = getTimeByTeam();
                    break;
                case 1: //sprint1
                    affichageResult = getTimeBystepByTeam(0);
                    break;
                case 2: //obstacle1
                    affichageResult = getTimeBystepByTeam(1);
                    break;
                case 3: //ravitaillement
                    affichageResult = getTimeBystepByTeam(2);

                    break;
                case 4: //sprint2
                    affichageResult = getTimeBystepByTeam(3);

                    break;
                case 5: //obstacle2
                    affichageResult = getTimeBystepByTeam(4);

                    break;
                case 6: //tour1 (s1+o1)
                    affichageResult = getTimeByTeamTour1();
                    break;
                case 7: //tour1 (s2+o2)
                    affichageResult = getTimeByTeamTour2();

                    break;
            }
        } else {

            switch (spinnerStep.getSelectedItemPosition()) {
                case 0: //all course
                    affichageResult = getTimeByplayer();
                    break;
                case 1: //sprint1
                    affichageResult = getTimeBystep(0);
                    break;
                case 2: //obstacle1
                    affichageResult = getTimeBystep(1);
                    break;
                case 3: //ravitaillement
                    affichageResult = getTimeBystep(2);

                    break;
                case 4: //sprint2
                    affichageResult = getTimeBystep(3);

                    break;
                case 5: //obstacle2
                    affichageResult = getTimeBystep(4);

                    break;
                case 6: //tour1 (s1+o1)
                    affichageResult = getTimeByPlayerTour1();
                    break;
                case 7: //tour1 (s2+o2)
                    affichageResult = getTimeByPlayerTour2();
                    break;
            }

        }

        for (int i = 0; i < affichageResult.size(); i++) {
            listItems.add(affichageResult.get(i));
        }

        adapter.notifyDataSetChanged();


    }

    public ArrayList<String> getTimeByTeamTour1() {
        ArrayList<String> StringTimeByPlayer = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            time = teams.getTimeForPlayer(team, 0, 0);
            time += teams.getTimeForPlayer(team, 0, 1);

            time = teams.getTimeForPlayer(team, 1, 0);
            time += teams.getTimeForPlayer(team, 1, 1);
            time = teams.getTimeForPlayer(team, 2, 0);
            time += teams.getTimeForPlayer(team, 2, 1);


            Pair timeForPlayer = new Pair(time, " Equipe " + teams.getListOfTeam().get(team).m_teamNumber + " ");

            listTimeByStep.add(timeForPlayer);
        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByPlayer.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByPlayer;
    }

    public ArrayList<String> getTimeByTeamTour2() {
        ArrayList<String> StringTimeByPlayer = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            time = teams.getTimeForPlayer(team, 0, 3);
            time += teams.getTimeForPlayer(team, 0, 4);

            time = teams.getTimeForPlayer(team, 1, 3);
            time += teams.getTimeForPlayer(team, 1, 4);
            time = teams.getTimeForPlayer(team, 2, 3);
            time += teams.getTimeForPlayer(team, 2, 4);

            Pair timeForPlayer = new Pair(time, " Equipe " + teams.getListOfTeam().get(team).m_teamNumber + " ");

            listTimeByStep.add(timeForPlayer);
        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByPlayer.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByPlayer;
    }

    public ArrayList<String> getTimeByPlayerTour2() {
        ArrayList<String> StringTimeByPlayer = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            for (int player = 0; player < 3; player++) {
                time = teams.getTimeForPlayer(team, player, 3);
                time += teams.getTimeForPlayer(team, player, 4);
                player p = teams.getListOfTeam().get(team).getPlayerList().get(player);
                Pair timeForPlayer = new Pair(time, p.getFullName());

                listTimeByStep.add(timeForPlayer);
            }

        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByPlayer.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByPlayer;
    }

    public ArrayList<String> getTimeByPlayerTour1() {
        ArrayList<String> StringTimeByPlayer = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            for (int player = 0; player < 3; player++) {
                time = teams.getTimeForPlayer(team, player, 0);
                time += teams.getTimeForPlayer(team, player, 1);
                player p = teams.getListOfTeam().get(team).getPlayerList().get(player);
                Pair timeForPlayer = new Pair(time, p.getFullName());

                listTimeByStep.add(timeForPlayer);
            }

        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByPlayer.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByPlayer;
    }

    public ArrayList<String> getTimeByplayer() {
        ArrayList<String> StringTimeByPlayer = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            for (int player = 0; player < 3; player++) {
                time = teams.getTotaltimeForplayer(team, player);
                player p = teams.getListOfTeam().get(team).getPlayerList().get(player);
                Pair timeForPlayer = new Pair(time, p.getFullName());

                listTimeByStep.add(timeForPlayer);
            }
        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByPlayer.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByPlayer;
    }

    public ArrayList<String> getTimeByTeam() {
        ArrayList<String> StringTimeByStep = new ArrayList<String>();

        ArrayList<Pair> listTimeByTeam = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {

            time = teams.getTotaltimeForplayer(team, 0);
            time += teams.getTotaltimeForplayer(team, 1);
            time += teams.getTotaltimeForplayer(team, 2);

            Pair timeForTeam = new Pair(time, " Equipe " + teams.getListOfTeam().get(team).m_teamNumber + " ");

            listTimeByTeam.add(timeForTeam);
        }

        Collections.sort(listTimeByTeam, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByTeam.size(); taille++) {
            StringTimeByStep.add(taille + 1 + "-" + listTimeByTeam.get(taille).getSecond() + " - " + listTimeByTeam.get(taille).getFirst() / 1000 + ":" + listTimeByTeam.get(taille).getFirst() % 1000);
        }

        return StringTimeByStep;
    }

    public ArrayList<String> getTimeBystepByTeam(int step) {
        ArrayList<String> StringTimeByStep = new ArrayList<String>();

        ArrayList<Pair> listTimeByStepByTeam = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            time = teams.getTimeForPlayer(team, 0, step);
            time += teams.getTimeForPlayer(team, 1, step);
            time += teams.getTimeForPlayer(team, 2, step);

            Pair timeForTeam = new Pair(time, " Equipe " + teams.getListOfTeam().get(team).m_teamNumber + " ");

            listTimeByStepByTeam.add(timeForTeam);

        }


        Collections.sort(listTimeByStepByTeam, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStepByTeam.size(); taille++) {
            StringTimeByStep.add(taille + 1 + "-" + listTimeByStepByTeam.get(taille).getSecond() + " - " + listTimeByStepByTeam.get(taille).getFirst() / 1000 + ":" + listTimeByStepByTeam.get(taille).getFirst() % 1000);
        }


        //test bie
        return StringTimeByStep;
    }

    public ArrayList<String> getTimeBystep(int step) {
        ArrayList<String> StringTimeByStep = new ArrayList<String>();

        ArrayList<Pair> listTimeByStep = new ArrayList<Pair>();
        long time = 0;

        for (int team = 0; team < teams.getNbTeam(); team++) {
            for (int player = 0; player < 3; player++) {
                time = teams.getTimeForPlayer(team, player, step);
                player p = teams.getListOfTeam().get(team).getPlayerList().get(player);
                Pair timeForPlayer = new Pair(time, p.getFullName());

                listTimeByStep.add(timeForPlayer);
            }

        }

        Collections.sort(listTimeByStep, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return p1.first.compareTo(p2.first);
            }
        });

        for (int taille = 0; taille < listTimeByStep.size(); taille++) {
            StringTimeByStep.add(taille + 1 + "-" + listTimeByStep.get(taille).getSecond() + " - " + listTimeByStep.get(taille).getFirst() / 1000 + ":" + listTimeByStep.get(taille).getFirst() % 1000);
        }

        return StringTimeByStep;
    }

    private static class Pair {
        private Long first;
        private String second;

        public Pair(Long _first, String _second) {
            first = _first;
            second = _second;
        }

        public Long getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }
    }

}