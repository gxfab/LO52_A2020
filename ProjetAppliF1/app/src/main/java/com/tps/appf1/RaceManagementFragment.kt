package com.tps.appf1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.tps.appf1.databases.race.runners.RunnerDatabase
import com.tps.appf1.databases.race.runners.RunnerEntity

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RaceManagementFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_racemanagement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* To access the db from other fragment. Make sur to use the right name "runners-db", otherwise it will create another one */
        //var db = Room.databaseBuilder(this.requireContext(), RunnerDatabase::class.java, "runners-db").allowMainThreadQueries().build()
        //var runner: RunnerEntity = RunnerEntity()
        //runner.RunnerName = "TestFromOtherFrag"
        //db.runnerDatabaseDao().insertRunner(runner)


        //view.findViewById<Button>(R.id.button).setOnClickListener {
        //    findNavController().navigate(R.id.action_SetOrderFragment_to_RaceManagementFragment)
        //}

    }
}