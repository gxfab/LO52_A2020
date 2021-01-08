package com.djira.f1levier.ui.participant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.djira.f1levier.R;
import com.djira.f1levier.adapter.ParticipantAdapter;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantFragment extends Fragment {

    private ParticipantViewModel participantViewModel;
// Initialize variable

    EditText editText,editTextLevel;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<Participant> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AppDB database;

    ParticipantAdapter adapter;

    public ParticipantFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        participantViewModel =
                ViewModelProviders.of(this).get(ParticipantViewModel.class);
        View root = inflater.inflate(R.layout.fragment_participant, container, false);

        editText = root.findViewById(R.id.edit_text);
        editTextLevel = root.findViewById(R.id.editTextLevel);

        btAdd = root.findViewById(R.id.bt_add);
        btReset = root.findViewById(R.id.bt_reset);
        recyclerView = root.findViewById(R.id.recycler_view);

        // Initialize database
        database = AppDB.getInstance(getActivity());
        //Store database value in data list
        dataList = database.participantDao().getAll();

        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        adapter = new ParticipantAdapter(getActivity(),dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get string from edit text
                String sText = editText.getText().toString().trim();
                int sLevel = Integer.parseInt(editTextLevel.getText().toString());

                if (!sText.equals("")) {
                    Participant data = new Participant();
                    data.setName(sText);
                    data.setLevel(sLevel);
                    data.setEquipe_id(null);
                    database.participantDao().insert(data);

                    editText.setText("");
                    editTextLevel.setText("");
                    dataList.clear();
                    dataList.addAll(database.participantDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all data from database
                database.participantDao().reset(dataList);
                List<Equipe> equipeList = database.equipeDao().getAll();
                database.equipeDao().reset(equipeList);
                dataList.clear();
                dataList.addAll(database.participantDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });

        return root;
    }
}