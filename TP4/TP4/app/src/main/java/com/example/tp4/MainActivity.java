package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

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
        /*TextView tv = findViewById(R.id.non);
        tv.setText(stringFromJNI());*/
    }

    public void clique(View v){
        Button b = (Button)v;
        int button_id = b.getId();
        String button_name = v.getResources().getResourceName(button_id);
        String[] separated = button_name.split("/");
        String buttonText =separated[separated.length-1];
        Log.d("myTag", "This is my message :"+buttonText);

        TextView tv = findViewById(R.id.input_tv);
        if( b.getId() == R.id.Write){
            int randnb = new Random().nextInt(10+1);
            tv.setText(dispWandR(buttonText,randnb));
        }
        else if(b.getId() == R.id.Read){
            String tvValue = tv.getText().toString();
            try {
                if (!tvValue.equals("")) {
                    int num1 = Integer.parseInt(tvValue);
                    if (num1 >= 0 && num1 <= 10) {
                        tv.setText(dispWandR(buttonText, num1));
                    }
                    else{
                        Toast toast = Toast.makeText(this, "Input needs to be between 0 and 10", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(this, "Input is empty", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            catch(NumberFormatException NFe){
                Toast toast = Toast.makeText(this, "Input not corresponding to integer", Toast.LENGTH_LONG);
                toast.show();
            }

        }
        else{
            tv.setText(dispDirection(buttonText));
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String dispDirection(String buttonName);
    public native String dispWandR(String buttonName,int number);
}