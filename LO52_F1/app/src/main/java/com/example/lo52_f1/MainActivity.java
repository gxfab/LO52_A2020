package com.example.lo52_f1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void startNewGame(View view) {
        Intent intent;
        intent = new Intent(this, RunnerAddActivity.class);
        startActivity(intent);
    }

    public void allResults(View view){
        File file = new File(getFilesDir() + "/results.txt");
        if (!file.exists() || file.length() == 0){
            Toast.makeText(getApplicationContext(), "No saved races ", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("lastRaceOnly", false);
            startActivity(intent);
        }
    }
}