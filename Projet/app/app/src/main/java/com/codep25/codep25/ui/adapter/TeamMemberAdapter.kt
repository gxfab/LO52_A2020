package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.databinding.TeamMemberBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.Participant
import com.codep25.codep25.viewmodel.TeamManagementViewModel
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [TeamManagementViewModel].
 */
class TeamMemberAdapter(
    private val colors: ParticipantColors,
    participants: List<Participant>
) :
    RecyclerView.Adapter<TeamMemberAdapter.ViewHolder>()
{

    private val teamMembers: MutableList<Participant> = participants.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TeamMemberBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = teamMembers[position]

        holder.participantNameView.text = item.name
        holder.participantOrderView.text = (position + 1).toString()
        holder.participantImage.setColorFilter(colors.getColorOrBlack(position))
    }

    override fun getItemCount(): Int = teamMembers.size

    fun swapParticipants(from: Int, to: Int) {
        Collections.swap(teamMembers, from, to)
        notifyItemMoved(from, to)
    }

    inner class ViewHolder(binding: TeamMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        val cardView: CardView = binding.card
        val participantNameView: TextView = binding.participantNameTextView
        val participantOrderView: TextView = binding.orderTextView
        val participantImage: ImageView = binding.participantImg

        override fun toString(): String {
            return super.toString() + " '" + participantNameView.text + "'"
        }
    }
}

