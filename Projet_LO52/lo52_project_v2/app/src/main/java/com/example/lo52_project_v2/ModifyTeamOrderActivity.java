package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.dao.AppDataBase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModifyTeamOrderActivity extends AppCompatActivity {

    List<Participant> currentOrder_participants ;
    List<Participant> newOrder_participants ;
    List<ParticipantEquipe> currentOrder_participantsEquipe ;
    List<ParticipantEquipe> newOrder_participantsEquipe ;

    Equipe currentTeam;
    Course currentRace;

    ListView currentOrder_lv;
    ListView newOrder_lv;

    ArrayAdapter<Participant> currentOrder_participants_Adapter;
    ArrayAdapter<Participant> newOrder_participants_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_team_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentOrder_lv = findViewById(R.id.currentOrder_lv);
        newOrder_lv = findViewById(R.id.newOrder_lv);

        Intent i = getIntent();
        Bundle data = i.getExtras();

        ParticipantList participants = (ParticipantList)data.getParcelable("correspondingParticipants_List") ;
        currentOrder_participants = participants.participants;
        newOrder_participants = new ArrayList<Participant>();
        currentRace = (Course) data.getParcelable("selected_race");
        currentTeam = (Equipe)data.getParcelable("selected_team");



        AppDataBase db = AppDataBase.getInstance(this);
        ParticipantEquipeWrapper participantOrderContainer = new ParticipantEquipeWrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                participantOrderContainer.participantEquipe_crossRef = db.participantDao().getCorrespondingsParticipantsOrder(currentTeam.idEquipe);
            }
        });
        t.start();
        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        currentOrder_participantsEquipe = participantOrderContainer.participantEquipe_crossRef;
        Collections.sort(currentOrder_participantsEquipe);

        List<Participant> sorted_currentOrder_participants = new ArrayList<Participant>();
        for(ParticipantEquipe cur_participantEquipe : currentOrder_participantsEquipe){
            for(Participant cur_Participant : currentOrder_participants){
                if(cur_Participant.id==cur_participantEquipe.id){
                    sorted_currentOrder_participants.add(cur_Participant);
                }
            }
        }

        currentOrder_participants = sorted_currentOrder_participants;

        currentOrder_participants_Adapter = getParticipantListAdapter(currentOrder_participants);
        newOrder_participants_Adapter = getParticipantListAdapter(newOrder_participants);

        currentOrder_lv.setAdapter(currentOrder_participants_Adapter);
        newOrder_lv.setAdapter(newOrder_participants_Adapter);

        currentOrder_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                Participant clicked = currentOrder_participants.get(position);
                int newOrderParticipantpos=0;
                Boolean isInList = false;

                for(int i=0;i<newOrder_participants.size();i++){
                    Participant cur_participant = newOrder_participants.get(i);
                    if(cur_participant.id == clicked.id){
                        isInList=true;
                        newOrderParticipantpos=i;
                    }
                }

                ModifyTeamsTabbedFragment.ParticipantViewHolder viewHolder = new ModifyTeamsTabbedFragment.ParticipantViewHolder();
                viewHolder.tv_Name =
                        (TextView)view.findViewById(R.id.tv_name);
                viewHolder.tv_Level =
                        (TextView)view.findViewById(R.id.tv_level);

                if(!isInList){
                    newOrder_participants.add(clicked);
                    viewHolder.tv_Name.setTextColor(Color.RED);
                    viewHolder.tv_Level.setTextColor(Color.RED);

                }
                else{
                    newOrder_participants.remove(newOrderParticipantpos);
                    viewHolder.tv_Name.setTextColor(Color.GRAY);
                    viewHolder.tv_Level.setTextColor(Color.GRAY);
                }

                newOrder_participants_Adapter.notifyDataSetChanged();
            }
        });

        Button cancelbtn = findViewById(R.id.modifyOrderCancel_btn);
        Button validatebtn = findViewById(R.id.modifyOrderValidate_btn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ires = new Intent(v.getContext(), ModifyTeamOrderActivity.class);
                setResult(Activity.RESULT_CANCELED, ires);
                finish();
            }
        });

        validatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newOrder_participants.size()==3){

                    AppDataBase db = AppDataBase.getInstance(v.getContext());

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            db.participantEquipeDao().delete(currentOrder_participantsEquipe);

                            newOrder_participantsEquipe = new ArrayList<ParticipantEquipe>();

                            for(int i =0;i<newOrder_participants.size();i++){
                                Participant cur_participant = newOrder_participants.get(i);
                                newOrder_participantsEquipe.add(new ParticipantEquipe( cur_participant.id,currentTeam.idEquipe,i));
                            }

                            db.participantEquipeDao().insertParticipantEquipe(newOrder_participantsEquipe);

                        }
                    });
                    t.start();

                    try {
                        t.join();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    Intent ires = new Intent(v.getContext(), ModifyTeamOrderActivity.class);
                    setResult(Activity.RESULT_OK, ires);
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(v.getContext(), "All participants must be rearranged", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

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
                            .inflate(R.layout.participant_display, null, false);
                }

                ModifyTeamsTabbedFragment.ParticipantViewHolder viewHolder = new ModifyTeamsTabbedFragment.ParticipantViewHolder();
                viewHolder.tv_Name =
                        (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tv_Level =
                        (TextView)convertView.findViewById(R.id.tv_level);

                convertView.setTag(viewHolder);

                TextView tv_Name =
                        ((ModifyTeamsTabbedFragment.ParticipantViewHolder)convertView.getTag()).tv_Name;
                TextView tv_Level =
                        ((ModifyTeamsTabbedFragment.ParticipantViewHolder)convertView.getTag()).tv_Level;

                tv_Name.setText(current.nomParticipant);
                tv_Level.setText(current.niveau.toString());

                return convertView;

            }
        };
    }
}