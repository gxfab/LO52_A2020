package com.djira.f1levier.ui.equipe;

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
import com.djira.f1levier.adapter.EquipeAdapter;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

import java.util.ArrayList;
import java.util.List;


public class EquipeFragment extends Fragment {

    private EquipeViewModel equipeViewModel;
// Initialize variable

    EditText editText;
    Button btRefresh;
    RecyclerView recyclerView;

    List<Equipe> dataList = new ArrayList<>();
    List<Participant> participants = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    AppDB database;
    EquipeAdapter adapter;

    public EquipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        equipeViewModel =
                ViewModelProviders.of(this).get(EquipeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_equipe, container, false);

        editText = root.findViewById(R.id.edit_text);
        recyclerView = root.findViewById(R.id.recycler_view_equipe);

        // Initialize database and get all equipes
        database = AppDB.getInstance(getActivity());
        dataList = database.equipeDao().getAll();

        loadEquipe();

        btRefresh = root.findViewById(R.id.btn_refresh);
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.equipeDao().reset(dataList);
                participants = database.participantDao().getAllByLevel();
                participantDispatcher(participants);
                loadEquipe();
            }
        });

        return root;
    }

    // Function to set adapter and to show "equipe" list
    void loadEquipe() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new EquipeAdapter(getActivity(),dataList);
        recyclerView.setAdapter(adapter);
    }

    // Algorithm to generate "equipe"
    void participantDispatcher(List<Participant> participants) {
        int sizeEquipe = participants.size()/3, size, i = 0;

        for ( int z = 0 ; z < sizeEquipe; z++ ) {
            size = participants.size()-1;
            //Create "equipe"
            Equipe e = createEquipe(i);
            //Add last participant and first participant to the "equipe";
            database.participantDao().updateEquipe(participants.get(i).getID(),e.getID());
            database.participantDao().updateEquipe(participants.get(size).getID(),e.getID());
            //Remove affected "participants" from list of "participants"
            participants.remove(size);
            participants.remove(i);
            i++;

        }
        dataList = database.equipeDao().getAll();
        size = participants.size();
        for (int y=0; y<size;y++) {
            //Add one participant to the "equipe"
            database.participantDao().updateEquipe(participants.get(y).getID(), dataList.get(y).getID());
        }
        // Do "equipe" balancing
        equipeBlancing();
    }

    //Create one "equipe"
    Equipe createEquipe(int i) {
        Equipe equipe = new Equipe();
        equipe.setName("Equipe " + (i+1));
        database.equipeDao().insert(equipe);
        return database.equipeDao().getByNom(equipe.getName());
    }

    //Function to balance "equipe" from "equipe" level
    void equipeBlancing() {
        dataList = database.equipeDao().getAllByLevel();
        // Load lowest and highest "equipe"
        Equipe e1 = dataList.get(0);
        Equipe e2 = dataList.get(dataList.size()-1);
        // Load participants by each "equipe"
        List<Participant> pListE1 = database.participantDao().getAllByEquipeID(e1.getID());
        List<Participant> pListE2 = database.participantDao().getAllByEquipeID(e2.getID());
        // Verify if the difference of equipe Level  is higher than 25
        if (((e1.getLevel() !=null) && (e2.getLevel() !=null))) {
            if ((e2.getLevel() - e1.getLevel()) > 25 ) {
                // Switch participant index 2 and update database
                database.participantDao().updateEquipe(pListE1.get(1).getID(), e2.getID());
                database.participantDao().updateEquipe(pListE2.get(1).getID(), e1.getID());
            }
        }
    }
}