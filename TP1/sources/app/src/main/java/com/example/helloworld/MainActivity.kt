package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Naviguate to the Hello Activity in response to button
     */
    fun naviguateToHello(view: View) {
        val intent = Intent(this, HelloActivity::class.java)
        startActivity(intent)
    }
}