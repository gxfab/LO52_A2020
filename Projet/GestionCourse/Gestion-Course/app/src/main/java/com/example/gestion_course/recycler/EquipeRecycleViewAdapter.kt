package com.example.gestion_course.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestion_course.R
import com.example.gestion_course.entities.Equipe
import com.example.gestion_course.entities.Participant

/**
 * Classe pour gérér la recyclerview des équipes dans l'écran de réorganisation des équipes
 */
class EquipeRecycleViewAdapter(
    var context: Context,
    var participantList: MutableList<List<Participant>>,
    var equipe: MutableList<Equipe>
) :
        RecyclerView.Adapter<EquipeRecycleViewAdapter.ItemHolder>() {

    var viewpool : RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
                .inflate(R.layout.gridview_layout_equipe, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val listPart: List<Participant> = participantList[position]
        val niveauEquipe = (listPart.map { it.niveau_participant }).sum()

        holder.nomEquipeText.text = equipe[position].nom_equipe + " (" + niveauEquipe + ")"


        /****************************************************************************************************************/
        // Création de la RecyclerView (EquipeDetailRecycleViewAdapter) pour les participants d'une équipe
        val layoutManager = LinearLayoutManager(
            holder.recycleViewEquipeDetail.context,
            RecyclerView.VERTICAL,
            false
        )

        layoutManager.initialPrefetchItemCount = listPart.size


        // Create sub item view adapter
        val equipeDetailRecycleViewAdapter = EquipeDetailRecycleViewAdapter(context, listPart)

        val mDividerItemDecoration = DividerItemDecoration(
            holder.recycleViewEquipeDetail.context,
            layoutManager.orientation
        )
        holder.recycleViewEquipeDetail.addItemDecoration(mDividerItemDecoration)

        holder.recycleViewEquipeDetail.layoutManager = layoutManager
        holder.recycleViewEquipeDetail.adapter = equipeDetailRecycleViewAdapter
        holder.recycleViewEquipeDetail.setRecycledViewPool(viewpool)



        // Setup ItemTouchHelper
        val callback = DragManageAdapter(
            equipeDetailRecycleViewAdapter, context,
            ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.ACTION_STATE_IDLE
        )
        val helper = ItemTouchHelper(callback)


        helper.attachToRecyclerView(holder.recycleViewEquipeDetail)

        /****************************************************************************************************************/

    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nomEquipeText = itemView.findViewById<TextView>(R.id.text_nomEquipe)
        var recycleViewEquipeDetail = itemView.findViewById<RecyclerView>(R.id.recyclerview_equipe_detail)

    }


}