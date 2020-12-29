package com.example.f1_levier.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.f1_levier.R;

import java.util.List;

import static com.example.f1_levier.view.RunActivity.win_team;
import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

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
        List<String> test = db.getBestTimeAsString(runnerList, 2);
        for(String s : test)
        {
            System.out.println(s);
        }
    }
}
