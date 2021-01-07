package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.AppDatabase
import com.utbm.nouassi.manou.coursecodep25.database.entity.Coureur
import com.utbm.nouassi.manou.coursecodep25.database.entity.Course
import com.utbm.nouassi.manou.coursecodep25.database.entity.EquipeAndCoureur
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "IDCOURSE"

/**
 * A simple [Fragment] subclass.
 * Use the [StartCourseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartCourseFragment : Fragment(), CourseEquipeAdapter.CourseEquipeHandler {
    // TODO: Rename and change types of parameters
    private var courseId: Int? = null

    private lateinit var course: Course
    private lateinit var myEquipeList: List<EquipeAndCoureur>
    private var nbNotify: Int = 0
    private lateinit var _startCourseChronoButton: Button
    private lateinit var _courseStatistiqueButton: Button
    private lateinit var _startCourseChronometer: Chronometer
    private lateinit var _startCourseEquipeRecyclerView: RecyclerView
    private lateinit var _courseResultatEditText: EditText
    private lateinit var _marqueeTextView: TextView


    var isChronometerRunning = false


    private lateinit var myStartCourseEquipeAdapter: CourseEquipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            courseId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_start_course, container, false)

        _startCourseChronoButton = view.findViewById(R.id.start_course_chrono_button)
        _startCourseChronometer = view.findViewById(R.id.start_course_chrono_meter)
        _courseResultatEditText = view.findViewById(R.id.course_resultat)
        _courseStatistiqueButton = view.findViewById(R.id.course_statistiques)

        myStartCourseEquipeAdapter = CourseEquipeAdapter(this)

        _startCourseEquipeRecyclerView = view.findViewById(R.id.start_course_equipe_recyclerview)
        _startCourseEquipeRecyclerView.layoutManager = GridLayoutManager(context, 3)
        _startCourseEquipeRecyclerView.adapter = myStartCourseEquipeAdapter
        _startCourseEquipeRecyclerView.isEnabled = false

        _startCourseChronoButton.setOnClickListener { startCourse() }

        _marqueeTextView = view.findViewById( R.id.marqueeText )
        _marqueeTextView.setSelected(true)

        val dispT = DisplayAllTask()
        dispT.execute()

        return view
    }

    private fun startCourse() {
        _startCourseChronometer.setBase(SystemClock.elapsedRealtime())
        _startCourseChronometer.start()
        _startCourseChronometer.isEnabled = false
        _startCourseEquipeRecyclerView.isEnabled = true
        isChronometerRunning = true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment StartCourseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            StartCourseFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }

        fun deuxCharacter(seconde: Int): String{

            return if(seconde < 10) "0"+seconde.toString() else seconde.toString()
        }

        fun recupererTemps(seconde: Int): String{
            var temps: String
            if( seconde < 60 ){
                temps = "00:"+ deuxCharacter(seconde)
            }
            else{
                val minutes = (seconde - seconde%60)/60
                temps = deuxCharacter(minutes) + ":" + deuxCharacter(seconde%60)
            }
            return temps
        }
    }

    private inner class DisplayAllTask : AsyncTask<Void, Int?, List<EquipeAndCoureur>>() {

        private lateinit var progressDialog: ProgressDialog

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Chargement ...")
            progressDialog.setMessage("Chargement des informations en cours, patientez svp")
            progressDialog.show()
        }

        override fun onPostExecute(result: List<EquipeAndCoureur>) {
            super.onPostExecute(result)
            myStartCourseEquipeAdapter.clear()
            myStartCourseEquipeAdapter.addAllEquipes(result as ArrayList<EquipeAndCoureur>)

            progressDialog.dismiss()
        }

        override fun doInBackground(vararg params: Void?): List<EquipeAndCoureur> {
            val db = AppDatabase.getInstance(context as Context)
            course = db.courseDao().getById(courseId as Int)
            var equipeAndCoureurs: List<EquipeAndCoureur> = db.equipeDao().getByCourseId( course.id )

            myEquipeList = equipeAndCoureurs

            return equipeAndCoureurs
        }

    }

    override fun getCurrentTime(): Long {
        val elapsedMillis: Long = SystemClock.elapsedRealtime() - _startCourseChronometer.getBase()
        return elapsedMillis / 1000
    }

    override fun isStarted(): Boolean {
        return isChronometerRunning
    }

    override fun updateCoureur(coureur: Coureur) {

        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                val db = AppDatabase.getInstance(context as Context)
                db.coureurDao().updateCoureur( coureur )

                return null
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    override fun notifyEnd() {
        nbNotify++
        if( nbNotify == myEquipeList.size ) {
            _startCourseChronometer.stop()
            _courseResultatEditText.setText("TERMINE")
            _courseStatistiqueButton.isEnabled = true
            _courseStatistiqueButton.setOnClickListener({
                val intent = Intent(context, StatistiqueActivity::class.java).apply {
                    putExtra("ID", course.id)
                }
                startActivity(intent)
            })
            isChronometerRunning = false

            object : AsyncTask<Void?, Void?, Void?>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    val db = AppDatabase.getInstance(context as Context)
                    course.duree = getCurrentTime().toInt()
                    db.courseDao().updateCourses(course)

                    return null
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

}