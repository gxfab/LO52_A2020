package com.codep25.codep25.ui.viewholder

import android.text.format.DateUtils
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.RaceTimeItemBinding
import com.codep25.codep25.model.entity.RaceTime
import com.codep25.codep25.model.entity.RacingState

class RaceTimeViewHolder(
    private val binding: RaceTimeItemBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(time: RaceTime) {
        binding.apply {
            participantNameTextView.text = time.participant.name
            chronoTextView.text = DateUtils.formatElapsedTime(time.timeMs / 1000)
            stateTextView.setText(when (time.state) {
                RacingState.SPRINT -> R.string.sprint_state
                RacingState.PIT_STOP -> R.string.pit_stop_state
                RacingState.HURDLE -> R.string.hurdle
            })
        }
    }
}