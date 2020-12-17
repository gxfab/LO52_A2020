package BDD.entity;

import java.util.ArrayList;

public class Team {
    private ArrayList<Runner> _team;
    private int _level;
    private int _id;
    private int _nb_step;

    /*Builder*/
    public Team(int id, ArrayList<Runner>team, int lvl){
        this._team = team;
        this._level=lvl;
        this._id=id;
    }
    public Team(int id, Runner p1, Runner p2, Runner p3, int lvl){
        ArrayList<Runner> team = new ArrayList<Runner>();
        team .add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
        this._level=lvl;
        this._id=id;
    }
    public Team(int id, Runner p1, Runner p2, Runner p3){
        ArrayList<Runner> team = new ArrayList<Runner>();
        team.add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
        this._level=p1.getLevel()+p2.getLevel()+p3.getLevel();
        this._id=id;
        this._nb_step=0;
    }

    /*Getter & Setter*/
    public void setParticipants(ArrayList<Runner> team){
        this._team=team;
    }
    public void setParticipants(Runner p1, Runner p2, Runner p3){
        ArrayList<Runner> team = new ArrayList<Runner>();
        team .add(p1);
        team.add(p2);
        team.add(p3);
        this._team = team;
    }
    public ArrayList<Runner> getParticipants(){
        return _team;
    }
    public void setParticipant(int n_id, Runner p){
        getParticipants().get(n_id).setName(p.getName());
        getParticipants().get(n_id).setFirstName(p.getFirstName());
        getParticipants().get(n_id).setLevel(p.getLevel());
    }

    public void setLevel(int lvl){
        this._level=lvl;
    }

    public int getLevel(){
        return _level;
    }

    public void setId(int id){
        this._id=id;
    }

    public int getId(){
        return _id;
    }

    public void setNb_step(int step){
        this._nb_step=step;
    }

    public int getNb_step(){ return _nb_step;}
}
