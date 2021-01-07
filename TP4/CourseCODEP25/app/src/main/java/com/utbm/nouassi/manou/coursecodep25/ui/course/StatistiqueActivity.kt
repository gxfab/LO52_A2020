package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.AppDatabase
import com.utbm.nouassi.manou.coursecodep25.database.entity.Coureur
import com.utbm.nouassi.manou.coursecodep25.database.entity.Course
import com.utbm.nouassi.manou.coursecodep25.database.entity.Equipe
import com.utbm.nouassi.manou.coursecodep25.database.entity.EquipeAndCoureur
import org.w3c.dom.Text

class StatistiqueActivity : AppCompatActivity() {

    private lateinit var course: Course
    private var courseId: Int = 0
    private lateinit var equipeAndCoureurs: ArrayList<EquipeAndCoureur>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistique)
        courseId = intent.getIntExtra( "ID", 0 )
        equipeAndCoureurs = ArrayList<EquipeAndCoureur>()

        afficherTout()
    }

    fun afficherTout(){
        val statCTask = SatistiqueCourseTask()
        statCTask.execute()
    }

    private inner class SatistiqueCourseTask : AsyncTask<Void, Int?, ArrayList<EquipeAndCoureur>>() {

        override fun onPostExecute(result: ArrayList<EquipeAndCoureur>) {
            super.onPostExecute(result)
            val _titre = findViewById<TextView>(R.id.statistique_titre_textview)
            _titre.setText( "Statistiques de la course: " + course.titre )

            var _statistiqueLinearLayout = findViewById<LinearLayout>(R.id.statistiques_linearlayout)
            var ligne = 0
            val lparams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.toFloat())

            var dureeM1: Int = course.duree
            var coureurM1: Coureur = Coureur("", 1, 1, 1, 1, 1, 1, 1, 1)
            var equipeM1: Equipe = Equipe(1, "", 1, 1)

            var dureeM2: Int = course.duree
            var coureurM2: Coureur = Coureur("", 1, 1, 1, 1, 1, 1, 1, 1)
            var equipeM2: Equipe = Equipe(1, "", 1, 1)

            var dureeM3: Int = course.duree
            var coureurM3: Coureur = Coureur("", 1, 1, 1, 1, 1, 1, 1, 1)
            var equipeM3: Equipe = Equipe(1, "", 1, 1)

            var dureePitStop: Int = course.duree
            var coureurPitStop: Coureur = Coureur("", 1, 1, 1, 1, 1, 1, 1, 1)
            var equipePitStop: Equipe = Equipe(1, "", 1, 1)

            var dureeMeilleurTour: Int = course.duree
            var typeMeilleurTour : String = ""
            var coureurMeilleurTour: Coureur = Coureur("", 1, 1, 1, 1, 1, 1, 1, 1)
            var equipeMeilleurTour: Equipe = Equipe(1, "", 1, 1)


            for ( eq in result){
                val equipe = eq.equipe
                var equipeLinearLayout = LinearLayout(this@StatistiqueActivity)

                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 2, 0, 10)
                equipeLinearLayout.layoutParams = params

                //Notre équipe
                val textView = TextView(this@StatistiqueActivity)
                val myParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.toFloat())
                textView.gravity = Gravity.CENTER
                textView.setText( "Equipe " + equipe.numero )

                textView.layoutParams = myParams
                equipeLinearLayout.addView( textView )

                var coureursLinearLayout = LinearLayout(this@StatistiqueActivity)
                coureursLinearLayout.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7.toFloat())
                coureursLinearLayout.orientation = LinearLayout.VERTICAL

                var dernierCoureurDuree: Int = 0

                var dureePrecedente = 0

                //Liste des coureurs et leurs statistiques
                for ( coureur in eq.coureurs ){
                    var coureurLigne = LinearLayout(this@StatistiqueActivity)
                    coureurLigne.layoutParams = params
                    coureurLigne.orientation = LinearLayout.HORIZONTAL
                    if( ligne%2 == 0 ){
                        coureurLigne.setBackgroundColor( ContextCompat.getColor(this@StatistiqueActivity, R.color.equipe_statistique_ligne) )
                    }

                    val textViewCoureur: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureur.setLayoutParams(lparams)
                    textViewCoureur.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureur.setText( coureur.nom )
                    coureurLigne.addView(textViewCoureur)

                    //TSA1
                    val textViewCoureurTSA1: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureurTSA1.setLayoutParams(
                        lparams
                    )
                    textViewCoureurTSA1.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureurTSA1.setText( StartCourseFragment.recupererTemps( coureur.nbTourSansAtelier1 ) )
                    coureurLigne.addView(textViewCoureurTSA1)

                    //TAA1
                    val textViewCoureurTAA1: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureurTAA1.setLayoutParams(
                        lparams
                    )
                    textViewCoureurTAA1.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureurTAA1.setText( StartCourseFragment.recupererTemps( coureur.nbTourAvecAtelier1 ) )
                    coureurLigne.addView(textViewCoureurTAA1)

                    //PITSTOP
                    val textViewCoureurPITSTOP: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureurPITSTOP.setLayoutParams(
                        lparams
                    )
                    textViewCoureurPITSTOP.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureurPITSTOP.setText( StartCourseFragment.recupererTemps( coureur.nbPitStop ) )
                    coureurLigne.addView(textViewCoureurPITSTOP)

                    //TSA2
                    val textViewCoureurTSA2: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureurTSA2.setLayoutParams(
                        lparams
                    )
                    textViewCoureurTSA2.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureurTSA2.setText( StartCourseFragment.recupererTemps( coureur.nbTourSansAtelier2 ) )
                    coureurLigne.addView(textViewCoureurTSA2)

                    //TAA2
                    val textViewCoureurTAA2: TextView = TextView(this@StatistiqueActivity)
                    textViewCoureurTAA2.setLayoutParams(
                        lparams
                    )
                    textViewCoureurTAA2.gravity = Gravity.CENTER_HORIZONTAL
                    textViewCoureurTAA2.setText( StartCourseFragment.recupererTemps( coureur.nbTourAvecAtelier2 ) )
                    coureurLigne.addView(textViewCoureurTAA2)


                    dernierCoureurDuree = coureur.nbTourAvecAtelier2
                    coureursLinearLayout.addView(coureurLigne)
                    ligne++

                    if ( dureeM1 >= (coureur.nbTourAvecAtelier2 - dureePrecedente)  ){
                        dureeM3 = dureeM2
                        coureurM3 = coureurM2
                        equipeM3 = equipeM2

                        coureurM2 = coureurM1
                        dureeM2 = dureeM1
                        equipeM2 = equipeM1

                        dureeM1 = (coureur.nbTourAvecAtelier2 - dureePrecedente)
                        coureurM1 = coureur
                        equipeM1 = equipe
                    }

                    if( dureePitStop >= (coureur.nbPitStop - dureePrecedente) ){
                        dureePitStop = (coureur.nbPitStop - dureePrecedente)
                        coureurPitStop = coureur
                        equipePitStop = equipe
                    }

                    if( dureeMeilleurTour >=  coureur.nbTourSansAtelier1 - dureePrecedente)
                    {
                        dureeMeilleurTour = coureur.nbTourSansAtelier1 - dureePrecedente
                        coureurMeilleurTour = coureur
                        equipeMeilleurTour = equipe
                        typeMeilleurTour = "Tour sans atelier 1"
                    }
                    if( dureeMeilleurTour >=  coureur.nbTourAvecAtelier1 - coureur.nbTourSansAtelier1)
                    {
                        dureeMeilleurTour = coureur.nbTourAvecAtelier1 - coureur.nbTourSansAtelier1
                        coureurMeilleurTour = coureur
                        equipeMeilleurTour = equipe
                        typeMeilleurTour = "Tour avec atelier 1"
                    }
                    if( dureeMeilleurTour >=  coureur.nbTourSansAtelier2 - coureur.nbPitStop)
                    {
                        dureeMeilleurTour = coureur.nbTourSansAtelier2 - coureur.nbPitStop
                        coureurMeilleurTour = coureur
                        equipeMeilleurTour = equipe
                        typeMeilleurTour = "Tour sans atelier 2"
                    }
                    if( dureeMeilleurTour >=  coureur.nbTourAvecAtelier2 - coureur.nbTourSansAtelier2)
                    {
                        dureeMeilleurTour = coureur.nbTourAvecAtelier2 - coureur.nbTourSansAtelier2
                        coureurMeilleurTour = coureur
                        equipeMeilleurTour = equipe
                        typeMeilleurTour = "Tour avec atelier 2"
                    }
                    dureePrecedente = coureur.nbTourAvecAtelier2

                }

                equipeLinearLayout.addView(coureursLinearLayout)

                val textViewTotal = TextView(this@StatistiqueActivity)

                textViewTotal.gravity = Gravity.CENTER
                textViewTotal.setText( StartCourseFragment.recupererTemps( dernierCoureurDuree ) )
                textViewTotal.layoutParams = myParams
                equipeLinearLayout.addView( textViewTotal )

                _statistiqueLinearLayout.addView(equipeLinearLayout)
            }

            val textViewM1 = findViewById<TextView>(R.id.statistique_meilleur_cycle1)
            textViewM1.setText( "Meilleur Cycle: " + StartCourseFragment.recupererTemps( dureeM1 ) + "/ Coureur: " + coureurM1.nom + "/ Equipe " + equipeM1.numero )

            val textViewM2 = findViewById<TextView>(R.id.statistique_meilleur_cycle2)
            textViewM2.setText( "2ème meilleur Cycle: " + StartCourseFragment.recupererTemps( dureeM2 ) + "/ Coureur: " + coureurM2.nom + "/ Equipe " + equipeM2.numero )

            val textViewM3 = findViewById<TextView>(R.id.statistique_meilleur_cycle3)
            textViewM3.setText( "3ème meilleur Cycle: " + StartCourseFragment.recupererTemps( dureeM3 ) + "/ Coureur: " + coureurM3.nom + "/ Equipe " + equipeM3.numero )

            val textViewPitStop = findViewById<TextView>(R.id.statistique_meilleur_pitstop)
            textViewPitStop.setText( "Meilleur PitStop: " + StartCourseFragment.recupererTemps( dureePitStop ) + "/ Coureur: " + coureurPitStop.nom + "/ Equipe " + equipePitStop.numero )

            val textViewMeilleurTour = findViewById<TextView>(R.id.statistique_meilleur_tour)
            textViewMeilleurTour.setText(  "Type du meilleur Tour: " + typeMeilleurTour + "/ Duree: " + StartCourseFragment.recupererTemps( dureeMeilleurTour ) + "/ Coureur: " + coureurMeilleurTour.nom + "/ Equipe " + equipeMeilleurTour.numero )
        }

        override fun doInBackground(vararg params: Void?): ArrayList<EquipeAndCoureur> {
            var db = AppDatabase.getInstance(this@StatistiqueActivity)
            course = db.courseDao().getById( courseId )
            equipeAndCoureurs = db.equipeDao().getByCourseId(courseId) as ArrayList<EquipeAndCoureur>


            return equipeAndCoureurs
        }

    }
}