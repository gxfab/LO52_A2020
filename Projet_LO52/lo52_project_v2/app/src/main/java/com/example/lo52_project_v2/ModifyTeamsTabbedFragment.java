package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.EquipeParticipants;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.dao.AppDataBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifyTeamsTabbedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyTeamsTabbedFragment extends Fragment {

    static class ParticipantViewHolder{
        TextView tv_Name;
        TextView tv_Level;
    }
    static class TeamViewHolder{
        TextView Team_Name_tv;
    }
    static class RaceViewHolder{
        TextView Race_Name_tv;
        TextView Race_Date_tv;
    }

    ListView Races_lv;
    ListView corresponding_teams_lv;
    ListView corresponding_members_lv;

    TextView corresponding_teams_tv;
    TextView corresponding_members_tv;

    Button modify_order_btn;

    List<Participant> corresponding_participants ;
    List<Equipe> corresponding_teams ;
    List<Course> races ;

    ArrayAdapter<Participant> corresponding_participants_Adapter;
    ArrayAdapter<Equipe> corresponding_teams_Adapter;
    ArrayAdapter<Course> races_Adapter;

    Course selected_race=null;
    Equipe selected_team=null;

    public static final String ARG_OBJECT = "Modify Team Order";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModifyTeamsTabbedFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ModifyTeamsTabbedFragment newInstance(String param1, String param2) {
        ModifyTeamsTabbedFragment fragment = new ModifyTeamsTabbedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_teams_tabbed, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Races_lv = view.findViewById(R.id.races_lv);
        corresponding_teams_lv=view.findViewById(R.id.correspondingTeams_lv);;
        corresponding_teams_tv=view.findViewById(R.id.correspondingTeams_tv);;
        corresponding_members_lv=view.findViewById(R.id.correspondingParticipants_lv);;
        corresponding_members_tv=view.findViewById(R.id.correspondingParticipants_tv);
        modify_order_btn=view.findViewById(R.id.modify_order_btn);

        Races_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                selected_race = races.get(position);
                selected_team = null;
                refresh_data();

            }
        });

        corresponding_teams_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                selected_team = corresponding_teams.get(position);
                refresh_data();

            }
        });

        modify_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ModifyTeamOrderActivity.class);
                ArrayList<Participant> tmp_corresponding_participants = new ArrayList<Participant>(corresponding_participants);
                //i.putParcelableArrayListExtra("correspondingParticipants_List",tmp_corresponding_participants);
                ParticipantList participants= new ParticipantList(tmp_corresponding_participants);
                i.putExtra("correspondingParticipants_List",participants);
                //i.putExtra("correspondingParticipants_List", tmp_corresponding_participants);
                i.putExtra("selected_team",selected_team);
                i.putExtra("selected_race",selected_race);
                startActivityForResult(i, 111);
            }
        });

        refresh_data();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 111 ) {
            if(resultCode!= Activity.RESULT_CANCELED){
                refresh_data();
            }
        }
    }

    public void refresh_data(){
        AppDataBase db = AppDataBase.getInstance(getView().getContext());
        RacesWrapper RacesContainer = new RacesWrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                RacesContainer.races = db.courseDao().getAllunFinished();
            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        races = RacesContainer.races;
        races_Adapter = getRaceListAdapter(races);
        Races_lv.setAdapter(races_Adapter);

        if(selected_race!=null){
            corresponding_teams_lv.setVisibility(View.VISIBLE);
            corresponding_teams_tv.setVisibility(View.VISIBLE);

            TeamsWrapper TeamsContainer = new TeamsWrapper();
            TeamsWrapper DummyTeamsContainer = new TeamsWrapper();

            t = new Thread(new Runnable() {
                public void run() {
                    TeamsContainer.teams = db.equipeDao().getCorrespondingTeams(selected_race.idCourse);
                    DummyTeamsContainer.teams = db.equipeDao().getAll();
                }
            });
            t.start();

            try {
                t.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            corresponding_teams = TeamsContainer.teams;
            corresponding_teams_Adapter = getTeamsListAdapter(corresponding_teams);
            corresponding_teams_lv.setAdapter(corresponding_teams_Adapter);

            if(selected_team!=null){
                corresponding_members_lv.setVisibility(View.VISIBLE);
                corresponding_members_tv.setVisibility(View.VISIBLE);
                modify_order_btn.setVisibility(View.VISIBLE);
                Wrapper participants_container = new Wrapper();

                t = new Thread(new Runnable() {
                    public void run() {
                        EquipeParticipants return_equipe = db.participantDao().getCorrespondingParticipants(selected_team.idEquipe);
                        participants_container.participants = return_equipe.participants;
                    }
                });
                t.start();

                try {
                    t.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                corresponding_participants = participants_container.participants;

                ParticipantEquipeWrapper participantOrderContainer = new ParticipantEquipeWrapper();

                t = new Thread(new Runnable() {
                    public void run() {
                        participantOrderContainer.participantEquipe_crossRef = db.participantDao().getCorrespondingsParticipantsOrder(selected_team.idEquipe);
                    }
                });
                t.start();

                try {
                    t.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                List<ParticipantEquipe> currentOrder_participantsEquipe = participantOrderContainer.participantEquipe_crossRef;
                Collections.sort(currentOrder_participantsEquipe);

                List<Participant> sorted_currentOrder_participants = new ArrayList<Participant>();
                for(ParticipantEquipe cur_participantEquipe : currentOrder_participantsEquipe){
                    for(Participant cur_Participant : corresponding_participants){
                        if(cur_Participant.id==cur_participantEquipe.id){
                            sorted_currentOrder_participants.add(cur_Participant);
                        }
                    }
                }

                corresponding_participants = sorted_currentOrder_participants;
                corresponding_participants_Adapter = getParticipantListAdapter(corresponding_participants);
                corresponding_members_lv.setAdapter(corresponding_participants_Adapter);

            }
            else{
                corresponding_members_lv.setVisibility(View.INVISIBLE);
                corresponding_members_tv.setVisibility(View.INVISIBLE);
                modify_order_btn.setVisibility(View.INVISIBLE);
            }
        }
        else{
            corresponding_teams_lv.setVisibility(View.INVISIBLE);
            corresponding_teams_tv.setVisibility(View.INVISIBLE);
            corresponding_members_lv.setVisibility(View.INVISIBLE);
            corresponding_members_tv.setVisibility(View.INVISIBLE);
            modify_order_btn.setVisibility(View.INVISIBLE);
        }
    }

    ArrayAdapter<Participant> getParticipantListAdapter(List<Participant> participantsList){
        return new ArrayAdapter<Participant>(getContext(), 0, participantsList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Participant current = corresponding_participants.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.participant_display, null, false);
                }

                ParticipantViewHolder viewHolder = new ParticipantViewHolder();
                viewHolder.tv_Name =
                        (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tv_Level =
                        (TextView)convertView.findViewById(R.id.tv_level);

                convertView.setTag(viewHolder);

                TextView tv_Name =
                        ((ParticipantViewHolder)convertView.getTag()).tv_Name;
                TextView tv_Level =
                        ((ParticipantViewHolder)convertView.getTag()).tv_Level;

                tv_Name.setText(current.nomParticipant);
                tv_Level.setText(current.niveau.toString());

                return convertView;
            }
        };
    }

    ArrayAdapter<Equipe> getTeamsListAdapter(List<Equipe> TeamList){
        return new ArrayAdapter<Equipe>(getContext(), 0, TeamList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Equipe current = corresponding_teams.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.team_item_display, null, false);
                }

                TeamViewHolder viewHolder = new TeamViewHolder();
                viewHolder.Team_Name_tv =
                        (TextView)convertView.findViewById(R.id.team_name_tv);

                convertView.setTag(viewHolder);

                TextView Team_Name_tv =
                        ((TeamViewHolder)convertView.getTag()).Team_Name_tv;

                Team_Name_tv.setText(current.nomEquipe);

                return convertView;

            }
        };
    }

    ArrayAdapter<Course> getRaceListAdapter(List<Course> raceList){
        return new ArrayAdapter<Course>(getContext(), 0, raceList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Course current = races.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.race_item_display, null, false);
                }

                RaceViewHolder viewHolder = new RaceViewHolder();
                viewHolder.Race_Name_tv =
                        (TextView)convertView.findViewById(R.id.race_name_tv);
                viewHolder.Race_Date_tv =
                        (TextView)convertView.findViewById(R.id.race_date_tv);

                convertView.setTag(viewHolder);

                TextView tv_RaceName =
                        ((RaceViewHolder)convertView.getTag()).Race_Name_tv;
                TextView tv_Race_date =
                        ((RaceViewHolder)convertView.getTag()).Race_Date_tv;

                tv_RaceName.setText(current.nomCourse);
                tv_Race_date.setText(current.date);

                return convertView;

            }
        };
    }
}