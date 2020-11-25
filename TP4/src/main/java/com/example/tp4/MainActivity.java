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
        //TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    public void OnWrite(View v){
        TextView TextV = findViewById(R.id.affichageSaisie);
        Random RandomNum = new Random();
        int generatedNumber = RandomNum.nextInt(10 - 0 + 1) + 0;
        TextV.setText(WriteFunction(generatedNumber));

    }
    public void OnRead(View v){
        TextView TextV = findViewById(R.id.affichageSaisie);
        Random RandomNum = new Random();
        int generatedNumber = RandomNum.nextInt(10 - 0 + 1) + 0;
        TextV.setText(ReadFunction(generatedNumber));

    }
    public void OnClick(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        TextView TextV = findViewById(R.id.affichageSaisie);
        TextV.setText(DirectionFunction(buttonText));
    }

    public native String DirectionFunction(String buttonName);
    public native String ReadFunction(int numberGenerated);
    public native String WriteFunction(int numberGenerated);
}
