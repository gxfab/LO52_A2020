package com.example.f1_levier.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.f1_levier.R;
import com.example.f1_levier.adapter.ParticipantAdapter;

import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

public class ParticipantActivity extends AppCompatActivity {

    int id_selected = -1;
    ParticipantAdapter adapter;
    TextView tv_selected;
    TextView tv_nb;

    //Constructeur
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particpant);

        /*define element of the view*/
        tv_nb = (TextView) findViewById(R.id.textView_nb_participant);
        tv_selected = (TextView) findViewById(R.id.textView_selected_participant);

        final ListView listView = (ListView) findViewById(R.id.listView_participant);

        Button b_edit = (Button)findViewById(R.id.button_edit_participant);
        b_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_participant();
            }
        });
        Button b_del = (Button)findViewById(R.id.button_del_participant);
        b_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_participant();
            }
        });

        /*number of participant*/
        tv_nb.setText(String.valueOf(runnerList.size()));

        /*Selection of participant*/
        // Create the adapter to convert the array to views
        adapter = new ParticipantAdapter(this, runnerList);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        //adapter.addAll(participants);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = runnerList.get(i).getLastName()+" "+ runnerList.get(i).getFirstName();
                id_selected = i;
                tv_selected.setText(name);
            }
        });
    }

    public void edit_participant() {
        if(id_selected != -1)
        {
            final Dialog d_edit = new Dialog(ParticipantActivity.this);
            d_edit.setContentView(R.layout.dialog_edit);

            Button b_set = (Button) d_edit.findViewById(R.id.button_set);
            final EditText te_name = (EditText) d_edit.findViewById(R.id.editText_name);
            final EditText te_fname = (EditText) d_edit.findViewById(R.id.editText_fname);
            final EditText te_lvl = (EditText) d_edit.findViewById(R.id.editText_lvl);
            te_name.setText(runnerList.get(id_selected).getLastName());
            te_fname.setText(runnerList.get(id_selected).getFirstName());
            te_lvl.setText(String.valueOf(runnerList.get(id_selected).getLevel()));

            b_set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(String.valueOf(te_lvl.getText())) < 100 && Integer.parseInt(String.valueOf(te_lvl.getText())) >= 0
                            && !te_name.getText().toString().equals("") && !te_fname.getText().toString().equals("")
                            && !te_name.getText().toString().equals(" ") && !te_fname.getText().toString().equals(" ")) {
                        runnerList.get(id_selected).setLastName(te_name.getText().toString());
                        runnerList.get(id_selected).setFirstName(te_fname.getText().toString());
                        runnerList.get(id_selected).setLevel(Integer.parseInt(String.valueOf(te_lvl.getText())));
                        db.runnerDAO().updateRunner(runnerList.get(id_selected));
                        d_edit.dismiss();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ParticipantActivity.this, "Veuillez remplir correctement les cases", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            d_edit.show();
        }
        else{
            Toast.makeText(ParticipantActivity.this, "Veuillez sellectionner un participant", Toast.LENGTH_SHORT).show();
        }
    }

    public void del_participant() {
        if(id_selected != -1)
        {
            db.runnerDAO().deleteRunner(runnerList.get(id_selected));
            runnerList.remove(id_selected);
            tv_nb.setText(String.valueOf(runnerList.size()));
            adapter.notifyDataSetChanged();
            id_selected = -1;
            tv_selected.setText("");
        }
        else{
            Toast.makeText(ParticipantActivity.this, "Veuillez sellectionner un participant", Toast.LENGTH_SHORT).show();
        }
    }
}
