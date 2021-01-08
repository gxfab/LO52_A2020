package com.example.lo52_project_v2.model.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity(primaryKeys = {"id","idEquipe"})

public class ParticipantEquipe implements Parcelable, Comparable<ParticipantEquipe>{
    public ParticipantEquipe(int id, int idEquipe, int ordreDePassage){
        this.id = id;
        this.idEquipe = idEquipe;
        this.ordreDePassage = ordreDePassage;
    }

    //idParticipants
    public int id;
    public int idEquipe;
    public int ordreDePassage;

    //methods used for parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    protected ParticipantEquipe(Parcel in) {
        this.id = in.readInt();
        this.idEquipe = in.readInt();
        this.ordreDePassage = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idEquipe);
        dest.writeInt(this.ordreDePassage);

    }

    public static final Creator<ParticipantEquipe> CREATOR = new Creator<ParticipantEquipe>() {
        @Override
        public ParticipantEquipe createFromParcel(Parcel in) {
            return new ParticipantEquipe(in);
        }

        @Override
        public ParticipantEquipe[] newArray(int size) {
            return new ParticipantEquipe[size];
        }
    };

    //methods used for sorting
    @Override
    public int compareTo(ParticipantEquipe f) {

        if (ordreDePassage > f.ordreDePassage) {
            return 1;
        }
        else if (ordreDePassage<  f.ordreDePassage) {
            return -1;
        }
        else {
            return 0;
        }

    }
}
