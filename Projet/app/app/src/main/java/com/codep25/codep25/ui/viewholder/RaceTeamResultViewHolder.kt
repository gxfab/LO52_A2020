package com.codep25.codep25.ui.viewholder

import android.text.format.DateUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.databinding.RaceTeamResultsItemBinding
import com.codep25.codep25.model.entity.RacingTeam
import com.codep25.codep25.ui.adapter.RaceTimeAdapter

class RaceTeamResultViewHolder(
    private val binding: RaceTeamResultsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(raceTeam: RacingTeam) {
        binding.apply {
            raceTeamNameTextView.text = raceTeam.team.name
            raceTeamTimeTextView.text = DateUtils.formatElapsedTime(raceTeam.totalRaceTime / 1000)
        }

        binding.timesRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = RaceTimeAdapter(raceTeam.times)
        }
    }
}