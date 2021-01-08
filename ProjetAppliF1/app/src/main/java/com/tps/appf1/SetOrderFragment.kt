package com.tps.appf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.tps.appf1.databases.race.teams.TeamDatabase
import kotlinx.android.synthetic.main.fragment_setorder.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SetOrderFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setorder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_LaunchRace).setOnClickListener {
            findNavController().navigate(R.id.action_SetOrderFragment_to_RaceManagementFragment)
        }

        /* TODO("Récupérer la liste des équipes et l'afficher dans le recyclerView" +
                "Au clic sur une équipe, afficher ses membres et modifier leur ordre de passage dans l'équipe" +
                "Gérer l'ordre de passage des équipes") */
    }
}