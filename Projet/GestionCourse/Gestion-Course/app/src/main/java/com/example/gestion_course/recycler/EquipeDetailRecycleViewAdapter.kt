package com.example.gestion_course.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.AppDatabase
import com.example.gestion_course.R
import com.example.gestion_course.entities.Participant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * Classe pour gérer la RecyclerView des participants d'une équipe dans l'écran de réorganisation des équipes
 */
class EquipeDetailRecycleViewAdapter(var context: Context, var participantList: List<Participant>) : RecyclerView.Adapter<EquipeDetailRecycleViewAdapter.ItemHolder>() {

    // Liste des participants à utiliser
    var participantArrayList = participantList.toCollection(ArrayList())
    val database = AppDatabase.getInstance(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.gridview_layout_equipe_detail, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return participantList.size
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val part: Participant = participantList[position]

        holder.prenom.text = part.nom_participant
        holder.niveau.text = part.niveau_participant.toString()
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var prenom = itemView.findViewById<TextView>(R.id.text_prenom_detail)
        var niveau = itemView.findViewById<TextView>(R.id.text_niveau_detail)
    }


    /**
     * Function called to swap dragged items
     */
    fun swapItems(fromPosition: Int, toPosition: Int) {

        //Change l'ordre dans notre liste des participants
        Collections.swap(participantArrayList, fromPosition, toPosition);

        // On met à jour chaque participant de la liste avec le bon ordre de passage
        for(i in 1..participantArrayList.size){
            participantArrayList[i-1].ordre_passage = i
        }

        // On met à jour la bdd
        GlobalScope.launch {
            val test = database.participantDao().updateList(participantArrayList)
        }

        //Obligatoire pour permettre à l'utilisateur le drag and drop
        notifyItemMoved(fromPosition, toPosition)
    }
}