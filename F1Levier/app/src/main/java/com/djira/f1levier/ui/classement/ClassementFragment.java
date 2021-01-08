package com.djira.f1levier.ui.classement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.djira.f1levier.R;
import com.djira.f1levier.adapter.ClassementAdapter;
import com.djira.f1levier.adapter.EquipeAdapter;
import com.djira.f1levier.adapter.ParticipantAdapter;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;
import com.djira.f1levier.ui.equipe.EquipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClassementFragment extends Fragment {

    private EquipeViewModel equipeViewModel;
// Initialize variable

    EditText editText;
    Button btRefresh;
    RecyclerView recyclerView;

    List<Equipe> equipes = new ArrayList<>();
    List<Participant> participants = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    AppDB database;

    ClassementAdapter adapter;

    public ClassementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        equipeViewModel =
                ViewModelProviders.of(this).get(EquipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_classement, container, false);

        recyclerView = root.findViewById(R.id.recycler_view_equipe_classement);

        // Initialize database
        database = AppDB.getInstance(getActivity());
        //Store database value in data list

        equipes = database.equipeDao().getAllByTemps();
        participants = database.participantDao().getAll();

        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        adapter = new ClassementAdapter(getActivity(), equipes, participants);
        //set adapter
        recyclerView.setAdapter(adapter);
        
     return root;
    }
}