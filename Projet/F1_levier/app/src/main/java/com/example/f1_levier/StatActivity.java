package com.example.f1_levier;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.f1_levier.RunActivity.win_team;
import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        TextView te_rank = findViewById(R.id.textView_rank);
        String temp = "";
        for(int i =0; i< win_team.size();i++){
            temp = temp + win_team.get(i) + " ";
        }
        te_rank.setText(temp);
    }
}
