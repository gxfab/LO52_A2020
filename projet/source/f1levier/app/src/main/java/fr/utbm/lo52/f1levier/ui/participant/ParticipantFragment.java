package fr.utbm.lo52.f1levier.ui.participant;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.entity.Participant;
import fr.utbm.lo52.f1levier.viewmodel.ParticipantViewModel;

public class ParticipantFragment extends Fragment {

    private static final int READ_DATA_FILE_REQUEST_CODE = 42;
    private static final String NEW_PARTICIPANT_DIALOG_CODE = "new_participant";

    private ParticipantViewModel participantViewModel;

    public ParticipantFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participant_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.participant_list);
        final ParticipantListAdapter adapter = new ParticipantListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        participantViewModel.getAllParticipants().observe(this,
                new Observer<List<Participant>>() {
                    @Override
                    public void onChanged(@Nullable final List<Participant> participants) {
                        adapter.setParticipants(participants);
                    }
                });

        DividerItemDecoration itemDecoration = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        FloatingActionButton addFab = view.findViewById(R.id.fab_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                NewParticipantDialog newParticipantDialog = new NewParticipantDialog();
                newParticipantDialog.show(Objects.requireNonNull(getFragmentManager()),
                        NEW_PARTICIPANT_DIALOG_CODE);
            }
        });

        FloatingActionButton importFab = view.findViewById(R.id.fab_import);
        importFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/*");
                startActivityForResult(intent, READ_DATA_FILE_REQUEST_CODE);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_DATA_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                proceedImportFromFileUri(resultData.getData());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void proceedImportFromFileUri(Uri uri) {
        for (String line : readTextLinesFromUri(uri)) {
            String[] lineData = line.split("\t");
            // Insert 0 as echelon when integer conversion does not work
            try {
                participantViewModel.insertAsync(new Participant(lineData[0],
                        Integer.valueOf(lineData[1])));
            }
            catch (NumberFormatException e) {
                handleImportLineException(lineData, e);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                handleImportLineException(lineData, e);
            }
        }
    }

    private void handleImportLineException(String [] lineData, Exception e) {
        participantViewModel.insertAsync(new Participant(lineData[0], 0));
        e.printStackTrace();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private ArrayList<String> readTextLinesFromUri(Uri uri) {
        ArrayList<String> lines= new ArrayList<>();
        try {
            InputStream inputStream =
                    Objects.requireNonNull(getContext()).getContentResolver().openInputStream(uri);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                lines.add(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
