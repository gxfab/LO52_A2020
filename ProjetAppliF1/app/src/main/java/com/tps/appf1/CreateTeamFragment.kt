package com.tps.appf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateTeamFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createteam, container, false)

        //create the runners db
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_Terminer).setOnClickListener {
            //vérifie que le nb total de partiticipant est multiple de 3,
            //Si oui petit disclaimer "l'app va maintenant crééer les équipes...etc" pour être sur
            //Puis crééer teams db
            //Puis algorithme de création d'équipe here :
            //
            //(Ecran de chargement selon le temps que ca prend)
            //
            //Add équipes finales à la db
            findNavController().navigate(R.id.action_CreateTeamFragment_to_SetOrderFragment)
        }

        view.findViewById<Button>(R.id.button_Ajouter).setOnClickListener {
            //Check if tous les champs sont rempli puis add partiticipant à la db runner
        }
    }
}