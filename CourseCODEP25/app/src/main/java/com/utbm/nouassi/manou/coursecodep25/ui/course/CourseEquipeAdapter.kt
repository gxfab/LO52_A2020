package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Insert
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.entity.Coureur
import com.utbm.nouassi.manou.coursecodep25.database.entity.Equipe
import com.utbm.nouassi.manou.coursecodep25.database.entity.EquipeAndCoureur
import java.util.ArrayList

class CourseEquipeAdapter (private var equipeCourseList: ArrayList<EquipeAndCoureur>, private var myHandler: CourseEquipeHandler) :
        RecyclerView.Adapter<CourseEquipeAdapter.ViewHolder>() {

    interface CourseEquipeHandler {
        fun getCurrentTime(): Long
        fun updateCoureur(coureur: Coureur)
        fun notifyEnd()
        fun isStarted(): Boolean
    }

    constructor(): this(ArrayList<EquipeAndCoureur>(), StartCourseFragment()){
    }

    constructor(cEH: CourseEquipeHandler): this(ArrayList<EquipeAndCoureur>(), StartCourseFragment()){
        equipeCourseList = ArrayList()
        myHandler = cEH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.course_equipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return equipeCourseList.size
    }

    fun addAllEquipes(equipes: ArrayList<EquipeAndCoureur>){
        equipeCourseList.addAll(equipes)
        notifyDataSetChanged()
    }

    fun clear(){
        equipeCourseList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( equipeCourseList.get(position) )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var _courseEquipeTableLayout = view.findViewById<TableLayout>(R.id.course_equipe_tableLayout)
        private var nbAction: Int = 0

        init {
            _courseEquipeTableLayout.setBackgroundColor( ContextCompat.getColor(view.context, R.color.equipe_background) )
        }

        fun bind(get: EquipeAndCoureur) {
            var equipe = get.equipe
            get.coureurs.forEach{
                val tableRow: View = LayoutInflater.from( _courseEquipeTableLayout.context ).inflate( R.layout.equipe_tablerow, null, false )
                var _titreTextView = tableRow.findViewById<TextView>(R.id.equipe_tablerow_titre)

                _titreTextView.setText( it.nom + " - niveau = "+ it.niveau.toString() )

                _courseEquipeTableLayout.addView(tableRow)
            }
            val tableRow: View = LayoutInflater.from( _courseEquipeTableLayout.context ).inflate( R.layout.equipe_tablerow, null, false )


            var _titreTextView = tableRow.findViewById<TextView>(R.id.equipe_tablerow_titre)
            val spanString = SpannableString( "Equipe " + equipe.numero + ": " + equipe.niveau  )
            spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
            _titreTextView.setText(spanString)
            _courseEquipeTableLayout.addView(tableRow)

            _courseEquipeTableLayout.setOnClickListener {  validerTour(get) }

        }

        fun validerTour(get: EquipeAndCoureur){
            if( myHandler.isStarted() ){
                var modulo = nbAction%5 //nIeme action du coureur à la position "positionCoureur"
                var positionCoureur = (nbAction - modulo)/5

                var coureur = get.coureurs.get(positionCoureur)

                if( modulo == 0 ){//Tour sans Atelier 1
                    coureur.nbTourSansAtelier1 = myHandler.getCurrentTime().toInt()
                }
                else if(modulo == 1){//Tour avec Atelier 1
                    coureur.nbTourAvecAtelier1 = myHandler.getCurrentTime().toInt()
                }
                else if(modulo == 2){//PitStop
                    coureur.nbPitStop = myHandler.getCurrentTime().toInt()
                }
                else if(modulo == 3){//Tour sans Atelier 2
                    coureur.nbTourSansAtelier2 = myHandler.getCurrentTime().toInt()
                }
                else if(modulo == 4){//Tour avec Atelier 2
                    val nbSecond = myHandler.getCurrentTime().toInt()

                    if( get.coureurs.indexOf( coureur ) == get.coureurs.lastIndex ){
                        //On est sur la dernière action de l'équipe car dernier coureur
                        _courseEquipeTableLayout.isEnabled = false
                        _courseEquipeTableLayout.setBackgroundColor( Color.GRAY )
                        myHandler.notifyEnd()
                    }

                    coureur.nbTourAvecAtelier2 = nbSecond
                }

                myHandler.updateCoureur(coureur)

                nbAction ++

                afficher(get)
            }
            else{
                Toast.makeText(_courseEquipeTableLayout.context, "Veuillez cliquer sur démarrer avant de valider une équipe", Toast.LENGTH_LONG)
            }
        }

        fun afficher(get: EquipeAndCoureur){
            var equipe = get.equipe

            var _titreTextView =  _courseEquipeTableLayout.getChildAt(get.coureurs.size).findViewById<TextView>(R.id.equipe_tablerow_titre)
                var eqpInfo = "Equipe " + equipe.numero
                if ( get.coureurs.get(get.coureurs.lastIndex).nbTourAvecAtelier2 > 0 ){
                    eqpInfo += "--" + StartCourseFragment.recupererTemps( get.coureurs.get(get.coureurs.lastIndex).nbTourAvecAtelier2 )
                }
            val spanString = SpannableString( eqpInfo )
            spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
            _titreTextView.setText(spanString)

            var counter = 0
            get.coureurs.forEach{
                var _titreTextView =  _courseEquipeTableLayout.getChildAt(counter).findViewById<TextView>(R.id.equipe_tablerow_titre)

                var modulo = nbAction%5 //nIeme action du coureur à la position "positionCoureur"
                var positionCoureur = (nbAction - modulo)/5


                val spanString : SpannableString

                if( counter == positionCoureur ){
                    val texte: String = "-->" + it.nom + " - niveau = "+ it.niveau.toString()
                    spanString =  SpannableString( texte )
                    spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
                    _titreTextView.isEnabled = true
                    val coureur = get.coureurs.get(counter)
                    if( coureur.nbTourAvecAtelier2 != 0  ){
                        _titreTextView.setTextColor(Color.GREEN)
                    }
                    else if( coureur.nbTourSansAtelier2 != 0  ){
                        _titreTextView.setTextColor(Color.BLUE)
                    }
                    else if( coureur.nbPitStop != 0  ){
                        _titreTextView.setTextColor(Color.YELLOW)
                    }
                    else if( coureur.nbTourAvecAtelier1 != 0  ){
                        _titreTextView.setTextColor(Color.RED)
                    }
                    else if( coureur.nbTourSansAtelier1 != 0  ){
                        _titreTextView.setTextColor(Color.MAGENTA)
                    }
                }
                else{
                    val texte: String = it.nom + " - niveau = "+ it.niveau.toString()
                    spanString =  SpannableString( texte )
                    _titreTextView.setTextColor(Color.BLACK)
                    _titreTextView.isEnabled = false
                }

                _titreTextView.setText( spanString )

                counter++
            }

        }
    }
}