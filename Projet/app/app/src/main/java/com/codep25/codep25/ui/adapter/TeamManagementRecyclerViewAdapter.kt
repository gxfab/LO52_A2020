package com.codep25.codep25.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentTeamManagementBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.ui.viewholder.TeamManagementViewHolder
import com.codep25.codep25.viewmodel.TeamManagementViewModel

/**
 * [RecyclerView.Adapter] that can display a [TeamManagementViewModel].
 */
class TeamManagementRecyclerViewAdapter(
    private val colors: ParticipantColors
) :
    RecyclerView.Adapter<TeamManagementViewHolder>()
{

    private val teamsList: SortedList<TeamWithParticipants> = SortedList(
        TeamWithParticipants::class.java,
        object : SortedListAdapterCallback<TeamWithParticipants>(this) {
            override fun areItemsTheSame(t1: TeamWithParticipants, t2: TeamWithParticipants) =
                t1.team.id_team == t2.team.id_team

            override fun compare(t1: TeamWithParticipants, t2: TeamWithParticipants) =
                t1.team.id_team.toString().compareTo(t2.team.id_team.toString(), true)

            override fun areContentsTheSame(t1: TeamWithParticipants, t2: TeamWithParticipants) =
                t1.team == t2.team && t1.participants == t2.participants
        }
    )

    var onTeamOrderChanged: ((TeamWithParticipants) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamManagementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentTeamManagementBinding.inflate(inflater, parent, false)
        return TeamManagementViewHolder(binding, colors).apply {
            onSwapped = {t, from, to -> this@TeamManagementRecyclerViewAdapter.onSwapped(t, from, to)}
        }
    }

    override fun onBindViewHolder(holder: TeamManagementViewHolder, position: Int) {
        val item = teamsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = teamsList.size()

    private fun onSwapped(team: TeamWithParticipants, fromPos: Int, toPos: Int) {
        val ind = teamsList.indexOf(team)
        if (ind == -1)
            return

        val newTeamOrdering = team.swapOrdered(fromPos, toPos)
        teamsList.updateItemAt(ind, newTeamOrdering)
        onTeamOrderChanged?.invoke(newTeamOrdering)
    }

    fun setTeams(teams: List<TeamWithParticipants>) {
        teamsList.clear()
        teamsList.addAll(teams)
        notifyDataSetChanged()
    }
}

