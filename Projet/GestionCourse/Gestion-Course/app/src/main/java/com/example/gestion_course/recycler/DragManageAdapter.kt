package com.example.gestion_course.recycler

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Classe qui permet d'implémenter le drag and drop pour une RecyclerView
 */
class DragManageAdapter(adapter: EquipeDetailRecycleViewAdapter, context: Context, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    var nameAdapter = adapter

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        nameAdapter.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    //Pas besoin de l'implémenter car pas de swipe (pas besoin de supprimmer un participant)
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }
}