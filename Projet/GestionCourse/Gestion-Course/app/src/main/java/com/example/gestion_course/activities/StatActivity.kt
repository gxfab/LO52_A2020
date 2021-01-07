package com.example.gestion_course.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.R
import com.example.gestion_course.recycler.StatRecycleViewAdapter
import com.example.gestion_course.entities.Participant
import com.example.gestion_course.viewModels.StatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StatActivity : AppCompatActivity() {

    private lateinit var statRecyclerView: RecyclerView
    private lateinit var statViewModel: StatViewModel
    private lateinit var listParticipants: List<Participant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        statViewModel = ViewModelProvider(this).get(StatViewModel::class.java)

        // Récupère les participants
        runBlocking {
            withContext(Dispatchers.IO) {
                listParticipants = statViewModel.getParticipants()
            }
        }

        createRecyclerView()
    }

    private fun createRecyclerView(){
        statRecyclerView = findViewById(R.id.recyclerview_stat)
        val layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        statRecyclerView.layoutManager = layoutManager
        statRecyclerView.setHasFixedSize(true)
        val statAdapter = StatRecycleViewAdapter(applicationContext, listParticipants)
        statRecyclerView.adapter = statAdapter
    }
}