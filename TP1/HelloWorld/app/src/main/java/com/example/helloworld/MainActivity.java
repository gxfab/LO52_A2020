package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
        This function is called when the button with ID=NameField is clicked,
        it is mentioned in activity_main.xml
    */
    public void ChangeActivity(View view) {
        Intent intent = new Intent(this, SecondaryActivity.class);
        Bundle bundle = new Bundle();
        EditText NameField = (EditText) findViewById(R.id.NameField);
        bundle.putString("UserName", NameField.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}