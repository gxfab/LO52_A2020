package com.example.lo52_f1;

import java.io.Serializable;
import java.util.ArrayList;

public class Runner implements Serializable {

    public String firstName;
    public String lastName;
    public int level;
    private ArrayList<Long> timeList = new ArrayList<Long>();

    public ArrayList<Long> getTimeList() { return timeList; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getLevel() {
        return level;
    }

    Runner(String firstName, String lastName, int level){
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
    }

    public long runnerTime() {
        long res = 0;
        for(int j = 0; j<5; j++)
            res += timeList.get(j);
        return res;
    }

    long bestTime(int type){
        long res=0;
        switch (type) {
            case 0: //best sprint
                if(timeList.get(0) < timeList.get(3))
                    res = timeList.get(0);
                else
                    res = timeList.get(0);
                break;
            case 1: //best obstacle
                if(timeList.get(1) < timeList.get(4))
                    res = timeList.get(1);
                else
                    res =  timeList.get(4);
                break;
            case 2: // best pit-stop
                res = timeList.get(2);
                break;
            case 3:
                res = runnerTime(); // best lap so this runner time
            break;
            default:
                break;
        }
        return res;
    }
}

