package com.tps.appf1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_createrace.*
import kotlinx.android.synthetic.main.fragment_createteam.*

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

        view.findViewById<Button>(R.id.button_Ajouter).setOnClickListener {
            if(ParticipantName.text.isNotEmpty() && ParticipantSurname.text.isNotEmpty() && ParticipantLevel.text.isNotEmpty())
            {
                //Add partiticipants à la db runner
            }
            else
            {
                Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }

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

    }
}