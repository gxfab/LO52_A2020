package com.example.f1_levier.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.f1_levier.R;

import java.util.Objects;

import static com.example.f1_levier.view.TeamActivity.teams;
import static com.example.f1_levier.view.TeamActivity.item_selected;;

public class TeamDialog extends AppCompatDialogFragment {

    private Spinner s_p1;
    private Spinner s_p2;
    private Spinner s_p3;
    private TeamDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_order, null);

        builder.setView(view)
                .setTitle("Passage")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (s_p1.getSelectedItem() != null && s_p2.getSelectedItem() != null && s_p3.getSelectedItem() != null) {
                            if (Integer.parseInt(s_p1.getSelectedItem().toString()) != Integer.parseInt(s_p2.getSelectedItem().toString()) &&
                                    Integer.parseInt(s_p2.getSelectedItem().toString()) != Integer.parseInt(s_p3.getSelectedItem().toString()) &&
                                    Integer.parseInt(s_p3.getSelectedItem().toString()) != Integer.parseInt(s_p1.getSelectedItem().toString())){

                                String place = s_p1.getSelectedItem().toString()+","+s_p2.getSelectedItem().toString()+","+s_p3.getSelectedItem().toString();
                                listener.applyTexts(place);
                            }
                            else {
                                Toast.makeText(getContext(), "Veuillez selectionner correctement les 3 valeurs possibles", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        TextView name_p1 = (TextView) view.findViewById(R.id.textView_n_p1);
        TextView fname_p1 = (TextView) view.findViewById(R.id.textView_fn_p1);
        TextView name_p2 = (TextView) view.findViewById(R.id.textView_n_p2);
        TextView fname_p2 = (TextView) view.findViewById(R.id.textView_fn_p2);
        TextView name_p3 = (TextView) view.findViewById(R.id.textView_n_p3);
        TextView fname_p3 = (TextView) view.findViewById(R.id.textView_fn_p3);

        s_p1 = (Spinner) view.findViewById(R.id.spinner_p1);
        s_p2 = (Spinner) view.findViewById(R.id.spinner_p2);
        s_p3 = (Spinner) view.findViewById(R.id.spinner_p3);

        name_p1.setText(teams.get(item_selected).getParticipants().get(0).getName());
        name_p2.setText(teams.get(item_selected).getParticipants().get(1).getName());
        name_p3.setText(teams.get(item_selected).getParticipants().get(2).getName());

        fname_p1.setText(teams.get(item_selected).getParticipants().get(0).getFirstName());
        fname_p2.setText(teams.get(item_selected).getParticipants().get(1).getFirstName());
        fname_p3.setText(teams.get(item_selected).getParticipants().get(2).getFirstName());

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TeamDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement TeamDialogListener");
        }
    }
    public interface TeamDialogListener {
        void applyTexts(String place);
    }
}