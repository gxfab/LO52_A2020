package com.codep25.codep25.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.ParticipantRowItemBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.Participant
import com.codep25.codep25.model.entity.ParticipantWithTeam

class ParticipantViewHolder(private val binding: ParticipantRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    data class SelectableParticipant(
        val participant: ParticipantWithTeam,
        val isSelected: Boolean
    )

    var onItemClick: ((SelectableParticipant) -> Unit)? = null
    var onLongItemClick: ((SelectableParticipant) -> Unit)? = null
    var onItemSelectedChange: ((Long, Boolean) -> Unit)? = null

    fun bind(participant: SelectableParticipant, colors: ParticipantColors, showSelect: Boolean) {
        val ctx = binding.root.context

        binding.apply {
            root.setOnClickListener {
                if (!showSelect)
                    onItemClick?.invoke(participant)
                else {
                    selectButton.isChecked = !selectButton.isChecked
                }
            }

            selectButton.setOnCheckedChangeListener { _, isChecked ->
                onItemSelectedChange?.invoke(participant.participant.id, isChecked)
            }

            root.setOnLongClickListener {
                onLongItemClick?.invoke(participant)
                true
            }

            participantImg.setColorFilter(colors.getColorOrBlack(participant.participant.position - 1))
            nameTextView.text = participant.participant.name
            levelTextView.text = ctx.getString(R.string.participant_row_item_level_txt, participant.participant.level)
            teamTextView.text = ctx.getString(R.string.participant_row_item_team_txt, participant.participant.teamName)
            positionTextView.text = ctx.getString(
                R.string.participant_row_item_team_position_txt,
                if (participant.participant.position != ParticipantWithTeam.POSITION_UNKNOWN)
                    participant.participant.position.toString()
                else
                    "-"
            )
            selectButton.isChecked = participant.isSelected

            if (showSelect)
                selectButton.visibility = View.VISIBLE
            else
                selectButton.visibility = View.GONE
        }
    }
}