package com.example.gestion_course.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.recycler.EquipeRecycleViewAdapter
import com.example.gestion_course.R
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.Participant
import com.example.gestion_course.viewModels.EquipeViewModel
import kotlinx.android.synthetic.main.activity_equipe.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class EquipeActivity : AppCompatActivity() {

    private lateinit var equipeViewModel: EquipeViewModel
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var equipeAdapter: EquipeRecycleViewAdapter? = null
    private var listEquipeParticipant= mutableListOf<List<Participant>>()
    private var listEquipe = mutableListOf<Equipe>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipe)


        // Get the viewModel
        equipeViewModel = ViewModelProvider(this).get(EquipeViewModel::class.java)

        // Récupère les informations de l'activité précédente
        val nbParticipants = intent.getIntExtra("nbParticipants", 9)
        val niveauParticipantManuel = intent.getIntExtra("niveauParticipant", 1)
        val prenomParticipanManuel = intent.getStringExtra("prenomParticipant")

        //Nettoie la bdd pour ne pas garder les données d'une course précédente
        equipeViewModel.clearDatabase()
        runBlocking {
            withContext(Dispatchers.IO){
                equipeViewModel.insertEtapes()
            }
        }


        // Génère les équipes avec l'algorithme
        equipeViewModel.genereEquipes(nbParticipants, prenomParticipanManuel, niveauParticipantManuel)

        var listParticipantList = equipeViewModel.getEquipes()
        for (i in 1..listParticipantList.size){
            listEquipeParticipant.add(listParticipantList[i - 1])
        }

        listEquipe = equipeViewModel.getNomEquipes()

        createRecyclerView()

        listenStart()
    }

    private fun createRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview_equipe)
        gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        equipeAdapter = EquipeRecycleViewAdapter(applicationContext, listEquipeParticipant!!, listEquipe)
        recyclerView?.adapter = equipeAdapter
    }

    private fun listenStart(){
        button_start_race.setOnClickListener {
            val intent = Intent(this, CourseActivity::class.java)
            //intent.putExtra("nbParticipants",nbParticipants)
            startActivity(intent)
        }
    }

    // Efface les temps lorsque les temps déjà enregistrés si il y en a
    override fun onResume() {
        super.onResume()
        runBlocking {
            withContext(Dispatchers.IO){
                equipeViewModel.clearTemps()
            }

        }
    }
}