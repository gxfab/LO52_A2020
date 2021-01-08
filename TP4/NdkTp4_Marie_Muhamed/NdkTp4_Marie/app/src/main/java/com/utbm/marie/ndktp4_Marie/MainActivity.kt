package com.utbm.marie.ndktp4_Marie

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.utbm.marie.ndktp4_Marie.R
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnWrite = findViewById<Button>(R.id.btn_write)
        btnWrite.setOnClickListener { fn_write() }

        val btnRead = findViewById<Button>(R.id.btn_read)
        btnRead.setOnClickListener { fn_read() }

        val btnUp = findViewById<Button>(R.id.btn_up)
        btnUp.setOnClickListener { fn_translate_japonais("UP") }

        val btnDown = findViewById<Button>(R.id.btn_down)
        btnDown.setOnClickListener { fn_translate_japonais("DOWN") }

        val btnLeft = findViewById<Button>(R.id.btn_left)
        btnLeft.setOnClickListener { fn_translate_japonais("LEFT") }

        val btnRight = findViewById<Button>(R.id.btn_right)
        btnRight.setOnClickListener { fn_translate_japonais("RIGHT") }

    }

    fun fn_write(){
        val editDef = findViewById<EditText>(R.id.saisie)
        val result = findViewById<TextView>(R.id.result_textview)
        try {
            val nombre =  editDef.text.toString().toInt()
            val text = write( nombre )
            result.setText( text )
        }
        catch (e: NumberFormatException) {
            result.setText( "Error: Please enter a valid number" )
        }
    }

    fun fn_read(){
        val editDef = findViewById<EditText>(R.id.saisie)
        val result = findViewById<TextView>(R.id.result_textview)
        try {
            val nombre =  editDef.text.toString().toInt()
            val text = read( nombre )
            result.setText( text )
        }
        catch (e: NumberFormatException) {
            result.setText( "Error: Please enter a valid number" )
        }
    }

    fun fn_translate_japonais(mot: String){
        val result = findViewById<TextView>(R.id.result_textview)
        result.setText( translateJaponais(mot) )
    }

    private external fun translateJaponais(direction: String): String
    private external fun write(a: Int): String
    private external fun read(a: Int): String

    companion object {
        // loading the native library'native-lib' when starting the application.
        init {
            System.loadLibrary("native-lib")
        }
    }
}