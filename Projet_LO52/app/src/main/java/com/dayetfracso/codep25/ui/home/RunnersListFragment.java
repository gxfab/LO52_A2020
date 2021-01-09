package com.dayetfracso.codep25.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dayetfracso.codep25.R;
import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.entity.Runner;

import java.util.List;

public class RunnersListFragment extends DialogFragment {
    ListView runnersListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_runners_list,container,false);

        runnersListView = (ListView) rootView.findViewById(R.id.runners_table);
        getDialog().setTitle("Liste des coureurs");

        //  Getting runners data from the database
        AppDatabase db = AppDatabase.getDatabase(getActivity().getApplicationContext());
        List<Runner> runners = db.runnerDao().getAllRunners();

        //  Populating the list view with the runners data
        RunnersListAdapter runnersListAdapter = new RunnersListAdapter(getActivity(),runners);
        runnersListView.setAdapter(runnersListAdapter);
        return rootView;
    }
}