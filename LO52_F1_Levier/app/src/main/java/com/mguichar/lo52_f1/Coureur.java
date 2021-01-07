package com.mguichar.lo52_f1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "coureurs")
public class Coureur implements Serializable {

    public Coureur(){}

    @PrimaryKey(autoGenerate = true)
    private int coureur_ID;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "nom_equipe")
    private String nom_equipe;

    @ColumnInfo(name = "niveau")
    private int niveau;

    @ColumnInfo(name = "sprint1")
    private int sprint1;
    @ColumnInfo(name = "sprint2")
    private int sprint2;
    @ColumnInfo(name = "obstacle1")
    private int obstacle1;
    @ColumnInfo(name = "obstacle2")
    private int obstacle2;
    @ColumnInfo(name = "pitstop")
    private int pitstop;
    @ColumnInfo(name = "hasrun")
    private boolean hasRun;

    public Coureur(String nom, String prenom, int niveau){

        this.nom = nom;
        this.prenom =prenom;
        this.niveau = niveau;
        this.sprint1 = 0;
        this.sprint2 = 0;
        this.pitstop = 0;
        this.obstacle1 = 0;
        this.obstacle2 = 0;
        this.hasRun = false;
    }

    public void setHasRun(boolean hasrun){

        this.hasRun = hasrun;
    }

    public boolean getHasRun(){

        return this.hasRun;
    }

    public String getNom_equipe() {
        return nom_equipe;
    }

    public void setNom_equipe(String nom_equipe) {
        this.nom_equipe = nom_equipe;
    }

    public int getSprint1() {
        return sprint1;
    }

    public void setSprint1(int sprint1) {
        this.sprint1 = sprint1;
    }

    public int getSprint2() {
        return sprint2;
    }

    public void setSprint2(int sprint2) {

        this.sprint2 = sprint2;
    }

    public int getObstacle1() {
        return obstacle1;
    }

    public void setObstacle1(int obstacle1) {

        // to get the real time and not only the time since the timer has started
        this.obstacle1 = obstacle1;
    }

    public int getObstacle2() {
        return obstacle2;
    }

    public void setObstacle2(int obstacle2) {

        this.obstacle2 = obstacle2;
    }

    public int getPitstop() {
        return pitstop;
    }

    public void setPitstop(int pitstop) {

        this.pitstop = pitstop;
    }

    public int getTotalTime(){
        return sprint1 + sprint2 + obstacle1 + obstacle2 + pitstop;
    }


    public int getCoureur_ID() {
        return coureur_ID;
    }

    public void setCoureur_ID(int coureur_ID) {
        this.coureur_ID = coureur_ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getFullName(){

        return prenom + " " + nom;
    }
}
