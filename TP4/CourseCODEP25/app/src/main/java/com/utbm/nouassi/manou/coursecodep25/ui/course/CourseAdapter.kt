package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.entity.CourseAndEquipe
import java.util.ArrayList

class CourseAdapter(private var courseList: ArrayList<CourseAndEquipe>, private var minCourse: Int = 0, private var maxCourse: Int = 0) :
        RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    constructor(): this(ArrayList<CourseAndEquipe>()){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun delete( course: CourseAndEquipe ){
        courseList.remove(course)
    }

    fun clear(){
        courseList.clear()
    }

    fun addAllCourses(courses: ArrayList<CourseAndEquipe>, min: Int, max: Int){
        courseList.addAll(courses)

        minCourse = min
        maxCourse = max

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList.get(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var _titreCourseTextView = view.findViewById<TextView>(R.id.titre_course_textview)
        private var _nbEquipeCourseTextView = view.findViewById<TextView>(R.id.nb_equipe_course_textview)
        private var _dateCourseTextView = view.findViewById<TextView>(R.id.date_course_textview)
        private var _dureeCourseTextView = view.findViewById<TextView>(R.id.duree_course_textview)

        private var view = view

        fun bind(course: CourseAndEquipe ){
            _titreCourseTextView.setText( course.course.titre )
            _nbEquipeCourseTextView.setText( course.equipes.size.toString() + " équipes" )
            _dateCourseTextView.setText( course.course.date )
            val duree : Int = course.course.duree

            if ( duree == minCourse as Int ){
                _dureeCourseTextView.setTextColor( Color.RED )
            }
            else if( duree == maxCourse as Int ){
                _dureeCourseTextView.setTextColor( Color.GREEN )
            }
            else{
                _dureeCourseTextView.setTextColor( Color.BLUE )
            }

            _dureeCourseTextView.setText( " (" + StartCourseFragment.recupererTemps(duree) + ")" )

            view.setOnClickListener({
                val intent = Intent(view.context, StatistiqueActivity::class.java).apply {
                    putExtra("ID", course.course.id)
                }
                view.context.startActivity(intent)
            })

        }
    }
}