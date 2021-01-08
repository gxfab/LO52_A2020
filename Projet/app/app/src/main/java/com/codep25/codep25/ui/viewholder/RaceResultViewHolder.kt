package com.codep25.codep25.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.databinding.RaceResultItemBinding
import com.codep25.codep25.model.entity.Race
import java.text.DateFormat
import java.util.*

class RaceResultViewHolder(
    private val binding: RaceResultItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    var onClick: ((Race) -> Unit)? = null

    fun bind(race: Race) {
        binding.dateTextView.text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
            .format(Date(race.date))

        binding.root.setOnClickListener { onClick?.invoke(race) }
    }
}