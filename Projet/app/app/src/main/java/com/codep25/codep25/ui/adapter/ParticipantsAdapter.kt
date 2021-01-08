package com.codep25.codep25.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.codep25.codep25.databinding.ParticipantRowItemBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.ParticipantWithTeam
import com.codep25.codep25.ui.viewholder.ParticipantViewHolder

class ParticipantsAdapter(private val colors: ParticipantColors) : RecyclerView.Adapter<ParticipantViewHolder>() {

    var onItemClick: ((ParticipantWithTeam) -> Unit)? = null
    var onSelectionModeChanged: ((Boolean) -> Unit)? = null
    var onSelected: ((Set<Long>) -> Unit)? = null
    private var _isSelecting = false

    var isSelecting
        get() = _isSelecting
        set(v) {
            _isSelecting = v
            onSelectionModeChanged?.invoke(v)
            notifyDataSetChanged()
        }

    private val participantList: SortedList<ParticipantWithTeam> = SortedList(
        ParticipantWithTeam::class.java,
        object : SortedListAdapterCallback<ParticipantWithTeam>(this) {
            override fun areItemsTheSame(p1: ParticipantWithTeam, p2: ParticipantWithTeam)
                    = p1.id == p2.id

            override fun compare(p1: ParticipantWithTeam, p2: ParticipantWithTeam)
                    = p1.name.compareTo(p2.name, true)

            override fun areContentsTheSame(p1: ParticipantWithTeam, p2: ParticipantWithTeam)
                    = p1.name == p2.name && p1.level == p2.level

        }
    )

    private val _selectedParticipants = HashSet<Long>()
    val selectedParticipants get(): Set<Long> = _selectedParticipants

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ParticipantRowItemBinding.inflate(inflater, parent, false)
        return ParticipantViewHolder(binding).apply {
            onItemClick = {
                this@ParticipantsAdapter.onItemClick?.invoke(it.participant)
            }

            onLongItemClick = {
                _isSelecting = !_isSelecting

                if (!isSelecting)
                    _selectedParticipants.clear()

                onSelectionModeChanged?.invoke(_isSelecting)
                notifyDataSetChanged()
            }

            onItemSelectedChange = {id, isSelected ->
                if (isSelected)
                    _selectedParticipants.add(id)
                else
                    _selectedParticipants.remove(id)

                onSelected?.invoke(selectedParticipants)
            }
        }
    }

    override fun getItemCount() = participantList.size()

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val p = participantList[position]
        holder.bind(
            ParticipantViewHolder.SelectableParticipant(
                p,
                _selectedParticipants.contains(p.id)
            ), colors, _isSelecting)
    }

    fun setParticipants(participants: List<ParticipantWithTeam>) {
        participantList.clear()
        _selectedParticipants.clear()
        participantList.addAll(participants)
        notifyDataSetChanged()
    }

    fun selectAll() {
        for (i in 0 until participantList.size())
            _selectedParticipants.add(participantList[i].id)

        notifyDataSetChanged()
    }

    fun deselectAll() {
        _selectedParticipants.clear()
        notifyDataSetChanged()
    }

}