package com.example.gestion_course.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.gestion_course.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var nbParticipants: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_quit.setOnClickListener {
            finishAffinity()
        }

        button_start.setOnClickListener {
            val intent = Intent(this, AddParticipantActivity::class.java)
            intent.putExtra("nbParticipants",nbParticipants)
            startActivity(intent)
        }


        // Gestion de la SeekBar
        seekBar_nbParticipants.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Met à jour le nombre de participants quand la valeur de la seekbar change
                nbParticipants = (progress + 2) * 3
                text_nbParticipants.text = "Participants : $nbParticipants"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
                //Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
    }


}