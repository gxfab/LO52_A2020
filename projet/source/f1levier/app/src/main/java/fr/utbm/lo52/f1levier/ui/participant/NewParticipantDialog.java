package fr.utbm.lo52.f1levier.ui.participant;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.entity.Participant;
import fr.utbm.lo52.f1levier.viewmodel.ParticipantViewModel;

public class NewParticipantDialog extends AppCompatDialogFragment {

    private EditText nameEditText;
    private SeekBar echelonSeekBar;
    private TextView echelonValueTextView;
    private ParticipantViewModel participantViewModel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_participant, null);

        nameEditText = view.findViewById(R.id.edit_text_participant_name);
        echelonSeekBar = view.findViewById(R.id.seek_bar_participant_echelon);
        echelonValueTextView = view.findViewById(R.id.text_view_participant_echelon_value);
        echelonValueTextView.setText(String.valueOf(echelonSeekBar.getProgress()));

        echelonSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                echelonValueTextView.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);

        builder.setView(view)
                .setTitle(R.string.title_new_participant)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!nameEditText.getText().toString().isEmpty()) {
                            Participant participant = new Participant(
                                    nameEditText.getText().toString().trim(),
                                    echelonSeekBar.getProgress());

                            participantViewModel.insertAsync(participant);
                        }

                    }
                });

        return builder.create();
    }
}
