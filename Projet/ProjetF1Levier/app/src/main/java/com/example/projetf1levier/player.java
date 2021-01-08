package com.example.projetf1levier;

import java.io.Serializable;

/*
*Class player
* have name, firstName and level
 */
public class player implements Comparable , Serializable {
    String m_name;
    String m_firstName;
    int m_level;


    /*
    *Constructor
    * take name firstName and level of the player
     */
    public player(String _name, String _firstName, int _level)
    {
        m_name=_name;
        m_firstName=_firstName;
        m_level=_level;
    }

    /*
    *getter
     */
    public int getLevel() {
        return m_level;
    }
    public String getName() {
        return m_name;
    }
    public String getFullName()
    {
        return getName() + " - " + getFirstName() +" - " +m_level;
    }
    public String getFirstName() {
        return m_firstName;
    }





    @Override
    public int compareTo(Object p) {
        int comparLevel=((player)p).getLevel();
        /* For Ascending order*/
        return this.m_level-comparLevel;
    }
}
