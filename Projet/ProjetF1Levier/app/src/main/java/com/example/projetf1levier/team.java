package com.example.projetf1levier;

import java.io.Serializable;
import java.util.ArrayList;

/*
 *team class
 * have list of player
 */
public class team implements Serializable {
    ArrayList<player> m_playerList;

    int m_teamNumber;

    int m_numberPlayerRun;
    int m_numberStepRun;
    long m_chronoTeam;
    long m_pastChrono;

    Boolean finishRun;

    ArrayList<Long> m_chronoPlayer;

    /*
     *Constructor
     */
    public team(int teamNumber) {
        m_playerList = new ArrayList<player>(3);

        m_teamNumber = teamNumber;

        m_numberPlayerRun = 0;

        finishRun = Boolean.FALSE;

        m_chronoPlayer = new ArrayList<Long>();
    }

    public void addPlayer(player p) {
        m_playerList.add(p);
    }

    public void nextStepRun() {

        if (m_numberStepRun == 4) {
            if (m_numberPlayerRun == 2) {
                finishRun = true;
            } else {
                m_numberPlayerRun++;
                m_numberStepRun = 0;
            }

        } else {
            m_numberStepRun++;
        }
    }

    public void addChrono(long _chrono) {
        m_chronoPlayer.add(_chrono - m_pastChrono);
        m_pastChrono = _chrono;
    }

    public long getChronoPlayer(int _step) {
        return m_chronoPlayer.get(_step);
    }

    public void setCurrentTime(Long t) {
        m_pastChrono = t;
    }

    public void changeOrder(int first, int second) {
        if (first <= second) {
            second++;
        }
        ArrayList<player> newPlayerList = new ArrayList<player>();
        newPlayerList.add(m_playerList.get(first));
        newPlayerList.add(m_playerList.get(second));
        newPlayerList.add(m_playerList.get(3 - (first + second)));
        m_playerList = newPlayerList;
    }

    /*
     *getter
     */
    public Boolean getFinishRun() {
        return finishRun;
    }

    public int getTeamNumber() {
        return m_teamNumber;
    }

    public ArrayList<player> getPlayerList() {
        return m_playerList;
    }

    public int getNumberPlayerRun() {
        return m_numberPlayerRun;
    }

    public int getNumberStepRun() {
        return m_numberStepRun;
    }

}
