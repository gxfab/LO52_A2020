package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.EquipeParticipants;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.bo.Tour;
import com.example.lo52_project_v2.model.dao.AppDataBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class start_race_activity extends AppCompatActivity {

    GridView teams_grid;
    Chronometer chrono;
    Button start_chrono_btn;
    Boolean running = false;
    private long lastPause;

    Course current_race;

    List<Equipe> allTeams;
    List<EquipeParticipants> allParticipants;
    List<Pair<Integer,Integer>> current_teams_participant ;

    List<List<Tour>> participantstats;

    ArrayList<Boolean> itemClickable =new ArrayList<Boolean> ();

    private int finishedTeams =0;

    private String[] etapes = {
            "Sprint sans obstacle",
            "Sprint avec obstacle",
            "pitstop",
            "sprint SO",
            "sprint AO"
    };

    private int[] mImageIds = {
            R.drawable.ic_run_24,
            R.drawable.ic_obstacle,
            R.drawable.ic_pitstop_r,
            R.drawable.ic_run_24,
            R.drawable.ic_obstacle
    };

    int cur_Time =0;
    List<Integer> prev_Time ;

    MyArrayAdapter<Equipe> teams_gridview_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_race_activity);

        teams_grid = findViewById(R.id.running_teams_gv);

        chrono = findViewById(R.id.mainChronometer);
        chrono.setBase(SystemClock.elapsedRealtime());
        cur_Time=0;

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                cur_Time = cur_Time+1;
            }
        });

        lastPause = SystemClock.elapsedRealtime();
        start_chrono_btn = findViewById(R.id.start_chronometer_btn);

        teams_grid.setNumColumns(4);

        Intent i = getIntent();
        Bundle data = i.getExtras();

        current_race = (Course) data.getParcelable("selected_race") ;

        AppDataBase db = AppDataBase.getInstance(this);


        EquipeParticipantsWrapper correspondingTeamParticipantsContainer = new EquipeParticipantsWrapper();

        TeamsWrapper correspondingTeamsContainer = new TeamsWrapper();

        current_teams_participant = new ArrayList<Pair<Integer,Integer>>();
        participantstats = new ArrayList<List<Tour>>();
        ParticipantEquipeWrapper participantOrderContainer = new ParticipantEquipeWrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                correspondingTeamsContainer.teams = db.equipeDao().getCorrespondingTeams(current_race.idCourse);

                correspondingTeamParticipantsContainer.teams_participants = new ArrayList<EquipeParticipants>();
                prev_Time = new ArrayList<Integer>();

                for(Equipe cur_team : correspondingTeamsContainer.teams){
                    EquipeParticipants return_equipe = db.participantDao().getCorrespondingsParticipantsinOrder(cur_team.idEquipe);
                    participantOrderContainer.participantEquipe_crossRef = db.participantDao().getCorrespondingsParticipantsOrder(cur_team.idEquipe);

                    List<ParticipantEquipe>currentOrder_participantsEquipe = participantOrderContainer.participantEquipe_crossRef;
                    Collections.sort(currentOrder_participantsEquipe);

                    List<Participant> sorted_currentOrder_participants = new ArrayList<Participant>();
                    for(ParticipantEquipe cur_participantEquipe : currentOrder_participantsEquipe){
                        for(Participant cur_Participant : return_equipe.participants){
                            if(cur_Participant.id==cur_participantEquipe.id){
                                sorted_currentOrder_participants.add(cur_Participant);
                            }
                        }
                    }

                    return_equipe.participants = sorted_currentOrder_participants;

                    correspondingTeamParticipantsContainer.teams_participants.add(return_equipe);
                    current_teams_participant.add(new Pair<Integer, Integer>(0,0));
                    participantstats.add(new ArrayList<Tour>());
                    prev_Time.add(0);
                }

            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        allTeams = correspondingTeamsContainer.teams;
        allParticipants = correspondingTeamParticipantsContainer.teams_participants;
        teams_gridview_adapter = getParticipantListAdapter(allTeams);

        teams_gridview_adapter.setAllItemsClickable(false);
        start_chrono_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(running==false){
                    chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    chrono.start();
                    teams_gridview_adapter.setAllItemsClickable(true);
                    running = true;
                    start_chrono_btn.setText("Stop");
                }
                else{
                    lastPause = SystemClock.elapsedRealtime();
                    chrono.stop();
                    teams_gridview_adapter.setAllItemsClickable(false);
                    running = false;
                    start_chrono_btn.setText("Resume");
                }

            }
        });

        teams_grid.setAdapter(teams_gridview_adapter);

        teams_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                Equipe current = allTeams.get(position);
                Participant current_runner = allParticipants.get(position).participants.get(current_teams_participant.get(position).first);

                ParticipantViewHolder viewHolder = new ParticipantViewHolder();
                viewHolder.tv_Roundnb=
                        (TextView)view.findViewById(R.id.round_number_tv);
                viewHolder.tv_ParticipantName=
                        (TextView)view.findViewById(R.id.participant_name_tv);
                viewHolder.iv_Icon=
                        (ImageView)view.findViewById(R.id.step_icon);


                if(current_teams_participant.get(position).second < etapes.length-1){
                    //cas où il reste des étapes

                    if(current_teams_participant.get(position).first==0 && current_teams_participant.get(position).second==0){
                        //cas ou il est le premier dans l'ordre de passage pour ne pas avoir a calculer le temp
                        participantstats.get(position).add(new Tour(etapes[current_teams_participant.get(position).second],cur_Time-prev_Time.get(position)-2,current_race.idCourse,current_runner.id));
                        prev_Time.set(position,cur_Time);
                    }
                    else{
                        //cas où il faut calculer le temp avec le précédent
                        participantstats.get(position).add(new Tour(etapes[current_teams_participant.get(position).second],
                                cur_Time-prev_Time.get(position),
                                current_race.idCourse,
                                current_runner.id));
                        prev_Time.set(position,cur_Time);
                    }
                    current_teams_participant.set(position,new Pair<Integer,Integer>(current_teams_participant.get(position).first,current_teams_participant.get(position).second+1));
                    viewHolder.iv_Icon.setImageResource(mImageIds[current_teams_participant.get(position).second]);
                    viewHolder.tv_Roundnb.setText(current_teams_participant.get(position).second.toString());

                }
                else {
                    //cas où on passe au joueur suivant
                    if(current_teams_participant.get(position).first < allParticipants.get(position).participants.size()-1){
                        //cas où il reste des coureurs dans l'équipe
                        if(current_teams_participant.get(position).first==0 && current_teams_participant.get(position).second==0){
                            //cas ou il est le premier dans l'ordre de passage pour ne pas avoir a calculer le temp
                            participantstats.get(position).add(new Tour(etapes[current_teams_participant.get(position).second],cur_Time-prev_Time.get(position),current_race.idCourse,current_runner.id));
                        }
                        else{
                            //cas où il faut calculer le temp avec le précédent
                            participantstats.get(position).add(new Tour(etapes[current_teams_participant.get(position).second],
                                    cur_Time-prev_Time.get(position),
                                    current_race.idCourse,
                                    current_runner.id));
                            prev_Time.set(position,cur_Time);
                        }

                        current_teams_participant.set(position,new Pair<Integer,Integer>(current_teams_participant.get(position).first+1,0));
                        Participant new_runner = allParticipants.get(position).participants.get(current_teams_participant.get(position).first);

                        viewHolder.iv_Icon.setImageResource(mImageIds[current_teams_participant.get(position).second]);
                        viewHolder.tv_Roundnb.setText(current_teams_participant.get(position).second.toString());
                        viewHolder.tv_ParticipantName.setText(new_runner.nomParticipant);

                    }
                    else{
                        //cas où l'équipe à terminer
                        participantstats.get(position).add(new Tour(etapes[current_teams_participant.get(position).second],
                                cur_Time-prev_Time.get(position),
                                current_race.idCourse,
                                current_runner.id));
                        prev_Time.set(position,cur_Time);

                        allTeams.get(position).rang= finishedTeams;

                        viewHolder.iv_Icon.setImageResource(mImageIds[current_teams_participant.get(position).second]);
                        viewHolder.tv_Roundnb.setText("End");
                        viewHolder.tv_ParticipantName.setText("");

                        teams_gridview_adapter.setItemClickable(position,false);

                        finishedTeams = finishedTeams+1;
                        if(finishedTeams==allTeams.size()){
                            //cas où toutes les teams ont finies
                            AppDataBase db = AppDataBase.getInstance(view.getContext());
                            current_race.finie=true;

                            Thread t = new Thread(new Runnable() {
                                //@Override
                                public void run() {

                                    for(List<Tour> equipe_tours : participantstats){
                                        db.tourDao().insertTour(equipe_tours);
                                    }
                                    db.equipeDao().update(allTeams);
                                    db.courseDao().update(current_race);

                                    Intent ires = new Intent(view.getContext(), start_race_activity.class);
                                    setResult(Activity.RESULT_OK, ires);
                                    finish();
                                }
                            });
                            t.start();

                            try {
                                t.join();
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });


    }

    static class ParticipantViewHolder{
        TextView tv_Roundnb;
        TextView tv_ParticipantName;
        ImageView iv_Icon;
    }

    MyArrayAdapter<Equipe> getParticipantListAdapter(List<Equipe> all_teams_List){
        return new MyArrayAdapter<Equipe>(this, 0, all_teams_List) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Participant current_runner = allParticipants.get(position).participants.get(current_teams_participant.get(position).first);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.gridview_item, null, false);
                }

                ParticipantViewHolder viewHolder = new ParticipantViewHolder();
                viewHolder.tv_Roundnb =
                        (TextView)convertView.findViewById(R.id.round_number_tv);
                viewHolder.tv_ParticipantName =
                        (TextView)convertView.findViewById(R.id.participant_name_tv);
                viewHolder.iv_Icon =
                        (ImageView) convertView.findViewById(R.id.step_icon);

                convertView.setTag(viewHolder);

                TextView tv_Roundnb =
                        ((ParticipantViewHolder)convertView.getTag()).tv_Roundnb;
                TextView tv_ParticipantName =
                        ((ParticipantViewHolder)convertView.getTag()).tv_ParticipantName;
                ImageView iv_Icon =
                        ((ParticipantViewHolder)convertView.getTag()).iv_Icon;

                tv_Roundnb.setText(current_teams_participant.get(position).second.toString());
                tv_ParticipantName.setText(current_runner.nomParticipant);
                iv_Icon.setImageResource(mImageIds[current_teams_participant.get(position).second]);

                return convertView;

            }
        };
    }

}

