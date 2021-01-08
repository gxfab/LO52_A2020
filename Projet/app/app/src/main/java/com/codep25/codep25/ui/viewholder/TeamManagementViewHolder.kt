package com.codep25.codep25.ui.viewholder

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentTeamManagementBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.ui.adapter.TeamMemberAdapter

class TeamManagementViewHolder(
    private val binding: FragmentTeamManagementBinding,
    private val colors: ParticipantColors
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: TeamMemberAdapter
    private val helper: ItemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            private var fromPos = -1
            private var toPos = -1

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fP = viewHolder.adapterPosition
                if (fromPos == -1)
                    fromPos = fP

                toPos = target.adapterPosition
                adapter.swapParticipants(fP, toPos)
                return true // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                if (fromPos == -1 || toPos == -1)
                    return

                adapter.notifyDataSetChanged()
                team?.let {
                    onSwapped?.invoke(it, fromPos, toPos)
                }

                fromPos = -1
                toPos = -1
            }
        })

    var onSwapped: ((TeamWithParticipants, Int, Int) -> Unit)? = null
    private var team: TeamWithParticipants? = null

    init {
        helper.attachToRecyclerView(binding.list)
    }

    fun bind(team: TeamWithParticipants) {
        adapter = TeamMemberAdapter(colors, team.orderedParticipants)
        this.team = team

        binding.apply {
            teamTextView.text = teamTextView.context.getString(
                R.string.fragment_team_name_title, team.team.name, team.level
            )

            list.apply {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                adapter = this@TeamManagementViewHolder.adapter
            }
        }
    }

    override fun toString(): String {
        return super.toString() + " '" + binding.teamTextView.text + "'"
    }
}