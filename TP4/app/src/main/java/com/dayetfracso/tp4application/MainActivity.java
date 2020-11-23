package com.dayetfracso.tp4application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
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

    public void onClickOnRead(View view) {
        EditText textEntryField = findViewById(R.id.textEntryField);
        String value = textEntryField.getText().toString();
        int numericValue = value.equals("")  ? 5 : Integer.parseInt(value);

        if(numericValue >= 0 && numericValue<=10) {
            TextView textViewRead = findViewById(R.id.readLabel);
            textViewRead.setText("Read : " + calculateSquare(numericValue));
        }
        else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "La valeur doit être comprise entre 0 et 10", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickOnWrite(View view) {
        EditText textEntryField = findViewById(R.id.textEntryField);
        String value = textEntryField.getText().toString();
        int numericValue = value.equals("")  ? 5 : Integer.parseInt(value);

        if(numericValue >= 0 && numericValue<=10) {
            TextView textViewWrite = findViewById(R.id.writeLabel);
            textViewWrite.setText("Write : " + calculateCube(numericValue));
        }
        else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "La valeur doit être comprise entre 0 et 10", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClickOnUp(View view) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, getJapaneseEquivalent(0), Toast.LENGTH_LONG);
            toast.show();
    }

    public void onClickOnDown(View view) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, getJapaneseEquivalent(1), Toast.LENGTH_LONG);
        toast.show();
    }

    public void onClickOnRight(View view) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, getJapaneseEquivalent(2), Toast.LENGTH_LONG);
        toast.show();
    }

    public void onClickOnLeft(View view) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, getJapaneseEquivalent(3), Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    public native String calculateSquare(int value);

    public native String calculateCube(int value);

    public native String getJapaneseEquivalent(int direction);
}