package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.TextView;
import android.os.Bundle;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        Intent intent = getIntent();
        if (intent != null){
            String UserName = "";
            if (intent.hasExtra("UserName")){
                UserName = intent.getStringExtra("UserName");
            }
            TextView Hello2 = (TextView) findViewById(R.id.WelcomeText);
            Hello2.setText("Welcome to LO52 " + UserName);
        }
    }

}