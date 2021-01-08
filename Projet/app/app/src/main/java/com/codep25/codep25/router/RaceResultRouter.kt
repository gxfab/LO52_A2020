package com.codep25.codep25.router

import android.content.Context
import android.content.Intent
import com.codep25.codep25.model.entity.Race
import com.codep25.codep25.ui.activity.RaceResultsActivity

class RaceResultRouter {
    companion object {
        fun openRace(ctx : Context, raceId: Long) {
            val intent = Intent(ctx, RaceResultsActivity::class.java).apply {
                putExtra(RaceResultsActivity.RACE_ID_KEY, raceId)
            }

            ctx.startActivity(intent)
        }

        fun openRace(ctx : Context, race: Race) = openRace(ctx, race.id)
    }
}