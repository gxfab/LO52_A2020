package fr.utbm.handlendk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    //UI Component
    TextView tv;
    EditText input;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv = findViewById(R.id.text);
        input = findViewById(R.id.input);
        tv.setText(stringFromJNI());

        //use findByView to retrieve button
        tv = findViewById(R.id.text);
        tv.setText(stringFromJNI());

        //Handle Button and listenning clicking
        Button down = findViewById(R.id.btdown);
        down.setOnClickListener(this);
        Button left = findViewById(R.id.btleft);
        left.setOnClickListener(this);
        Button right = findViewById(R.id.btright);
        right.setOnClickListener(this);
        Button up = findViewById(R.id.btup);
        up.setOnClickListener(this);
        Button read = findViewById(R.id.btread);
        read.setOnClickListener(this);
        Button write = findViewById(R.id.btwrite);
        write.setOnClickListener(this);
    }

    //Business method
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btup: {
               tv.setText(direction("UP"));
                break;
            }
            case R.id.btdown: {
                tv.setText(direction("DOWN"));
                break;
            }
            case R.id.btright: {
                tv.setText(direction("RIGHT"));
                break;
            }
            case R.id.btleft: {
                tv.setText(direction("LEFT"));
                break;
            }
            case R.id.btread: {
                if(!input.getText().toString().equalsIgnoreCase("def")) {
                    Log.d("app", read(Integer.valueOf(input.getText().toString())));
                    tv.setText(read(Integer.valueOf(input.getText().toString())));
                }
                break;
            }
            case R.id.btwrite: {
                if(!input.getText().toString().equalsIgnoreCase("def")) {
                    tv.setText(write(Integer.valueOf(input.getText().toString())));
                }
                break;
            }
        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private native String stringFromJNI();
    private native String direction(String direction);
    private native String write(int a);
    private native String read(int a);
}
