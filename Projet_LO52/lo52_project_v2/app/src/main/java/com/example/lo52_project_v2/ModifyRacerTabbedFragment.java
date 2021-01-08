package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.EquipeParticipants;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.dao.AppDataBase;

import java.util.List;


public class ModifyRacerTabbedFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    ListView mListView;
    TextView ParticipantNumber_tv;

    ArrayAdapter<Participant> participants_Adapter;
    List<Participant> participants ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModifyRacerTabbedFragment() {
        // Required empty public constructor
    }

    public static ModifyRacerTabbedFragment newInstance(String param1, String param2) {
        ModifyRacerTabbedFragment fragment = new ModifyRacerTabbedFragment();
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
        return inflater.inflate(R.layout.fragment_modify_racer_tabbed, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mListView = (ListView) getView().findViewById(R.id.listView);
        ParticipantNumber_tv = view.findViewById(R.id.ParticipantNumberDisplay_tv);
        AppDataBase db = AppDataBase.getInstance(getView().getContext());
        Wrapper ParticipantContainer = new Wrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                ParticipantContainer.participants = db.participantDao().getAll();
                Log.d("success", "Walking in thread");
            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        participants = ParticipantContainer.participants;
        ParticipantNumber_tv.setText(Integer.toString(participants.size()));
        participants_Adapter = getParticipantsListAdapter(participants);
        mListView.setAdapter(participants_Adapter);
        mListView.setOnItemClickListener(ItemClickListener);
    }

    private AdapterView.OnItemClickListener ItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Participant current = participants.get(position);
            Intent i = new Intent(getContext(), ModifyParticipantActivity.class);
            i.putExtra("participant_parced",current);
            startActivityForResult(i, 1888);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 1888 ) {
            if(resultCode!= Activity.RESULT_CANCELED){
                refresh_data();
            }
        }
    }


    public void refresh_data() {
        mListView = (ListView) getView().findViewById(R.id.listView);
        AppDataBase db = AppDataBase.getInstance(getView().getContext());
        Wrapper ParticipantContainer = new Wrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                ParticipantContainer.participants = db.participantDao().getAll();
                Log.d("success", "Walking in thread");
            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        participants = ParticipantContainer.participants;
        ParticipantNumber_tv.setText(Integer.toString(participants.size()));

        participants_Adapter = getParticipantsListAdapter(participants);

        mListView.setAdapter(participants_Adapter);
    }

    ArrayAdapter<Participant> getParticipantsListAdapter(List<Participant> participantsList){
        return new ArrayAdapter<Participant>(getContext(), 0, participantsList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Participant current = participantsList.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.participant_display, null, false);
                }


                TeamsFragment.ViewHolder viewHolder = new TeamsFragment.ViewHolder();
                viewHolder.tv_Name =
                        (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tv_Level =
                        (TextView)convertView.findViewById(R.id.tv_level);

                convertView.setTag(viewHolder);

                TextView tv_Name =
                        ((TeamsFragment.ViewHolder)convertView.getTag()).tv_Name;
                TextView tv_Level =
                        ((TeamsFragment.ViewHolder)convertView.getTag()).tv_Level;

                tv_Name.setText(current.nomParticipant);
                tv_Level.setText(current.niveau.toString());

                return convertView;
            }
        };
    }


}

class Wrapper{
    List<Participant> participants;
}

class TeamsWrapper{
    List<Equipe> teams;
}

class RacesWrapper{
    List<Course> races;
}

class RaceWrapper{
    Course race;
}

class ParticipantEquipeWrapper{
    List<ParticipantEquipe> participantEquipe_crossRef;
}

class EquipeParticipantsWrapper{

    List<EquipeParticipants> teams_participants;
}