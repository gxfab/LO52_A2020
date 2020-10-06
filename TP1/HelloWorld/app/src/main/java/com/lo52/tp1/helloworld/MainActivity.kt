package com.lo52.tp1.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun afficher(view : View){
        val intent = Intent(this, HelloWordActivity::class.java)
        startActivity(intent)
    }
}