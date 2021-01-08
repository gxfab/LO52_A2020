package com.example.lo52_project_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lo52_project_v2.model.bo.Participant;
import com.example.lo52_project_v2.model.dao.AppDataBase;

public class ModifyParticipantActivity extends AppCompatActivity {

    Participant currentParticipant;
    TextView NewName_tv;
    TextView NewLevel_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_participant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Bundle data = i.getExtras();

        currentParticipant = (Participant)data.getParcelable("participant_parced");

        NewName_tv=findViewById(R.id.editTxtParticipantNewName);
        NewLevel_tv=findViewById(R.id.editTxtParticipantNewLevel);
        NewName_tv.setText(currentParticipant.nomParticipant);
        NewLevel_tv.setText(currentParticipant.niveau.toString());

        Button cancelbtn = (Button)findViewById(R.id.CancelParticipantUpdate);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelParticipantclick(v);
            }
        });

        Button deletebtn = (Button)findViewById(R.id.DeleteParticipantUpdate);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteParticipantclick(v);
            }
        });

        Button validatebtn = (Button)findViewById(R.id.ValidateParticipantUpdate);
        validatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateParticipantclick(v);
            }
        });
    }

    public void deleteParticipantclick(View v){
        AppDataBase db = AppDataBase.getInstance(this);

        Thread t = new Thread(new Runnable() {
            //@Override
            public void run() {
                db.participantDao().deleteParticipant(currentParticipant);
            }
        });
        t.start();

        try {
            t.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        Intent ires = new Intent(this, ModifyParticipantActivity.class);
        setResult(Activity.RESULT_OK, ires);
        finish();
    }

    public void updateParticipantclick(View v){
        AppDataBase db = AppDataBase.getInstance(this);

        String Newname_str = NewName_tv.getText().toString();
        String NewLevel_str = NewLevel_tv.getText().toString();

        if(Newname_str.trim().length()>0 && NewLevel_str.trim().length()>0){
            try {
                int iLevel = Integer.parseInt(NewLevel_str);
                if(iLevel>=0 && iLevel<=100) {
                    currentParticipant.nomParticipant = Newname_str;
                    currentParticipant.niveau = Integer.parseInt(NewLevel_str);

                    Thread t = new Thread(new Runnable() {
                        //@Override
                        public void run() {
                            db.participantDao().updateParticipant(currentParticipant);
                        }
                    });
                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent ires = new Intent(this, ModifyParticipantActivity.class);
                    setResult(Activity.RESULT_OK, ires);

                    finish();
                }
                else {
                    Toast toast = Toast.makeText(this, "Levels must be between 1 and 100", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
            catch(NumberFormatException NFe){
                Toast toast = Toast.makeText(this, "Level input not corresponding to integer", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(this, "All inputs must be completed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void cancelParticipantclick(View v){
        Intent ires = new Intent(this, ModifyParticipantActivity.class);
        setResult(Activity.RESULT_CANCELED, ires);
        finish();
    }

}