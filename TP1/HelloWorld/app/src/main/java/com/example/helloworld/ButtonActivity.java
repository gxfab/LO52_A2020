package com.example.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
    }

    public void launchHello(View view){
        Intent intent= new Intent(ButtonActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
