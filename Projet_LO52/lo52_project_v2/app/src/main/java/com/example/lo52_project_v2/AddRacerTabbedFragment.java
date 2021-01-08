package com.example.lo52_project_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.dao.AppDataBase;


public class AddRacerTabbedFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    TextView editName_tv;
    TextView editLevel_tv;

    Button cancel_btn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddRacerTabbedFragment() {

    }

    public static AddRacerTabbedFragment newInstance(String param1, String param2) {
        AddRacerTabbedFragment fragment = new AddRacerTabbedFragment();
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

        View view = inflater.inflate(R.layout.fragment_add_racer_tabbed, container, false);
        Button add_player_button = (Button) view.findViewById(R.id.addPlayer);
        add_player_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRacer_click(v);
            }
        });

        editName_tv = view.findViewById(R.id.editTextTextLastName);
        editLevel_tv = view.findViewById(R.id.editTextTextLevel);
        cancel_btn = view.findViewById(R.id.cancel_add_btn);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName_tv.setText("");
                editLevel_tv.setText("");
            }
        });

        return view;
    }

    public void addRacer_click(View v){

        String Lastname = editName_tv.getText().toString();
        String Levelstr = editLevel_tv.getText().toString();

        AppDataBase db = AppDataBase.getInstance(v.getContext());

        if(Lastname.trim().length()>0 && Levelstr.trim().length()>0){
            try {
                int iLevel = Integer.parseInt(Levelstr);
                if(iLevel>=0 && iLevel<=100) {
                    Participant curParticipant = new Participant(Lastname, iLevel);
                    Thread t = new Thread(new Runnable() {
                        //@Override
                        public void run() {
                            db.participantDao().insertParticipant(curParticipant);
                        }
                    });
                    t.start();
                    editName_tv.setText("");
                    editLevel_tv.setText("");
                }
                else {
                    Toast toast = Toast.makeText(getContext(), "Levels must be between 1 and 100", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            catch(NumberFormatException NFe){
                Toast toast = Toast.makeText(getContext(), "Level input not corresponding to integer", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getContext(), "All inputs must be completed", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}