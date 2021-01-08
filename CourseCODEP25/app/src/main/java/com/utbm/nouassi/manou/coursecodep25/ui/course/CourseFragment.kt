package com.utbm.nouassi.manou.coursecodep25.ui.course

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utbm.nouassi.manou.coursecodep25.CreateCourseActivity
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.AppDatabase
import com.utbm.nouassi.manou.coursecodep25.database.entity.CourseAndEquipe

class CourseFragment : Fragment() {

    private lateinit var _courseRecyclerView: RecyclerView
    private lateinit var myCourseAdapter: CourseAdapter
    private lateinit var _myFloatingActionButton: FloatingActionButton
    private var minCourse: Int = 0
    private var maxCourse: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_course, container, false)

        _courseRecyclerView = root.findViewById( R.id.course_recyclerview )

        _myFloatingActionButton = root.findViewById(R.id.course_fab)
        _myFloatingActionButton.setOnClickListener({ creerCourse() })

        _courseRecyclerView.layoutManager = LinearLayoutManager(context)
        myCourseAdapter = CourseAdapter()
        _courseRecyclerView.adapter = myCourseAdapter

        afficherListeCourse()

        return root
    }

    private fun creerCourse(){
        val intent = Intent(context, CreateCourseActivity::class.java)
        startActivity(intent)
    }

    private fun afficherListeCourse() {
        var listT: ListCourseTask = ListCourseTask()
        listT.execute()
    }

    private inner class ListCourseTask : AsyncTask<Void, Int?, ArrayList<CourseAndEquipe>>() {

        override fun onPostExecute(result: ArrayList<CourseAndEquipe>) {
            super.onPostExecute(result)
            myCourseAdapter.clear()
            myCourseAdapter.addAllCourses(result, minCourse, maxCourse)
        }

        override fun doInBackground(vararg params: Void?): ArrayList<CourseAndEquipe> {
            val db = AppDatabase.getInstance(context as Context)
            val courses : ArrayList<CourseAndEquipe> = db.courseDao().getAll() as ArrayList<CourseAndEquipe>
            minCourse = db.courseDao().getDureeMin()
            maxCourse = db.courseDao().getDureeMax()

            return courses
        }

    }
}