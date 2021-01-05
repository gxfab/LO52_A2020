package com.mguichar.viertel_tp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    private native String giveMeOneDirection(String str);
    private native String write(int i);
    private native String read(int i);
    TextView translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Project Variables
        TextView output = findViewById(R.id.def_output);
        EditText input = findViewById(R.id.def_input);

        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button up = findViewById(R.id.up);
        Button down = findViewById(R.id.down);
        Button read = findViewById(R.id.read);
        Button write = findViewById(R.id.write);

        translation = findViewById(R.id.translation);


        // Source code for the read and write function
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().equals("")) {

                    int n = Integer.parseInt(input.getText().toString());
                    if (n >= 0 && n <= 10)
                        output.setText(String.valueOf(write(n)));
                    else
                        Toast.makeText(MainActivity.this, "Daignez entrer une valeur comprise en 0 et 10, inclus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().equals("")) {

                    int n = Integer.parseInt(input.getText().toString());
                    if (n >= 0 && n <= 10)
                        output.setText(String.valueOf(read(n)));
                    else
                        Toast.makeText(MainActivity.this, "Daignez entrer une valeur comprise en 0 et 10, inclus", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Buttons code
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRANSLATION("LEFT");
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRANSLATION("RIGHT");
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRANSLATION("UP");
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TRANSLATION("DOWN");
            }
        });

    }

    private void TRANSLATION(String direction) {

        String translatedDirection = giveMeOneDirection(direction);
        translation.setText(translatedDirection);
    }
}
