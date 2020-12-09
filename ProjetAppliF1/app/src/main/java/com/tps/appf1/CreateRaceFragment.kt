package com.tps.appf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_createrace.*
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateRaceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createrace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var date = LocalDateTime.now()
        //Use autofillhint ? -> convertir date en string et la récupérer pour la mettre dans le champ

        view.findViewById<Button>(R.id.button_CreateRace2).setOnClickListener {
            findNavController().navigate(R.id.action_CreateRaceFragment_to_CreateTeamFragment)
        }
    }
}