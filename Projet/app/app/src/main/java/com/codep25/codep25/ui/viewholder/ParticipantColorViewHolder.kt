package com.codep25.codep25.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.ParticipantColorItemBinding
import com.codep25.codep25.model.color.ParticipantColors

class ParticipantColorViewHolder(
  private val binding: ParticipantColorItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    data class ParticipantColor(
        val color: Int,
        val position: Int
    )

    var onClick: ((Int) -> Unit)? = null

    fun bind(participantColor: ParticipantColor) {
        val ctx = binding.root.context

        with(binding) {
            participantColorTextview.text = ctx.getString(
                R.string.participant_color_item_text,
                participantColor.position
            )

            participantImg.setColorFilter(
                participantColor.color
            )

            root.setOnClickListener { _ -> onClick?.invoke(participantColor.position) }
        }
    }
}