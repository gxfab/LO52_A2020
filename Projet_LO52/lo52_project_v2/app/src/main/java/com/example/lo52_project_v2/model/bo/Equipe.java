package com.example.lo52_project_v2.model.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipe implements Parcelable {

    public Equipe(String nomEquipe){
        this.nomEquipe = nomEquipe;
    }

    @PrimaryKey(autoGenerate = true)
    public int idEquipe;

    @ColumnInfo(name="nomEquipe")
    public String nomEquipe;

    @ColumnInfo(name="rang")//Resultat de la course
    public int rang;

    public int idCourse;
    public long dureeParcours;

    //methods used for parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    protected Equipe(Parcel in) {
        this.idEquipe = in.readInt();
        this.nomEquipe = in.readString();
        this.rang = in.readInt();
        this.idCourse = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idEquipe);
        dest.writeString(this.nomEquipe);
        dest.writeInt(this.rang);
        dest.writeInt(this.idCourse);
    }

    public static final Creator<Equipe> CREATOR = new Creator<Equipe>() {
        @Override
        public Equipe createFromParcel(Parcel in) {
            return new Equipe(in);
        }

        @Override
        public Equipe[] newArray(int size) {
            return new Equipe[size];
        }
    };
}
