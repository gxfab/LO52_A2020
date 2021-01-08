package com.example.lo52_project_v2;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

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

public class show_stats_ativity extends AppCompatActivity {

    AppDataBase db;
    List<Equipe> equipes;
    GridView teams_grid;
    ListView team_detail;
    ArrayAdapter<Equipe> teams_gridview_adapter;

    List<Equipe> allTeams;
    List<EquipeParticipants> allParticipants;

    Course current_race;

    List<ArrayAdapter<Participant>> team_participants_adapters;

    Equipe selectedTeam;

    List<List<Integer>> stats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stats_ativity);
        teams_grid = findViewById(R.id.course_state);
        teams_grid.setNumColumns(2);
        team_detail = findViewById(R.id.team_detail_lv);

        int idCourse = (int)getIntent().getSerializableExtra("selected_race_id");

        db = AppDataBase.getInstance(this);

        EquipeParticipantsWrapper correspondingTeamParticipantsContainer = new EquipeParticipantsWrapper();
        TeamsWrapper correspondingTeamsContainer = new TeamsWrapper();
        RaceWrapper correspondingRaceContainer = new RaceWrapper();
        ParticipantEquipeWrapper participantOrderContainer = new ParticipantEquipeWrapper();
        stats = new ArrayList<List<Integer>>();

        Thread t = new Thread(new Runnable() {
            public void run() {
                equipes = db.equipeDao().getCorrespondingTeams(idCourse);
                correspondingRaceContainer.race = db.courseDao().getCourse(idCourse);
                correspondingTeamsContainer.teams = db.equipeDao().getCorrespondingTeams(correspondingRaceContainer.race.idCourse);
                correspondingTeamParticipantsContainer.teams_participants = new ArrayList<EquipeParticipants>();


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
                }

                for(EquipeParticipants cur_list : correspondingTeamParticipantsContainer.teams_participants){
                    List<Integer> adding = new ArrayList<Integer>();

                    for(Participant cur_part : cur_list.participants){
                        int cumul=0;
                        List<Tour> corr_Tours = db.tourDao().getCorr_Rounds(cur_part.id,correspondingRaceContainer.race.idCourse);
                        for(Tour tour : corr_Tours){
                            cumul = cumul + (int)tour.duree;
                        }
                        adding.add(cumul);
                    }
                    stats.add(adding);
                }
            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        current_race = correspondingRaceContainer.race;
        allTeams = correspondingTeamsContainer.teams;
        allParticipants = correspondingTeamParticipantsContainer.teams_participants;

        team_participants_adapters = new ArrayList<ArrayAdapter<Participant>>();

        for(int j=0;j<allParticipants.size();j++){
            team_participants_adapters.add( getParticipantListAdapter(allParticipants.get(j).participants));
        }

        teams_gridview_adapter = getEquipeListAdapter(equipes);
        teams_grid.setAdapter(teams_gridview_adapter);

        teams_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {
                selectedTeam = equipes.get(position);
                db = AppDataBase.getInstance(view.getContext());

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        List<Tour> ToursTest = db.tourDao().getCorrespondingsRounds(selectedTeam.idEquipe);
                    }
                });
                t.start();

                try {
                    t.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

    }

    static class EquipeViewHolder{
        TextView teamName;
        TextView teamRank;
        ListView participants;
    }

    static class ParticipantViewHolder{
        TextView name;
        TextView stat;
    }

    ArrayAdapter<Equipe> getEquipeListAdapter(List<Equipe> equipes){
        return new ArrayAdapter<Equipe>(this, 0, equipes) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Equipe current = equipes.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.show_stat_gv_item, null, false);
                }

                EquipeViewHolder viewHolder = new EquipeViewHolder();
                viewHolder.teamName = (TextView)convertView.findViewById(R.id.teamName);
                viewHolder.teamRank = (TextView)convertView.findViewById(R.id.teamRank);
                viewHolder.participants = (ListView) convertView.findViewById(R.id.participantsList);

                convertView.setTag(viewHolder);

                TextView teamName = ((EquipeViewHolder)convertView.getTag()).teamName;
                TextView teamRank = ((EquipeViewHolder)convertView.getTag()).teamRank;
                ListView teamParticipants = ((EquipeViewHolder)convertView.getTag()).participants;

                teamName.setText(current.nomEquipe);
                teamRank.setText(Integer.toString(current.rang));

                teamParticipants.setAdapter(team_participants_adapters.get(position));

                return convertView;
            }
        };
    }

    ArrayAdapter<Participant> getParticipantListAdapter(List<Participant> participantList){
        return new ArrayAdapter<Participant>(this, 0, participantList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {

                Participant current = participantList.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.participant_course_stats, null, false);
                }



                ParticipantViewHolder viewHolder = new ParticipantViewHolder();
                viewHolder.name =
                        (TextView)convertView.findViewById(R.id.stat_racer_name);
                viewHolder.stat =
                        (TextView)convertView.findViewById(R.id.stat_time);

                convertView.setTag(viewHolder);

                TextView tv_Name =
                        ((ParticipantViewHolder)convertView.getTag()).name;
                TextView tv_stat =
                        ((ParticipantViewHolder)convertView.getTag()).stat;

                int teamposition=0;

                for(int k=0;k<allParticipants.size();k++){
                    for(int l=0;l<allParticipants.get(k).participants.size();l++){
                        if(allParticipants.get(k).participants.get(l).id==current.id){
                            teamposition=k;
                        }
                    }
                }

                tv_Name.setText(current.nomParticipant);
                int total_secs =stats.get(teamposition).get(position);
                String format = DateUtils.formatElapsedTime((long)total_secs);
                tv_stat.setText(format);

                return convertView;
            }
        };
    }
}