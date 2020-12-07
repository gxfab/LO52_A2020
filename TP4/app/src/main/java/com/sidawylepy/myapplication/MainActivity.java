package com.sidawylepy.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private int getUserNumber(){
        return Integer.parseInt (((EditText)findViewById(R.id.editTextNumber)).getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(nativeRead(5));
    }

    public void btnClickRead(View view) {
        int number = getUserNumber();
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(nativeRead(number));
    }

    public void btnClickWrite(View view) {
        int number = getUserNumber();
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(nativeWrite(number));
    }

    public void btnClickDirection(View view) {
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(nativeJapaneseButton (((Button)view).getText().toString()));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String nativeRead(int number);
    public native String nativeWrite(int number);
    public native String nativeJapaneseButton(String button);

}
