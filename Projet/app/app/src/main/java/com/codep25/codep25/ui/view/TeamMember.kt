package com.codep25.codep25.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.codep25.codep25.databinding.TeamMemberBinding

class TeamMember @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: TeamMemberBinding =
        TeamMemberBinding.inflate(LayoutInflater.from(context), this, true)

    init {

        Log.d("TeamMember creation", attrs.toString())
        attrs?.let {

            with(binding.orderTextView) {
                text = "1"
            }

            with(binding.participantNameTextView) {
                text = "Name"
            }
        }
    }
}
