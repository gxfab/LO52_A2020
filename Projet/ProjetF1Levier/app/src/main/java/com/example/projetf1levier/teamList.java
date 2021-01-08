package com.example.projetf1levier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class teamList implements Serializable {

    ArrayList<team> m_listOfTeam;
    ArrayList<player> m_listOfPlayer;

    int[] m_imageNum = {R.drawable.pict1, R.drawable.pict2, R.drawable.pict3};
    int[] m_imageStep = {R.drawable.sprint1, R.drawable.obstacle1, R.drawable.ravitaillement, R.drawable.sprint2, R.drawable.obstacle2};


    public teamList(PlayerScreen screen) {
        m_listOfTeam = new ArrayList<team>();
        m_listOfPlayer = new ArrayList<player>(30);
    }

    public long getTimeForPlayer(int team, int player, int step) {
        return m_listOfTeam.get(team).getChronoPlayer((player + 1) * 3 + step);
    }

    public long getTotaltimeForplayer(int team, int player) {
        long time = 0L;
        time += m_listOfTeam.get(team).getChronoPlayer((player) * 5 + 0);
        time += m_listOfTeam.get(team).getChronoPlayer((player) * 5 + 1);
        time += m_listOfTeam.get(team).getChronoPlayer((player) * 5 + 2);
        time += m_listOfTeam.get(team).getChronoPlayer((player) * 5 + 3);
        time += m_listOfTeam.get(team).getChronoPlayer((player) * 5 + 4);

        return time;
    }

    public int getImageNum(int n) {
        return m_imageNum[n];
    }

    public int getImageStep(int n) {
        return m_imageStep[n];
    }

    public void addPlayer(player _p) {
        m_listOfPlayer.add(_p);
    }

    public void addPlayer(String _name, String _firstName, int _level) {
        addPlayer(new player(_name, _firstName, _level));
    }

    public void removeplayer(int id) {
        m_listOfPlayer.remove(id);
    }

    public int getNbPlayer() {
        return m_listOfPlayer.size();
    }


    public void makeTeam() {

        double sum = 0;
        for (int i = 0; i < m_listOfPlayer.size(); i++) {
            sum = sum + m_listOfPlayer.get(i).getLevel();
        }


        int nbPlayer = getNbPlayer();
        int nbTeam = (nbPlayer % 3 == 0) ? nbPlayer / 3 : nbPlayer / 3 + 1;

        Collections.sort(m_listOfPlayer);

        for (int i = 0; i < nbTeam; i++) {
            m_listOfTeam.add(new team(i + 1));
        }

        int team = 0, i = 1, j = i;

        if(nbTeam==1)
        {
            i=0;//avoid bug when nbteam=0;
        }
        for (int p = 0; p < nbPlayer; p++) {
            m_listOfTeam.get(team).addPlayer(m_listOfPlayer.get(p));
            team = team + i;
            if (i == 0 && nbTeam!=1) {
                i = -j;
                j = i;
            } else if (team == nbTeam - 1 || team == 0) {
                i = 0;
            }
        }
    }

    public ArrayList<team> getListOfTeam() {
        return m_listOfTeam;
    }

    public int getNbTeam() {
        return m_listOfTeam.size();
    }

    public int getNumberTeamFinish(){
        int numberTeamFinish=0;
        for (int t=0;t<m_listOfTeam.size();t++)
        {
            if(m_listOfTeam.get(t).getFinishRun())
            {
                numberTeamFinish++;
            }
        }
        return numberTeamFinish;
    }



}


