package com.utbm.nouassi.manou.coursecodep25.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.utbm.nouassi.manou.coursecodep25.CreateCourseActivity
import com.utbm.nouassi.manou.coursecodep25.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val comButton = root.findViewById<Button>(R.id.commencer_button)
        comButton.setOnClickListener({
            val intent = Intent(context, CreateCourseActivity::class.java)
            startActivity(intent)
        })

        return root
    }
}