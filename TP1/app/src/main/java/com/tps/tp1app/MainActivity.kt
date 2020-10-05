package com.tps.tp1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //Se déclenche au click du bouton
    fun buttonHelloWorldClick(view: View) {

        //Les intents sont nécessaires pour ouvrir une nouvelle activité. Ici il ne sert qu'à ça
        val intent = Intent(this, MainActivity_ButtonHelloWorld::class.java).apply {}
        //Lance l'activité fille avec l'intent comme lien à l'activité parent
        startActivity(intent)
    }
}