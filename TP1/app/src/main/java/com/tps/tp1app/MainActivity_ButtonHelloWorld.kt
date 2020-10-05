package com.tps.tp1app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity_ButtonHelloWorld : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_button_hello_world)

        val message = "Plop"

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.textView_HelloWorld).apply {
            text = message
    }
}