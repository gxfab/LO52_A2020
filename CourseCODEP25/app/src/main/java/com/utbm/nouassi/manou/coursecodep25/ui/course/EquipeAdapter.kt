package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.entity.CourseAndEquipe
import com.utbm.nouassi.manou.coursecodep25.database.entity.EquipeAndCoureur
import java.util.*


class EquipeAdapter (private var equipeCourseList: ArrayList<EquipeAndCoureur>) :
    RecyclerView.Adapter<EquipeAdapter.ViewHolder>() {

    constructor(): this(ArrayList<EquipeAndCoureur>()){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_equipe_item, parent, false)
        return EquipeAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return equipeCourseList.size
    }

    override fun onBindViewHolder(holder: EquipeAdapter.ViewHolder, position: Int) {
        holder.bind( equipeCourseList.get(position) )
    }

    fun addAllEquipes(equipes: ArrayList<EquipeAndCoureur>){
        equipeCourseList.addAll(equipes)
        notifyDataSetChanged()
    }

    fun clear(){
        equipeCourseList.clear()
        notifyDataSetChanged()
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var _courseEquipeTableLayout = view.findViewById<TableLayout>(R.id.course_equipe_tableLayout)


        fun bind(get: EquipeAndCoureur) {
            var equipe = get.equipe
            val tableRow: View = LayoutInflater.from( _courseEquipeTableLayout.context ).inflate( R.layout.equipe_tablerow, null, false )


            var _titreTextView = tableRow.findViewById<TextView>(R.id.equipe_tablerow_titre)
            val spanString = SpannableString( "Equipe " + equipe.numero + ": " + equipe.niveau  )
            spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
                _titreTextView.setText(spanString)
            _courseEquipeTableLayout.addView(tableRow)
            get.coureurs.forEach{
                val tableRow: View = LayoutInflater.from( _courseEquipeTableLayout.context ).inflate( R.layout.equipe_tablerow, null, false )
                var _titreTextView = tableRow.findViewById<TextView>(R.id.equipe_tablerow_titre)

                _titreTextView.setText( it.nom + " - niveau = "+ it.niveau.toString() )

                _courseEquipeTableLayout.addView(tableRow)
            }

        }

    }
}