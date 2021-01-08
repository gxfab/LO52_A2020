package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lo52_project_v2.model.bo.Course;
import com.example.lo52_project_v2.model.dao.AppDataBase;

import java.util.List;

public class RaceFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button start_race_btn;
    Button show_stats_btn;

    TextView selected_unfinishedRace_tv;
    TextView selected_finishedRace_tv;

    ListView finished_race_lv;
    ListView unfinished_race_lv;

    List<Course> finishedRaces;
    List<Course> unfinishedRaces;

    ArrayAdapter<Course> finishedRaces_Adapter;
    ArrayAdapter<Course> unfinishedRaces_Adapter;

    Course selected_finishedRace;
    Course selected_unfinishedRace;

    static class RaceViewHolder{
        TextView Race_Name_tv;
        TextView Race_Date_tv;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRacerTabbedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RaceFragment newInstance(String param1, String param2) {
        RaceFragment fragment = new RaceFragment();
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

        View view = inflater.inflate(R.layout.fragment_race, container, false);

        start_race_btn = (Button) view.findViewById(R.id.start_race_btn);
        show_stats_btn = (Button) view.findViewById(R.id.show_stats_btn);
        selected_unfinishedRace_tv = view.findViewById(R.id.selectedUnfinishedRace_tv);
        selected_finishedRace_tv = view.findViewById(R.id.selectedFinishedRace_tv);
        finished_race_lv = view.findViewById(R.id.finished_race_lv);
        unfinished_race_lv = view.findViewById(R.id.unfinishedRace_lv);

        refresh_data();

        finished_race_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                selected_finishedRace = finishedRaces.get(position);
                selected_finishedRace_tv.setText(selected_finishedRace.nomCourse);

            }
        });

        unfinished_race_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                selected_unfinishedRace = unfinishedRaces.get(position);
                selected_unfinishedRace_tv.setText(selected_unfinishedRace.nomCourse);
            }
        });

        start_race_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_unfinishedRace!=null){
                    Intent i = new Intent(getContext(), start_race_activity.class);
                    i.putExtra("selected_race",selected_unfinishedRace);
                    startActivityForResult(i, 555);
                }
                else{
                    Toast toast = Toast.makeText(v.getContext(), "You must Select an unfinished race to start it", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        show_stats_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_finishedRace!=null){
                    Intent i = new Intent(getContext(), show_stats_ativity.class);
                    i.putExtra("selected_race_id",selected_finishedRace.idCourse);
                    startActivityForResult(i, 556);
                }
                else{
                    Toast toast = Toast.makeText(v.getContext(), "You must Select a finished race to show its stats", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 555 ) {
            if(resultCode!= Activity.RESULT_CANCELED){
                selected_unfinishedRace_tv.setText("");
                selected_unfinishedRace = null;
                refresh_data();
            }
        }
    }

    public void refresh_data(){

        AppDataBase db = AppDataBase.getInstance(getContext());

        RacesWrapper unFinishedRacesContainer = new RacesWrapper();
        RacesWrapper FinishedRacesContainer = new RacesWrapper();

        Thread t = new Thread(new Runnable() {
            public void run() {
                unFinishedRacesContainer.races = db.courseDao().getAllunFinished();
                FinishedRacesContainer.races = db.courseDao().getAllFinished();

            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        finishedRaces = FinishedRacesContainer.races;
        unfinishedRaces = unFinishedRacesContainer.races;

        finishedRaces_Adapter = getRaceListAdapter(finishedRaces);
        unfinishedRaces_Adapter = getRaceListAdapter(unfinishedRaces);

        finished_race_lv.setAdapter(finishedRaces_Adapter);
        unfinished_race_lv.setAdapter(unfinishedRaces_Adapter);

    }


    ArrayAdapter<Course> getRaceListAdapter(List<Course> raceList){
        return new ArrayAdapter<Course>(getContext(), 0, raceList) {
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                Course current = raceList.get(position);

                if(convertView == null) {
                    convertView = getLayoutInflater()
                            .inflate(R.layout.race_item_display, null, false);
                }

                ModifyTeamsTabbedFragment.RaceViewHolder viewHolder = new ModifyTeamsTabbedFragment.RaceViewHolder();
                viewHolder.Race_Name_tv =
                        (TextView)convertView.findViewById(R.id.race_name_tv);
                viewHolder.Race_Date_tv =
                        (TextView)convertView.findViewById(R.id.race_date_tv);

                convertView.setTag(viewHolder);

                TextView tv_RaceName =
                        ((ModifyTeamsTabbedFragment.RaceViewHolder)convertView.getTag()).Race_Name_tv;
                TextView tv_Race_date =
                        ((ModifyTeamsTabbedFragment.RaceViewHolder)convertView.getTag()).Race_Date_tv;

                tv_RaceName.setText(current.nomCourse);
                tv_Race_date.setText(current.date);

                return convertView;

            }
        };
    }
}
