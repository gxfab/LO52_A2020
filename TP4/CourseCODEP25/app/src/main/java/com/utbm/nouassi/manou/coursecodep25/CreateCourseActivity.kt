package com.utbm.nouassi.manou.coursecodep25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utbm.nouassi.manou.coursecodep25.ui.course.CreateCourseFragment
import com.utbm.nouassi.manou.coursecodep25.ui.course.StartCourseFragment

class CreateCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)

        val CreateCourseFragment =
                                    CreateCourseFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.create_course_framelayout, CreateCourseFragment)
            //.addToBackStack(null)
            .commit()
    }

    fun afficherDemarrageCourse( courseId: Int ){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.create_course_framelayout, StartCourseFragment.newInstance(courseId))
                .commit()
    }
}