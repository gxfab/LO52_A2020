package com.tps.apptp4


import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /***************Listeners pour le click des boutons UP/DOWN/LEFT/RIGHT***********************/
        buttonUp.setOnClickListener {
            var direction: String = callDirection("UP")  //Lance la fonction C++ et récupère le résultat
            textviewDef?.setText(direction)
        }
        buttonDown.setOnClickListener {
            var direction: String = callDirection("DOWN")
            textviewDef?.setText(direction)
        }
        buttonRight.setOnClickListener {
            var direction: String = callDirection("RIGHT")
            textviewDef?.setText(direction)
        }
        buttonLeft.setOnClickListener {
            var direction: String = callDirection("LEFT")
            textviewDef?.setText(direction)
        }

    }
    external fun callDirection(myButtonName: String) : String     //Declaration fonction C++

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}


