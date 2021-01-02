package com.tps.apptp4


import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //Recupère les views du layout

        /***************Listeners pour le click des boutons UP/DOWN/LEFT/RIGHT et READ/WRITE***********************/
        buttonUp.setOnClickListener {
            var direction: String = callDirection("UP")  //Lance la fonction C++ et récupère le résultat
            textViewDirection.text = direction
        }
        buttonDown.setOnClickListener {
            var direction: String = callDirection("DOWN")
            textViewDirection.text = direction
        }
        buttonRight.setOnClickListener {
            var direction: String = callDirection("RIGHT")
            textViewDirection.text = direction
        }
        buttonLeft.setOnClickListener {
            var direction: String = callDirection("LEFT")
            textViewDirection.text = direction
        }
        buttonRead.setOnClickListener {
            if (textViewNumber.text.isNotEmpty())
            {
                var number: Int = Integer.parseInt(textViewNumber.text.toString()); //Convertion Editable to Int
                var read: String = callMultiplication(number, "READ")
                textViewDirection.text = read
            }
            else
            {
                textViewDirection.text = "Error, editText is empty";
            }
        }
        buttonWrite.setOnClickListener {
            if (textViewNumber.text.isNotEmpty())
            {
                var number: Int = Integer.parseInt(textViewNumber.text.toString());
                var write: String = callMultiplication(number, "WRITE")
                textViewDirection.text = write
            }
            else
            {
                textViewDirection.text = "Error, editText is empty";
            }
        }

    }
    external fun callDirection(myButtonName: String) : String     //Declaration fonctions C++
    external fun callMultiplication(myNumber: Int, myButtonName: String) : String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}


