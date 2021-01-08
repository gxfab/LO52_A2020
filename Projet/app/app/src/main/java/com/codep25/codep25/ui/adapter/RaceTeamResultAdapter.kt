package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.databinding.RaceRowItemBinding
import com.codep25.codep25.databinding.RaceTeamResultsItemBinding
import com.codep25.codep25.model.entity.RaceWithTeams
import com.codep25.codep25.model.entity.RacingTeam
import com.codep25.codep25.ui.viewholder.RaceTeamResultViewHolder

class RaceTeamResultAdapter : RecyclerView.Adapter<RaceTeamResultViewHolder>() {
    private val teamList: SortedList<RacingTeam> = SortedList(
        RacingTeam::class.java,
        object : SortedListAdapterCallback<RacingTeam>(this) {
            override fun areItemsTheSame(p1: RacingTeam, p2: RacingTeam)
                    = p1.team.id_team == p2.team.id_team

            override fun compare(p1: RacingTeam, p2: RacingTeam)
                    = p1.totalRaceTime.compareTo(p2.totalRaceTime)

            override fun areContentsTheSame(p1: RacingTeam, p2: RacingTeam)
                    = p1.team.name.compareTo(p2.team.name, true) == 0

        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceTeamResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RaceTeamResultsItemBinding.inflate(inflater, parent, false)
        return RaceTeamResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RaceTeamResultViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    override fun getItemCount() = teamList.size()

    fun setRace(race: RaceWithTeams) {
        teamList.clear()
        teamList.addAll(race.teams)
        notifyDataSetChanged()
    }
}