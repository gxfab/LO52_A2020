package com.codep25.codep25.ui.viewholder

import android.graphics.Color
import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.RaceRowItemBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.RacingState
import com.codep25.codep25.model.entity.RacingTeam

class RaceTeamViewHolder(
    private val binding: RaceRowItemBinding
) :  RecyclerView.ViewHolder(binding.root) {

    var onClick: ((RacingTeam) -> Unit)? = null
    private var team: RacingTeam? = null

    fun bind(team: RacingTeam, elapsedTime: Long, colors: ParticipantColors) {
        this.team = team

        if (team.isFinished) {
            bindArrivedRaceTeam(team)
        } else {
            bindRunningRaceTeam(team, elapsedTime, colors)
        }
    }

    private fun bindRunningRaceTeam(team: RacingTeam, elapsedTime: Long, colors: ParticipantColors) {
        with(binding) {
            root.setOnClickListener { onClick?.invoke(team) }

            updateElapsedTime(elapsedTime)

            teamNameTextView.text = team.team.name
            participantName.text = team.currentParticipant.name

            raceNbTurnTextView.text = team.cycleNumber.toString()

            participantName.visibility = View.VISIBLE
            raceNbTurnTextView.visibility = View.VISIBLE

            progressBar.progress = team.progress

            when(team.currentState) {
                RacingState.SPRINT -> raceImage.setImageResource(R.drawable.ic_sprint)
                RacingState.HURDLE -> raceImage.setImageResource(R.drawable.ic_hurdle_race)
                RacingState.PIT_STOP -> raceImage.setImageResource(R.drawable.ic_pit_stop)
            }
            raceImage.setColorFilter(colors.getColorOrBlack(team.currentParticipantPos))
        }
    }

    private fun bindArrivedRaceTeam(team: RacingTeam) {
        with(binding) {
            root.setOnClickListener { onClick?.invoke(team) }

            teamNameTextView.text = team.team.name
            progressBar.progress = team.progress
            raceItemChronometer.text = DateUtils.formatElapsedTime(
                    team.totalRaceTime / 1000
            )

            participantName.visibility = View.INVISIBLE
            raceNbTurnTextView.visibility = View.INVISIBLE
            raceImage.setImageResource(R.drawable.ic_finish_race)
            raceImage.setColorFilter(Color.BLACK)
        }
    }

    fun updateElapsedTime(elapsedTime: Long) {
        if ((team?.isFinished == true))
            return

        binding.raceItemChronometer.text = DateUtils.formatElapsedTime(
            (elapsedTime - (team?.totalRaceTime ?: 0)) / 1000
        )
    }
}