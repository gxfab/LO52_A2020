package com.example.lo52_project_v2;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.bo.Equipe;
import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.bo.ParticipantEquipe;
import com.example.lo52_project_v2.model.dao.AppDataBase;
import com.example.lo52_project_v2.model.dao.CourseDao;
import com.example.lo52_project_v2.model.dao.EquipeDao;
import com.example.lo52_project_v2.model.dao.ParticipantEquipeDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GenerateTeamsTabbedFragment extends Fragment {

    List<Participant> participants ;
    List<Participant> available_participants ;
    List<Participant> selected_participants ;

    ListView available_participants_lv;
    ListView selected_participants_lv;

    ArrayAdapter<Participant> available_participants_Adapter;
    ArrayAdapter<Participant> selected_participants_Adapter;

    public static final String ARG_OBJECT = "Generate Teams";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GenerateTeamsTabbedFragment() {
        // Required empty public constructor
    }

    public static GenerateTeamsTabbedFragment newInstance(String param1, String param2) {
        GenerateTeamsTabbedFragment fragment = new GenerateTeamsTabbedFragment();
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
        View view = inflater.inflate(R.layout.fragment_generate_teams_tabbed, container, false);
        Button generate = (Button) view.findViewById(R.id.button);

        TextView race_name_tv = (TextView)view.findViewById(R.id.editTextRaceName);
        generate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(race_name_tv.getText().toString().trim().length()>0) {
                    if (selected_participants.size() % 3 == 0 && selected_participants != null && selected_participants.size() > 0 && selected_participants.size() <=30) {
                        AppDataBase db = AppDataBase.getInstance(v.getContext());
                        EquipeDao equipeDao = db.equipeDao();
                        ParticipantEquipeDao participantEquipeDao = db.participantEquipeDao();
                        CourseDao courseDao = db.courseDao();
                        //Course course = new Course("Course" + new Random().nextInt(), LocalDate.now().toString());
                        Course course = new Course(race_name_tv.getText().toString(), LocalDate.now().toString());
                        course.idCourse = (int) courseDao.insertCourse(course);
                        List<List<Participant>> teams = generateTeams(selected_participants);
                        for (int i = 0; i < teams.size(); i++) {
                            Equipe e = new Equipe("Equipe" + new Random().nextInt());
                            e.idCourse = course.idCourse;
                            e.idEquipe = (int) equipeDao.insertEquipe(e);

                            List<Participant> tmp = teams.get(i);
                            for (Participant p : tmp) {
                                participantEquipeDao.insertParticipantEquipe(new ParticipantEquipe(p.id, e.idEquipe, 0));
                            }

                        }

                        available_participants.clear();
                        available_participants.addAll(participants);
                        available_participants_Adapter.notifyDataSetChanged();
                        selected_participants.clear();
                        selected_participants_Adapter.notifyDataSetChanged();

                        race_name_tv.setText("");

                        Toast toast = Toast.makeText(view.getContext(), "Teams are created !", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(view.getContext(), "You have to select a " +
                                "number of participants which is a multiple of 3, and less than 10 teams", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    ServiceHandler sh = new ServiceHandler(getActivity(),"You need to enter a race name");
                    sh.start();
                }
            }
        });

        return view;
    }

    /**ALGO DE GENERATION DES EQUIPES**/
    private List<List<Participant>> generateTeams(List<Participant> participants){
        List<List<List<Participant>>> casPossible = new ArrayList<>();
        int nbrEquipes = participants.size()/3;

        for(int i=0;i<participants.size()-2;i++){
            ArrayList<List<Participant>> iList = new ArrayList<>();
            for(int j=i+1;j<participants.size()-1;j++){
                for(int k=j+1;k<participants.size();k++){
                    ArrayList<Participant> tmp = new ArrayList<>();
                    tmp.add(participants.get(i));
                    tmp.add(participants.get(j));
                    tmp.add(participants.get(k));
                    iList.add(tmp);
                }
            }
            casPossible.add(i, iList);
        }

        HashMap<Integer,List<List<Participant>>> formationPossible = new HashMap<>();

        for(List<List<Participant>> tmp : casPossible){
            for(List<Participant> tmp2 : tmp){
                List<List<Participant>> tmp3 = new ArrayList<>();
                tmp3.add(tmp2);
                for(List<List<Participant>> tmp4 : casPossible){
                    if(!tmp4.equals(tmp)) {
                        for(List<Participant> tmp5 : tmp4) {
                            boolean exists = false;
                            for(Participant p : tmp5){
                                for(List<Participant> tmp6 : tmp3){
                                    for(Participant p2 : tmp6){
                                        if(p2.id == p.id){
                                            exists = true;
                                            break;
                                        }
                                    }
                                    if(exists)
                                        break;
                                }
                                if(exists)
                                    break;
                            }
                            if(!exists)
                                tmp3.add(tmp5);
                        }
                    }
                }
                int max = tmp3.get(0).get(0).niveau + tmp3.get(0).get(1).niveau + tmp3.get(0).get(2).niveau;
                int min = tmp3.get(0).get(0).niveau + tmp3.get(0).get(1).niveau + tmp3.get(0).get(2).niveau;
                for(int p = 0; p < tmp3.size(); p++){
                    int niveauEquipe = tmp3.get(p).get(0).niveau + tmp3.get(p).get(1).niveau + tmp3.get(p).get(2).niveau;
                    if(niveauEquipe < min)
                        min = niveauEquipe;
                    else if(niveauEquipe > max)
                        max = niveauEquipe;
                }
                formationPossible.put(max-min,tmp3);
            }
        }

        double min = Double.POSITIVE_INFINITY;
        for( int k : formationPossible.keySet()){
            List<List<Participant>> tmp = formationPossible.get(k);
            if(tmp.size() == nbrEquipes){
                if(k < min)
                    min = k;
            }
        }
        return formationPossible.get((int)min);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
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
        available_participants = new ArrayList<Participant>(participants);
        selected_participants = new ArrayList<Participant>();

        available_participants_lv = view.findViewById(R.id.availableParticipants_lv);
        selected_participants_lv = view.findViewById(R.id.selectedParticipants_lv);

        available_participants_Adapter = getParticipantsListAdapter(available_participants);
        selected_participants_Adapter = getParticipantsListAdapter(selected_participants);

        available_participants_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                selected_participants.add(available_participants.get(position));
                selected_participants_Adapter.notifyDataSetChanged();
                available_participants.remove(position);
                available_participants_Adapter.notifyDataSetChanged();

            }
        });

        selected_participants_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                available_participants.add(selected_participants.get(position));
                available_participants_Adapter.notifyDataSetChanged();
                selected_participants.remove(position);
                selected_participants_Adapter.notifyDataSetChanged();

            }
        });
        available_participants_lv.setAdapter(available_participants_Adapter);
        selected_participants_lv.setAdapter(selected_participants_Adapter);

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