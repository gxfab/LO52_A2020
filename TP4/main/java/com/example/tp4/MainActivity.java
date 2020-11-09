package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    EditText text;
    Button left,right,up,down,read,write;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();


        this.text= findViewById(R.id.display);

        left =findViewById(R.id.leftButton);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("LEFTBUTTONCLICKED");
            }
        });

        up =findViewById(R.id.upButton);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("UPBUTTONCLICKED");
            }
        });

        down =findViewById(R.id.downButton);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("DOWNBUTTONCLICKED");
            }
        });

        right =findViewById(R.id.rightButton);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("RIGHTBUTTONCLICKED");
            }
        });

        read =findViewById(R.id.readButton);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read.setText("READ : "+ Math.pow(rand.nextInt(10),2));
            }
        });

        write =findViewById(R.id.writeButton);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("WRITE : "+ Math.pow(rand.nextInt(10),3));
            }
        });

    }
}