package com.example.f1_levier;

import java.util.ArrayList;

public class Data {
    String name;
    int id_step;
    int id_team;
    int image_step;
    int image_person;

    public Data(String name, int id_team) {
        this.name = name;
        this.id_team = id_team;
        this.id_step = MyData.id_step[0];
        this.image_person=MyData.drawableArray[0];
        this.image_step=MyData.drawableArray[3];
    }

    public String getName() {
        return name;
    }

    public int getIdTeam() {
        return id_team;
    }

    public int getImagePerson() {
        return image_person;
    }

    public int getImageStep() {
        return image_step;
    }

    public int getIdStep() {
        return id_step;
    }
}

class MyData {

    static ArrayList<ArrayList<String>> nameArray;
    static int[] id_step = {1,2};

    static int[] drawableArray = {R.drawable.pf, R.drawable.ps, R.drawable.pt,R.drawable.wait,
            R.drawable.sprint, R.drawable.obstacle, R.drawable.pitstop,R.drawable.end};

    static int[] id_team = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
}
