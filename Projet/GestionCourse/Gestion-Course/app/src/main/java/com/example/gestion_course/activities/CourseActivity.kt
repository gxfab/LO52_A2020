package com.example.gestion_course.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.recycler.CourseRecycleViewAdapter
import com.example.gestion_course.R
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.Etape
import com.example.gestion_course.entities.Participant
import com.example.gestion_course.viewModels.CourseViewModel
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CourseActivity : AppCompatActivity() {

    private lateinit var courseViewModel: CourseViewModel
    private lateinit var courseRecyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var courseAdapter: CourseRecycleViewAdapter
    private lateinit var listEquipes: List<Equipe>
    private lateinit var listEtapes: List<Etape>
    private lateinit var listParticipantsList: List<List<Participant>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        listenStart()

        // Get the viewModel
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)


        //Récupère les informations de la bdd
        runBlocking {
            withContext(Dispatchers.IO) {
                listEquipes = courseViewModel.getEquipes()
                listEtapes = courseViewModel.getEtapes()
                listParticipantsList = courseViewModel.getParticipants(listEquipes.size)
            }
        }
        createRecyclerView()
    }

    //Créer la RecyclerView (grille des équipes)
    private fun createRecyclerView(){
        courseRecyclerView = findViewById(R.id.recyclerview_course)
        gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        courseRecyclerView.layoutManager = gridLayoutManager
        courseRecyclerView.setHasFixedSize(true)
        courseAdapter = CourseRecycleViewAdapter(applicationContext, listEquipes, listParticipantsList, listEtapes)
        courseRecyclerView.adapter = courseAdapter
    }

    private fun listenStart(){
        button_start_stat.setOnClickListener {
            val intent = Intent(this, StatActivity::class.java)
            startActivity(intent)
        }
    }
}