package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.databinding.RaceRowItemBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.ParticipantWithTeam
import com.codep25.codep25.model.entity.RaceConfig
import com.codep25.codep25.model.entity.RacingTeam
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.ui.viewholder.RaceTeamViewHolder

class RaceAdapter(
    private val colors: ParticipantColors
) : RecyclerView.Adapter<RaceTeamViewHolder>() {

    private val teamList: SortedList<RacingTeam> = SortedList(
        RacingTeam::class.java,
        object : SortedListAdapterCallback<RacingTeam>(this) {
            override fun areItemsTheSame(p1: RacingTeam, p2: RacingTeam)
                    = p1.team.id_team == p2.team.id_team

            override fun compare(p1: RacingTeam, p2: RacingTeam)
                    = p1.team.name.compareTo(p2.team.name, true)

            override fun areContentsTheSame(p1: RacingTeam, p2: RacingTeam)
                    = p1.team.name.compareTo(p2.team.name, true) == 0

        }
    )

    private var lastChrono: Long = 0
    private val boundHolders = HashSet<RaceTeamViewHolder>()

    var onClick: ((RacingTeam) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceTeamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RaceRowItemBinding.inflate(inflater, parent, false)
        return RaceTeamViewHolder(binding).apply {
            onClick = this@RaceAdapter.onClick
        }
    }

    override fun onBindViewHolder(holder: RaceTeamViewHolder, position: Int) {
        holder.bind(teamList[position], lastChrono, colors)
        boundHolders.add(holder)
    }

    override fun onViewRecycled(holder: RaceTeamViewHolder) {
        super.onViewRecycled(holder)
        boundHolders.remove(holder)
    }

    override fun getItemCount() = teamList.size()

    fun setTeams(teams: List<RacingTeam>) {
        teamList.clear()
        teamList.addAll(teams)
        notifyDataSetChanged()
    }

    fun onChronometer(elapsed: Long) {
        lastChrono = elapsed

        for (h in boundHolders)
            h.updateElapsedTime(elapsed)
    }
}