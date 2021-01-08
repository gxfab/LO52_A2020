package com.example.lo52_project_v2.model.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Participant implements Parcelable {
    public Participant(String nomParticipant, Integer niveau){
        this.nomParticipant = nomParticipant;
        this.niveau = niveau;
    }

    @PrimaryKey(autoGenerate = true)
    public int id; /*ALL MODIFIERS ARE PUBLIC BECAUSE ROOM NEEDS ACCESS TO THESE FIELDS.
                    OR YOU CAN ADD GETTERS AND SETTERS ACCORDING TO JAVABEANS CONVENTION*/

    @ColumnInfo(name="nomParticipant")
    public String nomParticipant;

    @ColumnInfo(name="niveau")
    public Integer niveau;

    //methods used for parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    protected Participant(Parcel in) {
        this.id = in.readInt();
        this.nomParticipant = in.readString();
        this.niveau = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nomParticipant);
        dest.writeInt(this.niveau);
    }

    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };
}
