package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.databinding.RaceRowItemBinding
import com.codep25.codep25.databinding.RaceTeamResultsItemBinding
import com.codep25.codep25.databinding.RaceTimeItemBinding
import com.codep25.codep25.databinding.SimpleTimeItemBinding
import com.codep25.codep25.model.entity.RaceTime
import com.codep25.codep25.model.entity.RaceWithTeams
import com.codep25.codep25.model.entity.RacingTeam
import com.codep25.codep25.model.entity.SimpleTime
import com.codep25.codep25.ui.viewholder.RaceTeamResultViewHolder
import com.codep25.codep25.ui.viewholder.RaceTimeViewHolder
import com.codep25.codep25.ui.viewholder.SimpleTimeViewHolder

class SimpleTimeAdapter(
    _times: List<SimpleTime>
) : RecyclerView.Adapter<SimpleTimeViewHolder>() {
    private val times = _times.sortedBy { it.msTime }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleTimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SimpleTimeItemBinding.inflate(inflater, parent, false)
        return SimpleTimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleTimeViewHolder, position: Int) {
        holder.bind(times[position])
    }

    override fun getItemCount() = times.size
}