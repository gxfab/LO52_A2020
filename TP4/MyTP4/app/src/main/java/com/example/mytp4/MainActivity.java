package com.example.mytp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String [] s = {"0","1","2","3","4","5","6","7","8","9","10"};
    boolean randomBoolean;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch sw = (Switch) findViewById(R.id.mySwitch);
        sw.setChecked(false);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sw.isChecked())
                    randomBoolean = true;
                else
                    randomBoolean = false;
            }
        });
    }

    public void directionListener(View view) {
        Button b = (Button)view;
        String buttonValue = b.getText().toString();
        TextView tv = (TextView)findViewById(R.id.textArea);
        tv.setText(directionInJapanese(buttonValue));
    }

    public void listener(View view) {
        TextView tv = (TextView)findViewById(R.id.textArea);
        int a;
        String s = tv.getText().toString();
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        if(!randomBoolean) {
            if(isCorrect(s)){
                a=Integer.parseInt(s);
                tv.setText(calculate(a,buttonText.equals("READ")));
            }
            else
                tv.setText("Saisie incorrecte");
        }
        else
        {
            Random random = new Random();
            a = random.nextInt(11);
            tv.setText(calculate(a,buttonText.equals("READ")));
        }
    }

    public boolean isCorrect(String a){
        boolean bool = false;
        for(int i=0;i<11;i++){
            if(a.equals(s[i]))
                bool = true;
        }
        return bool;
    }



    /**
     * Here are the native methods
     * They are implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    private native String directionInJapanese(String buttonValue);
    private native String calculate(int a, boolean b);
}