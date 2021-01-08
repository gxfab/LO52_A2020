package com.utbm.nouassi.manou.coursecodep25.ui.participant

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.utbm.nouassi.manou.coursecodep25.R
import com.utbm.nouassi.manou.coursecodep25.database.entity.Coureur
import com.utbm.nouassi.manou.coursecodep25.database.entity.Participant
import com.utbm.nouassi.manou.coursecodep25.ui.course.CourseEquipeAdapter
import com.utbm.nouassi.manou.coursecodep25.ui.participant.ParticipantAdapter.*
import java.util.ArrayList

class ParticipantAdapter (private var participantList: ArrayList<Participant>, private var myHandler: ParticipantAdapter.ParticipantHandler) :
    RecyclerView.Adapter<ParticipantAdapter.ViewHolder>() {

    interface ParticipantHandler {
        fun deleteParticipant(id: Int)
    }

    constructor(): this(ArrayList<Participant>(), ParticipantFragment()){
    }

    constructor(handler: ParticipantHandler): this(ArrayList<Participant>(), ParticipantFragment()){
        myHandler = handler
        participantList = ArrayList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.participant_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return participantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( participantList.get(position) )
    }

    fun addParticipants( participants: ArrayList<Participant>){
        participantList.addAll(participants)
        notifyDataSetChanged()
    }


    fun addParticipant( participant: Participant){
        participantList.add(0, participant)
        notifyDataSetChanged()
    }

    fun clear() {
        participantList.clear()
        notifyDataSetChanged()
    }

    fun removeParticipant(result: Participant) {
        participantList.remove(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var _nomTextView = view.findViewById<TextView>(R.id.nom_textview)
        private var _ratingBar = view.findViewById<RatingBar>(R.id.echelon)
        private var _deleteParticipantButton = view.findViewById<ImageButton>(R.id.delete_participant)
        fun bind(get: Participant) {
                _nomTextView.setText( get.nom )
                _ratingBar.rating = get.niveau.toFloat()/2
            _deleteParticipantButton.setOnClickListener({
                myHandler.deleteParticipant(get.id)
            })
        }
    }

}
