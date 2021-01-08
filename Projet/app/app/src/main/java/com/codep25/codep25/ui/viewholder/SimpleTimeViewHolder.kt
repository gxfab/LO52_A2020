package com.codep25.codep25.ui.viewholder

import android.text.format.DateUtils
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.R
import com.codep25.codep25.databinding.RaceTimeItemBinding
import com.codep25.codep25.databinding.SimpleTimeItemBinding
import com.codep25.codep25.model.entity.RaceTime
import com.codep25.codep25.model.entity.RacingState
import com.codep25.codep25.model.entity.SimpleTime

class SimpleTimeViewHolder(
    private val binding: SimpleTimeItemBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(time: SimpleTime) {
        binding.apply {
            textView.text = time.name
            chronoTextView.text = DateUtils.formatElapsedTime(time.msTime / 1000)
        }
    }
}