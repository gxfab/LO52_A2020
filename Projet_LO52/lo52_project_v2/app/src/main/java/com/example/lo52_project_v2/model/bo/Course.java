package com.example.lo52_project_v2.model.bo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course implements Parcelable {

    public Course(String nomCourse, String date){
        this.nomCourse = nomCourse;
        this.date = date;
    }

    @PrimaryKey(autoGenerate = true)
    public int idCourse;

    @ColumnInfo(name="nomCourse")
    public String nomCourse;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "finie")
    public boolean finie = false;

    //methods used for parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    protected Course(Parcel in) {
        this.idCourse = in.readInt();
        this.nomCourse = in.readString();
        this.date = in.readString();
        this.finie = (in.readInt()==1);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idCourse);
        dest.writeString(this.nomCourse);
        dest.writeString(this.date);
        if(this.finie==true){
            dest.writeInt(1);
        }
        else{
            dest.writeInt(0);
        }


    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

}
