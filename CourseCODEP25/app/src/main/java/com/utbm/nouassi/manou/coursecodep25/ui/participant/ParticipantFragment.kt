package com.utbm.nouassi.manou.coursecodep25.ui.participant

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.AppDatabase
import com.utbm.nouassi.manou.coursecodep25.database.entity.Participant


class ParticipantFragment : Fragment(), ParticipantAdapter.ParticipantHandler {

    //private lateinit var participantViewModel: ParticipantViewModel
    public lateinit var nomEditText: EditText
    private lateinit var niveauEditText: EditText
    private lateinit var addParticipantButton: Button
    private lateinit var participantRecyclerView: RecyclerView
    private lateinit var myAdapter: ParticipantAdapter

    private lateinit var addSyncTask: AjoutParticipantTask

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_participant, container, false)
        nomEditText = root.findViewById(R.id.nom_edit_text)
        niveauEditText = root.findViewById(R.id.niveau_edit_text)
        participantRecyclerView = root.findViewById(R.id.participant_recyclerview)
        participantRecyclerView.layoutManager = LinearLayoutManager(context)
        myAdapter = ParticipantAdapter(this)
        participantRecyclerView.adapter = myAdapter

        addSyncTask = AjoutParticipantTask()

        addParticipantButton = root.findViewById(R.id.add_participant_button)
        addParticipantButton.setOnClickListener({ ajouterParticipant() })

        afficherListeParticipant()

        return root
    }

    fun afficherListeParticipant(){
        val listP = ListParticipantTask()
        listP.execute()
    }

    fun ajouterParticipant(){
        if( nomEditText.text.toString() == "" || niveauEditText.text.toString() == "" ){
            Toast.makeText(context, "Veuillez remplir le/les champs vides.", Toast.LENGTH_SHORT).show()
        }
        else{
            if( addSyncTask.status != AsyncTask.Status.RUNNING ){
                addSyncTask = AjoutParticipantTask()
                addSyncTask.execute()
            }
            else{
                Toast.makeText(context, "Ajout déjà en cours d'exécution, veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private inner class ListParticipantTask : AsyncTask<Void, Int?, ArrayList<Participant>>() {

        override fun onPostExecute(result: ArrayList<Participant>) {
            super.onPostExecute(result)
            myAdapter.clear()
            myAdapter.addParticipants(result)
        }

        override fun doInBackground(vararg params: Void?): ArrayList<Participant> {
            val db = AppDatabase.getInstance(context as Context)
            val participants : ArrayList<Participant> = db.participantDao().getAll() as ArrayList<Participant>

            return participants
        }

    }

    private inner class AjoutParticipantTask : AsyncTask<Void, Int?, Participant>() {

        override fun doInBackground(vararg params: Void?): Participant {
            val db = AppDatabase.getInstance(context as Context)
            val participant = Participant( 0, nomEditText.text.toString(), Integer.parseInt(niveauEditText.text.toString())  )
            db.participantDao().insertAll(participant)

            var coureurs = db.coureurDao().getByParticipantId( participant.id )
            for ( coureur in coureurs ){
                db.coureurDao().delete(coureur)
            }

            return participant
        }

        override fun onPostExecute(result: Participant) {
            super.onPostExecute(result)

            myAdapter.addParticipant(result)
            nomEditText.setText("")
            niveauEditText.setText("")

            Toast.makeText(context, "le participant a été correctement ajouté.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun deleteParticipant(id: Int) {
        var dpT = DeleteParticipantTask()
        dpT.execute( id )
    }

    private inner class DeleteParticipantTask : AsyncTask<Int, Int?, Participant>() {
        override fun doInBackground(vararg params: Int?): Participant {
            val db = AppDatabase.getInstance(context as Context)
            val id = params[0]
            var part = db.participantDao().getById(id as Int)
            db.participantDao().delete(id as Int)

            return part
        }

        override fun onPostExecute(result: Participant) {
            super.onPostExecute(result)

            myAdapter.removeParticipant(result)
        }
    }
}