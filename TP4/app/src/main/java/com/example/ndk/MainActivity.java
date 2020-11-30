package com.example.ndk;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv_jap;
    EditText te_def;
    TextView tv_read;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    private native String direction(String str);
    private native String  write(int i);
    private native String  read(int i);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv_read = findViewById(R.id.textView_read);
        tv_jap = findViewById(R.id.textView_jap);
        te_def = findViewById(R.id.editText_def);
        Button b_left = findViewById(R.id.button_left);
        Button b_right = findViewById(R.id.button_right);
        Button b_up = findViewById(R.id.button_up);
        Button b_down = findViewById(R.id.button_down);
        Button b_read = findViewById(R.id.button_read);
        Button b_write = findViewById(R.id.button_write);

        b_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateDirection("LEFT");
            }
        });
        b_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateDirection("RIGHT");
            }
        });
        b_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateDirection("UP");
            }
        });
        b_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateDirection("DOWN");
            }
        });

        b_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(te_def != null) {
                    int i = Integer.parseInt(te_def.getText().toString());
                    if (i >= 0 && i <= 10) {
                        tv_read.setText(String.valueOf(write(i)));
                    } else {
                        Toast.makeText(MainActivity.this, "Erreur: La valeur doit être entre 0 et 10.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        b_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(te_def != null) {
                    int i = Integer.parseInt(te_def.getText().toString());
                    if (i >= 0 && i <= 10) {
                        tv_read.setText(String.valueOf(read(i)));
                    } else {
                        Toast.makeText(MainActivity.this, "Erreur: La valeur doit être entre 0 et 10.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void translateDirection(String str) {
        String translated = direction(str);
        tv_jap.setText(translated);
    }
}

