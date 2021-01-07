package com.tps.appf1

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_racemanagement.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RaceManagementFragment : Fragment() {
    var counter: Int = 0

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


        view.findViewById<Button>(R.id.button_StartChrono).setOnClickListener {
            counter += 1    //Incremente counter pour différencier le premier click et le reste
            if (counter == 1)
            {
                //init race chronometer
                val raceChronometer: Chronometer = view.findViewById<Chronometer>(R.id.raceChronometer)
                raceChronometer.base = SystemClock.elapsedRealtime()

                //init team 1 chronometer
                val team1Chronometer: Chronometer = view.findViewById<Chronometer>(R.id.team1Chronometer)
                team1Chronometer.base = SystemClock.elapsedRealtime()

                //run the chronometers
                raceChronometer.start()
                team1Chronometer.start()
                button_StartChrono.text = "Split"
            }
            else
            {
                //Recupère le temps des ateliers à chaque clic
            }
        }
        //}

    }
}