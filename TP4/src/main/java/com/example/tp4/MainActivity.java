package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    public void clique(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        TextView tv = findViewById(R.id.champSaisie);
        tv.setText(displayDirection(buttonText));
    }

    public void readAndWrite(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        TextView tv = findViewById(R.id.champSaisie);
        Random rand = new Random();
        int generatedNumber = rand.nextInt(10 - 0 + 1) + 0;
        if(buttonText.equals("Read")){tv.setText(displayRead(generatedNumber));}
        if(buttonText.equals("Write")){tv.setText(displayWrite(generatedNumber));}
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String displayDirection(String buttonName);
    public native String displayRead(int number);
    public native String displayWrite(int number);
}
