package com.example.gestion_course.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_course.AppDatabase
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.EquipeAvecParticipants
import com.example.gestion_course.entities.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EquipeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var nomEquipeList: MutableList<String>
    private lateinit var prenomList: MutableList<String>
    private var totalNiveau: Int = 0
    private var moyenneNiveauEquipe: Int = 0
    private var moyenneNiveauParticipant: Int = 0
    private var nbTotalParticipants: Int = 0
    private var nbEquipes: Int = 0
    private var participantList = mutableListOf<Participant>()
    private var equipeList = mutableListOf<Equipe>()
    private var listParticipantList = mutableListOf<List<Participant>>()
    var context: Context = getApplication()

    private fun initEquipeList() {
        nomEquipeList = mutableListOf(
                "Les Belfortains",
                "Les chanceux",
                "Team Voyou",
                "Team Fromage",
                "Les Supers",
                "BDS UTBM",
                "Les Guerriers",
                "Club DJT",
                "Team Meat",
                "Les Foufous"
        )
        nomEquipeList.shuffle()
    }

    private fun initPrenomList() {
        prenomList = mutableListOf(
                "Sacha",
                "Marco",
                "Serge",
                "Mateao",
                "Vincent",
                "Lucas",
                "Katia",
                "Sabina",
                "Marianne",
                "Constance",
                "Olivia",
                "Appolline",
                "Stevenson",
                "Fabien",
                "Erwan",
                "Kevin",
                "Killian",
                "Hedi",
                "Frederique",
                "Victoire",
                "Salome",
                "Regis",
                "Remi",
                "Anthony",
                "Jordan",
                "Alida",
                "Gwen",
                "Cecilia",
                "Amelie",
                "Ayoub"
        )
        prenomList.shuffle()
    }

    fun genereEquipes(nbParticipants: Int, prenomParticipantManuel: String, niveauParticipantManuel: Int){
        nbTotalParticipants = nbParticipants
        nbEquipes = nbParticipants/3
        createEquipes(nbParticipants)
        createParticipants(nbParticipants, prenomParticipantManuel, niveauParticipantManuel)
        getTotalNiveau()
        getMoyenneNiveauEquipe()
        getMoyenneNiveauParticipant()
        sortParticipantParNiveau()


        for (i in 1..nbEquipes){
            fillEquipe(i)
        }
        Log.i("complet", listParticipantList.toString())

        for(i in 1..nbEquipes){
            insertEquipeAvecParticipants(equipeList[i-1], listParticipantList[i-1])

        }
    }

    private fun createEquipes(nbParticipants: Int){
        initEquipeList()

        for (i in 1..nbEquipes){
            var equipe: Equipe = Equipe(i, nomEquipeList[i - 1])
            equipeList.add(equipe)
        }
    }

    fun createParticipants(nbParticipants: Int, prenomParticipantManuel: String, niveauParticipantManuel: Int){
        initPrenomList()
        val nbParticipantsGenere = nbParticipants - 1

        for (i in 1..nbParticipantsGenere){
            var part = Participant(i, prenomList[i - 1], Random.nextInt(1, 100), null, null, 6)
            participantList.add(part)
        }

        //On ajoute le participant rentré manuellement
        var part = Participant(nbParticipants, prenomParticipantManuel, niveauParticipantManuel, null, null, 6)
        participantList.add(part)
    }

    fun getTotalNiveau(){
        var listNiveau: List<Int> = participantList.map { it.niveau_participant }
        totalNiveau = listNiveau.sum()
    }

    fun getMoyenneNiveauEquipe(){
        moyenneNiveauEquipe = totalNiveau/nbEquipes
    }

    fun getMoyenneNiveauParticipant(){
        moyenneNiveauParticipant = totalNiveau/nbTotalParticipants
    }

    fun sortParticipantParNiveau(){
        participantList.sortBy { it.niveau_participant }
    }

    // Faire cette fonction là autant de fois que le nombre d'équipes
    fun fillEquipe(numEquipe: Int){
        var equipe = mutableListOf<Participant>()
        equipe.add(participantList.removeLast())

        while (equipe.size < 3){
            var listNiveauEquipe: List<Int> = equipe.map { it.niveau_participant }
            if (equipeFort(listNiveauEquipe.sum())){
                equipe.add(participantList.removeFirst())
            } else {
                equipe.add(participantList.removeLast())
            }
        }
        for (i in 1..equipe.size){
            equipe[i-1].num_equipe_participant = numEquipe
            equipe[i-1].ordre_passage = i
        }
        listParticipantList.add(equipe)

        var listNiveauTest: List<Int> = equipe.map { it.niveau_participant }
        Log.i("Niveau equipe", listNiveauTest.sum().toString())
    }

    private fun equipeFort(niveauTotalEquipe: Int): Boolean{
        return niveauTotalEquipe > moyenneNiveauEquipe
    }

    fun getEquipes(): MutableList<List<Participant>>{
        return listParticipantList
    }

    fun getNomEquipes(): MutableList<Equipe>{
        return equipeList
    }

    private fun getDatabase(): AppDatabase{
        return AppDatabase.getInstance(context)
    }

    fun clearDatabase(){
        val database = getDatabase()
        database.clearTables()
    }

    fun clearTemps(){
        val database = getDatabase()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database.tempsDao().deleteData()
            }

        }
    }

    suspend fun insertEtapes(){
        val database = getDatabase()
        database.etapeDao().insertAllEtapes()
    }


    fun insertEquipeAvecParticipants(equipe: Equipe, participants: List<Participant>){
        val database = getDatabase()
        viewModelScope.launch {
            database.equipeDao().insertEquipeAvecParticipants(equipe, participants)
        }
    }


}