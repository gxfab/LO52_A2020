package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.databinding.ParticipantRowItemBinding
import com.codep25.codep25.databinding.RaceResultItemBinding
import com.codep25.codep25.model.entity.ParticipantWithTeam
import com.codep25.codep25.model.entity.Race
import com.codep25.codep25.ui.viewholder.RaceResultViewHolder

class RaceResultAdapter : RecyclerView.Adapter<RaceResultViewHolder>() {
    private val raceList: SortedList<Race> = SortedList(
        Race::class.java,
        object : SortedListAdapterCallback<Race>(this) {
            override fun areItemsTheSame(p1: Race, p2: Race)
                    = p1.id == p2.id

            override fun compare(p1: Race, p2: Race)
                    = -p1.date.compareTo(p2.date)

            override fun areContentsTheSame(p1: Race, p2: Race)
                    = p1.date == p2.date

        }
    )

    var onClick: ((Race) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RaceResultItemBinding.inflate(inflater, parent, false)
        return RaceResultViewHolder(binding).apply {
            onClick = this@RaceResultAdapter.onClick
        }
    }

    override fun onBindViewHolder(holder: RaceResultViewHolder, position: Int) {
        holder.bind(raceList[position])
    }

    override fun getItemCount() = raceList.size()

    fun setRaces(races: List<Race>) {
        raceList.clear()
        raceList.addAll(races)
        notifyDataSetChanged()
    }
}