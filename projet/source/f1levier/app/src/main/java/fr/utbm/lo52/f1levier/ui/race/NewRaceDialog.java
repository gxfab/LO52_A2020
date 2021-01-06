package fr.utbm.lo52.f1levier.ui.race;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.entity.Race;
import fr.utbm.lo52.f1levier.ui.team.TeamActivity;
import fr.utbm.lo52.f1levier.viewmodel.RaceViewModel;

public class NewRaceDialog extends AppCompatDialogFragment {
    private EditText nameEditText;
    private RaceViewModel raceViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_race, null);

        nameEditText = view.findViewById(R.id.edit_text_race_name);

        raceViewModel = ViewModelProviders.of(this).get(RaceViewModel.class);

        builder.setView(view)
                .setTitle(R.string.title_new_race)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!nameEditText.getText().toString().isEmpty()) {
                            Race race = new Race(nameEditText.getText().toString().trim());

                            RaceInsertAsyncTask asyncTask = new RaceInsertAsyncTask(raceViewModel,
                                    getContext());
                            asyncTask.execute(race);
                        }

                    }
                });

        return builder.create();
    }

    private static class RaceInsertAsyncTask extends AsyncTask<Race, Void, Long> {

        private RaceViewModel raceViewModel;

        private WeakReference<Context> contextWeakReference;

        RaceInsertAsyncTask(RaceViewModel raceViewModel, Context context) {
            this.raceViewModel = raceViewModel;
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Long doInBackground(Race... params) {
            return raceViewModel.insertSync(params[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            Context context = contextWeakReference.get();
            if (context != null) {
                Intent teamIntent = new Intent(context, TeamActivity.class);
                teamIntent.putExtra(TeamActivity.EXTRA_RACE_ID, id.intValue());
                context.startActivity(teamIntent);
            }
            else {
                // TODO : Find a way to resolve the context reference lost problem
            }
        }
    }
}
