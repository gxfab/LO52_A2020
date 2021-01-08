package com.example.lo52_f1;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
    public char name;
    public ArrayList<Runner> runner = new ArrayList<Runner>();
    public int position;

    public int getPosition() {  return position; }
    public void setPosition(int position) { this.position = position; }

    public char getName() { return name; }
    public ArrayList<Runner> getRunner() {
        return runner;
    }

    public Team(char name, Runner runner1, Runner runner2, Runner runner3) {
        this.name = name;
        this.runner.add(runner1);
        this.runner.add(runner2);
        this.runner.add(runner3);
        this.position = 0;
    }

    public Team(char name, Runner runner1, Runner runner2, Runner runner3, int position) {
        this.name = name;
        this.runner.add(runner1);
        this.runner.add(runner2);
        this.runner.add(runner3);
        this.position = position;
    }

    public void orderChange(int order){
        Runner tmp;
        switch (order) {
            case 0: // 1 - 3 - 2
                tmp = runner.get(1);
                runner.remove(1);
                runner.add(tmp);
                break;
            case 1: // 2 - 1 - 3
                tmp = runner.get(0);
                runner.remove(0);
                runner.add(tmp);
                tmp = runner.get(1);
                runner.remove(1);
                runner.add(tmp);
                break;
            case 2: // 2 - 3 - 1
                tmp = runner.get(0);
                runner.remove(0);
                runner.add(tmp);
                break;
            case 3: // 3 - 1 - 2
                tmp = runner.get(0);
                runner.remove(0);
                runner.add(tmp);
                tmp = runner.get(0);
                runner.remove(0);
                runner.add(tmp);
                break;
            case 4: // 3 - 2 - 1
                tmp = runner.get(1);
                runner.remove(1);
                runner.add(tmp);
                tmp = runner.get(0);
                runner.remove(0);
                runner.add(tmp);
                break;
            default:
                break;
        }
    }

    public long teamTime() {
        long res = 0;
        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0; j<5; j++) {
                res += this.getRunner().get(i).getTimeList().get(j);
            }
        }
        return res;
    }

    Runner fastestRunner(int type){
        long time = runner.get(0).bestTime(type);
        Runner r = runner.get(0);
        if(time > runner.get(1).bestTime(type)) {
            time = runner.get(1).bestTime(type);
            r = runner.get(1);
        }
        if(time > runner.get(2).bestTime(type))
            return runner.get(2);
        else
            return r;
    }
}