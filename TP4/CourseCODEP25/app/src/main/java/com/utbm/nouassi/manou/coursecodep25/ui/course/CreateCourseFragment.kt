package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.CreateCourseActivity
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.AppDatabase
import com.utbm.nouassi.manou.coursecodep25.database.entity.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [CreateCourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCourseFragment : Fragment() {

    private lateinit var _nomCourseEditText: EditText
    private lateinit var _genererEquipeButton: Button
    private lateinit var _createCourseRecyclerView: RecyclerView
    private lateinit var _startCourseButton: Button
    private lateinit var myEquipeAdapter: EquipeAdapter
    private lateinit var _marqueeTextView: TextView

    private lateinit var course: Course


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_create_course, container, false)

        _nomCourseEditText = view.findViewById(R.id.nom_course_edit_text)
        _genererEquipeButton = view.findViewById(R.id.generer_equipe_button)
        _genererEquipeButton.setOnClickListener({ genererEquipe() })

        _startCourseButton = view.findViewById(R.id.start_course_button)
        _startCourseButton.setOnClickListener({ demarrerCourse() })


        _createCourseRecyclerView = view.findViewById(R.id.create_course_recyclerview)
        _createCourseRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        myEquipeAdapter = EquipeAdapter()
        _createCourseRecyclerView.adapter = myEquipeAdapter

        _marqueeTextView = view.findViewById( R.id.marqueeText )
        _marqueeTextView.setSelected(true)

        return view
    }

    fun genererEquipe(){
            if( _nomCourseEditText.text.toString() == "" ){
                Toast.makeText(context, "Veuillez remplir le nom de la course", Toast.LENGTH_SHORT)
            }
            else{
                var genererEquipeTask = GenererEquipeTask()
                    genererEquipeTask.execute()
            }
    }

    fun demarrerCourse(){
        (activity as CreateCourseActivity?)!!.afficherDemarrageCourse(course.id)
    }


    private inner class GenererEquipeTask : AsyncTask<Void, Int?, ArrayList<EquipeAndCoureur>>() {
        private lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Génération en cours ...")
            progressDialog.setMessage("Chargement en cours, patientez svp")
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void?): ArrayList<EquipeAndCoureur> {
            val db = AppDatabase.getInstance(context as Context)

            var equipeAndCoureur: ArrayList<EquipeAndCoureur> = ArrayList()

            var nbEquipe = 0
            //Liste des participants par ordre décroissant de niveau
            var participants = db.participantDao().getAllOrderNiveauDesc()
            //On aura nbEquipe équipes de 2,3 participants
            nbEquipe = (participants.size - participants.size%2)/2

            //Création de course
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            course = Course(0, _nomCourseEditText.text.toString(), currentDate, 0)
            val idCourse = db.courseDao().insert(course)

            course.id = idCourse.toInt()

            //Création des équipes
            var equipeList = ArrayList<Equipe>()
            for ( i in 1..nbEquipe ){
                var equipe: Equipe = Equipe(0, i.toString(), 0, course.id)
                val idEq = db.equipeDao().insert(equipe)
                    equipe.id = idEq.toInt()
                equipeList.add(equipe)
            }

            //Ajout des participants dans les équipes créée
            for ( participant in participants ){
                //Equipe dans laquel le participant sera enregistré
                var indexEquipeCourante = getMinLevelEquipeIndex(equipeList)
                var equipeCourante = equipeList.get(indexEquipeCourante)
                //Enregistrement de notre coureur
                var coureur = Coureur( participant.nom, participant.niveau, 0, 0, 0, 0, 0, participant.id, equipeCourante.id  )
                db.coureurDao().insert(coureur)
                equipeCourante.niveau = equipeCourante.niveau + participant.niveau
                db.equipeDao().updateEquipes(equipeCourante)
                equipeList.set( indexEquipeCourante, equipeCourante )
            }

            equipeAndCoureur = db.equipeDao().getByCourseId( course.id ) as ArrayList<EquipeAndCoureur>

            return equipeAndCoureur

        }

        fun getMinLevelEquipeIndex(equipes: ArrayList<Equipe>): Int{
            var equipe = equipes.get(0)
            for(eq in equipes){
                if( equipe.niveau > eq.niveau ){
                    equipe = eq
                }
            }
            return equipes.indexOf(equipe)
        }

        override fun onPostExecute(result: ArrayList<EquipeAndCoureur>) {
            super.onPostExecute(result)
            _nomCourseEditText.isEnabled = false
            _genererEquipeButton.isEnabled = false
            _startCourseButton.isEnabled = true

            myEquipeAdapter.clear()
            myEquipeAdapter.addAllEquipes( result )

            progressDialog.dismiss()
        }
    }

}