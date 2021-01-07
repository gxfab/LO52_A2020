package com.example.gestion_course.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.R
import com.example.gestion_course.entities.Participant

/**
 * Classe pour gérer la recyclerview dans l'écran des statistiques (pas terminé)
 */
class StatRecycleViewAdapter(var context: Context, var listParticipants: List<Participant>) : RecyclerView.Adapter<StatRecycleViewAdapter.ItemHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_participant_stat, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.i("liste participants : ",listParticipants.toString())
        val participant: Participant = listParticipants[position]
        holder.nomParticipant.text = participant.nom_participant
    }

    override fun getItemCount(): Int {
        return listParticipants.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nomParticipant = itemView.findViewById<TextView>(R.id.text_participant_list_stat)

    }
}