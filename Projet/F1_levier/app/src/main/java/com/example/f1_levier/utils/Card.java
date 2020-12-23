package com.example.f1_levier.utils;

import com.example.f1_levier.R;

import java.util.ArrayList;

public class Card {
    String name;
    int id_step;
    int id_team;
    int image_step;
    int image_person;

    public Card(String name, int id_team) {
        this.name = name;
        this.id_team = id_team;
        this.id_step = ElementCard.id_step[0];
        this.image_person=ElementCard.drawableArray[0];
        this.image_step=ElementCard.drawableArray[3];
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


