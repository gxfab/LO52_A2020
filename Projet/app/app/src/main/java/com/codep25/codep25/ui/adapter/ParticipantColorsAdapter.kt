package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codep25.codep25.databinding.ParticipantColorItemBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.ui.viewholder.ParticipantColorViewHolder

class ParticipantColorsAdapter: RecyclerView.Adapter<ParticipantColorViewHolder>() {

    private val colorList = ArrayList<ParticipantColorViewHolder.ParticipantColor>()
    var onClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParticipantColorItemBinding.inflate(inflater, parent, false)
        return ParticipantColorViewHolder((binding)).apply {
            onClick = this@ParticipantColorsAdapter.onClick
        }
    }

    override fun onBindViewHolder(holder: ParticipantColorViewHolder, position: Int) {
        holder.bind(colorList[position])
    }

    override fun getItemCount() = colorList.size

    fun getColors(colorFactory: ParticipantColors.Factory) : ParticipantColors {
        val colors = ArrayList<Int>(colorList.size)

        for (i in 0 until colorList.size)
            colors.add(0)

        for (c in colorList) {
            colors[c.position] = c.color
        }

        return colorFactory.fromList(colors)
    }

    fun setColors(participantColors: ParticipantColors) {
        colorList.clear()
        colorList.addAll(
            participantColors.toColorArray().mapIndexed { index, color ->
                ParticipantColorViewHolder.ParticipantColor(
                    color,
                    index
                ) }
        )

        notifyDataSetChanged()
    }

    fun setColorForPos(pos: Int, color: Int) {
        colorList[pos] = ParticipantColorViewHolder.ParticipantColor(color, pos)
        notifyItemChanged(pos)
    }
}