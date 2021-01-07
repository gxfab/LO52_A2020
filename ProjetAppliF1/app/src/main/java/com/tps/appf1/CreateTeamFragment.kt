package com.tps.appf1

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.tps.appf1.databases.race.runners.RunnerDAO
import com.tps.appf1.databases.race.runners.RunnerDatabase
import com.tps.appf1.databases.race.runners.RunnerEntity
import com.tps.appf1.databases.race.teams.TeamDatabase
import com.tps.appf1.databases.race.teams.TeamEntity
import kotlinx.android.synthetic.main.fragment_createrace.*
import kotlinx.android.synthetic.main.fragment_createteam.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateTeamFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createteam, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Create runners db
        var runnersDB = Room.databaseBuilder(this.requireContext(), RunnerDatabase::class.java, "runners-db").allowMainThreadQueries().build()

        //Empty the runners db, only useful for demonstration as the project is not mature.
        //The runners db should later be linked with the race db
        //runnersDB.runnerDatabaseDao().deleteALL()

        //Create runner entity to add rows in the db
        var runner: RunnerEntity = RunnerEntity()

        view.findViewById<Button>(R.id.button_Ajouter).setOnClickListener {
            //Check if not empty fields
            if(ParticipantName.text.isNotEmpty() && ParticipantSurname.text.isNotEmpty() && ParticipantLevel.text.isNotEmpty())
            {
                //Add partiticipants à la runners db
                runner.RunnerName = ParticipantName.text.toString()
                runner.RunnerSurname = ParticipantSurname.text.toString()
                runner.RunnerLevel = Integer.parseInt(ParticipantLevel.text.toString()) //Conversion editable to int
                runnersDB.runnerDatabaseDao().insertRunner(runner) //Ajoute l'objet runner à la db

                //Incremente le nombre de participants en comptant le nb de rows dans la db
                var x: Int = runnersDB.runnerDatabaseDao().count()
                NbParticipants.text = x.toString()

                //Puis vide les champs pour éviter de dupliquer l'ajout dans la db
                view.findViewById<EditText>(R.id.ParticipantName).setText("")
                view.findViewById<EditText>(R.id.ParticipantSurname).setText("")
                view.findViewById<EditText>(R.id.ParticipantLevel).setText("")
            }
            else
            {
                Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.button_Terminer).setOnClickListener {
            //vérifie que le nb total de partiticipant est multiple de 3 grâce au modulo 3
            var reste: Int = Integer.parseInt(NbParticipants.text.toString())%3
            if(reste == 0 /*&& NbParticipants.text != "00"*/)
            {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Le programme va maintenant créer les équipes. Voulez-vous continuer ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui") { dialog, id ->
                        //create db Team and Team entity
                        var teamsDB = Room.databaseBuilder(this.requireContext(), TeamDatabase::class.java, "teams-db").allowMainThreadQueries().build()
                        var team: TeamEntity = TeamEntity()

                        //Recup level list from runners db
                      /*  var levelList: MutableList<Int> = mutableListOf()
                        levelList.addAll(runnersDB.runnerDatabaseDao().getLevelList()) */
                        //
                        //
                        //Puis algorithme de création d'équipe here (Voir mutableList sur android developers)
                        //1er element = index 0
                        //levelList.remove(15) = remove number "15" from list
                        //levelList.first() = first element
                        //levelList.last() = last element
                     /*   team.TeamRunner1 = runnersDB.runnerDatabaseDao().getRunnerID(levelList.first())  //Get ID of the runner by passing his level. If there is multiples same levels, random pick
                        team.TeamRunner1Level = levelList.first()      //Set the Runner 1 Level
                        team.TeamRunner2
                        team.TeamRunner2Level
                        team.TeamRunner3
                        team.TeamRunner3Level
                        teamsDB.teamDatabaseDao().insert(team) */ //Ajoute la team 1 dans la db
                        //...etc
                        //Peut-etre qu'il faudrait faire un fichier dedier à cette fonction parce que ca va faire beaucoup de ligne
                        //
                        //(Ecran de chargement selon le temps que ca prend)
                        //
                        //Add équipes finales à la db
                        findNavController().navigate(R.id.action_CreateTeamFragment_to_SetOrderFragment)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        //Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
            else
            {
                Toast.makeText(context, "Veuillez rentrer suffisamment de participants pour faire des équipes de 3", Toast.LENGTH_SHORT).show()
            }

        }

    }
}