package com.mguichar.lo52_f1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "equipes")
public class Equipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int equipe_ID;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "id_coureur1")
    private int id_coureur1;

    @ColumnInfo(name = "id_coureur2")
    private int id_coureur2;

    @ColumnInfo(name = "id_coureur3")
    private int id_coureur3;

    public Equipe(String nom, int id_coureur1, int id_coureur2, int id_coureur3) {
        this.nom = nom;
        this.id_coureur1 = id_coureur1;
        this.id_coureur2 = id_coureur2;
        this.id_coureur3 = id_coureur3;
    }

    public int getEquipe_ID() {
        return equipe_ID;
    }

    public void setEquipe_ID(int equipe_ID) {
        this.equipe_ID = equipe_ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_coureur1() {
        return id_coureur1;
    }

    public void setId_coureur1(int id_coureur1) {
        this.id_coureur1 = id_coureur1;
    }

    public int getId_coureur2() {
        return id_coureur2;
    }

    public void setId_coureur2(int id_coureur2) {
        this.id_coureur2 = id_coureur2;
    }

    public int getId_coureur3() {
        return id_coureur3;
    }

    public void setId_coureur3(int id_coureur3) {
        this.id_coureur3 = id_coureur3;
    }

    public int getCoureurById(int i){
        switch (i){

            case 0: return id_coureur1;
            case 1: return id_coureur2;
            case 2: return id_coureur3;
        }

        return 0;
    }
}
