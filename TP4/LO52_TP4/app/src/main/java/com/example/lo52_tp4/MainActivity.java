package com.example.lo52_tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonUp(View view) {
        displayInLabel(upInJapanese());
    }

    public void buttonDown(View view) {
        displayInLabel(downInJapanese());
    }

    public void buttonLeft(View view) {
        displayInLabel(leftInJapanese());
    }

    public void buttonRight(View view) {
        displayInLabel(rightInJapanese());
    }

    public void buttonRead(View view) {
        EditText editText = findViewById(R.id.editText);
        try {
            int value = Integer.parseInt(editText.getText().toString());
            if (value < 0 || value > 10) {
                Toast toast = Toast.makeText(this, "Error : Number must be between 0 and 10", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                TextView tv = findViewById(R.id.label);
                String message = "Read : " + value + "*" + value + " = " + read(value);
                tv.setText(message);
            }
        } catch(NumberFormatException e) {
            Toast toast = Toast.makeText(this, "Error : Enter a number", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void buttonWrite(View view) {
        EditText editText = findViewById(R.id.editText);
        try {
            int value = Integer.parseInt(editText.getText().toString());
            if (value < 0 || value > 10) {
                Toast toast = Toast.makeText(this, "Error : Number must be between 0 and 10", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                TextView tv = findViewById(R.id.label);
                String message = "Write : " + value + "*" + value + "*" + value + " = " + write(value);
                tv.setText(message);
            }
        } catch(NumberFormatException e) {
            Toast toast = Toast.makeText(this, "Error : Enter a number", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void displayInLabel(String message) {
        TextView tv = findViewById(R.id.label);
        tv.setText(message);
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String upInJapanese();
    public native String downInJapanese();
    public native String leftInJapanese();
    public native String rightInJapanese();
    public native String read(int value);
    public native String write(int value);
}
