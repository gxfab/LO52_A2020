package com.codep25.codep25

import android.graphics.Color

class Config {
    companion object {
        const val DEFAULT_NB_PARTICIPANTS_PER_TEAM = 3
        const val DEFAULT_NB_TURNS_PER_PARTICIPANT = 2
        val DEFAULT_PARTICIPANTS_COLORS: List<Int> = listOf(
            Color.rgb(237, 94, 23),
            Color.rgb(219, 21, 237),
            Color.rgb(23, 237, 226)
        )
        const val DEFAULT_MAX_LEVEL = 100
        const val CODEP25_URL = "https://www.codep25.com/"
    }
}