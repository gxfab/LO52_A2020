package com.example.f1_levier.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.BDD.roomDatabase.AppDataBase;
import com.example.f1_levier.R;
import com.example.f1_levier.utils.ElementCard;

import java.util.ArrayList;

import static com.example.f1_levier.view.TeamActivity.teams;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private TextView tv_lvl;
    private EditText te_name;
    private EditText te_fname;
    private Button b_start;
    public static AppDataBase db;
    public static ArrayList<Runner> runnerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = db.getInstance(this);

        /*Name & firstname*/
        te_name = (EditText) findViewById(R.id.editText_name);
        te_fname = (EditText) findViewById(R.id.editText_fname);
        runnerList = new ArrayList<Runner>();
        for(int i=0;i<30;i++)
        {
            int rand = (int)(Math.random() * 100 + 1);
            Runner newRunner = new Runner(i, "John"+i,"Doe",rand);
            runnerList.add(newRunner);
            db.runnerDAO().insertRunner(newRunner);
        }

        /*Level*/
        tv_lvl = (TextView) findViewById(R.id.textView_lvl);
        Button b_lvl = (Button) findViewById(R.id.button_lvl);// on click of button display the dialog_number
        b_lvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker();
            }
        });

        /*Add participant*/
        Button b_add = (Button) findViewById(R.id.button_add);// on click of button display the dialog_number
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_participant();
            }
        });

        /*Check participant*/
        Button b_check_participant = (Button) findViewById(R.id.button_check);// on click of button display the dialog_number
        b_check_participant.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {check_participant();
                                                   }
                                               });

        /*Team*/
        Button b_team = (Button) findViewById(R.id.button_team);
        b_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team();
            }
        });

        /*Start*/
        b_start = (Button) findViewById(R.id.button_start);
        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void numberPicker() {
        final Dialog d_lvl = new Dialog(MainActivity.this);
        d_lvl.setTitle("Niveau");
        d_lvl.setContentView(R.layout.dialog_number);
        Button b_set = (Button) d_lvl.findViewById(R.id.button_set);
        final NumberPicker np_lvl = (NumberPicker) d_lvl.findViewById(R.id.numberPicker_lvl);
        np_lvl.setMaxValue(100); // max value 100
        np_lvl.setMinValue(0);   // min value 0
        np_lvl.setWrapSelectorWheel(false);
        np_lvl.setOnValueChangedListener(this);
        b_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lvl.setText(String.valueOf(np_lvl.getValue())); //set the value to textview
                d_lvl.dismiss();
            }
        });
        d_lvl.show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

    public void add_participant() {
        if(!te_name.getText().toString().equals("") && !te_fname.getText().toString().equals("") && !te_name.getText().toString().equals(" ") && !te_fname.getText().toString().equals(" ") && !tv_lvl.getText().toString().equals("Niveau"))
        {
            Runner newRunner = new Runner(runnerList.size(), te_name.getText().toString(), te_fname.getText().toString(), Integer.parseInt(tv_lvl.getText().toString()));
            runnerList.add(newRunner);
            db.runnerDAO().insertRunner(newRunner);
            Log.i(te_name.getText().toString() + te_fname.getText().toString(), tv_lvl.getText().toString());
            te_name.setText("");
            te_fname.setText("");
            tv_lvl.setText("Niveau");
        }
        else{
            Toast.makeText(MainActivity.this,"Compléter tout les champs",Toast.LENGTH_SHORT).show();
        }
    }

    public void check_participant() {
        if(runnerList.size() >= 1)
        {
            Intent intent = new Intent(this, ParticipantActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this,"Ajouter au moins un participant",Toast.LENGTH_SHORT).show();
        }
    }

    public void team() {
        if (runnerList.size() == 30)
        {
            Intent intent = new Intent(this, TeamActivity.class);
            startActivity(intent);
            b_start.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(MainActivity.this,"Il faut 30 participants",Toast.LENGTH_SHORT).show();
        }
    }

    public void start() {
        if (runnerList.size() == 30 && teams != null) {
            ElementCard.nameArray = new ArrayList<>();
            for (int i = 0; i < teams.size(); i++) {
                ArrayList<String> t = new ArrayList<>();
                t.add(db.getRunnerFromId(runnerList, teams.get(i).getFirstRunnerId()).getLastName()+" "+db.getRunnerFromId(runnerList, teams.get(i).getFirstRunnerId()).getFirstName());
                t.add(db.getRunnerFromId(runnerList, teams.get(i).getSecondRunnerId()).getLastName()+" "+db.getRunnerFromId(runnerList, teams.get(i).getSecondRunnerId()).getFirstName());
                t.add(db.getRunnerFromId(runnerList, teams.get(i).getThirdRunnerId()).getLastName()+" "+db.getRunnerFromId(runnerList, teams.get(i).getThirdRunnerId()).getFirstName());
                ElementCard.nameArray.add(t);
            }
            Intent intent = new Intent(this, RunActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this,"Il faut 30 participants et avoir les équipes de composées",Toast.LENGTH_SHORT).show();
        }
    }
}