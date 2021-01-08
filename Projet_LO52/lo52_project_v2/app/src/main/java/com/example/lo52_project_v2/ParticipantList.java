package com.example.lo52_project_v2;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.lo52_project_v2.model.bo.Participant;

import java.util.ArrayList;

public class ParticipantList implements Parcelable {

    public static ArrayList<Participant> participants;

    public ParticipantList(ArrayList<Participant> in) {
        this.participants = in;
    }

    protected ParticipantList(Parcel in) {
        participants = in.createTypedArrayList(Participant.CREATOR);
    }

    public static final Creator<ParticipantList> CREATOR = new Creator<ParticipantList>() {
        @Override
        public ParticipantList createFromParcel(Parcel in) {
            return new ParticipantList(in);
        }

        @Override
        public ParticipantList[] newArray(int size) {
            return new ParticipantList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(participants);
    }

}
