package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void displayMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button LogBut = findViewById(R.id.LogBut);
        final EditText NameField = findViewById(R.id.NameField);
        LogBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMsg("Hello "+ NameField.getText());
            }
        });
    }
}