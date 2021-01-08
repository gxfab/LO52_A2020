package com.codep25.codep25.model.color

import android.graphics.Color
import com.codep25.codep25.model.storage.Storage
import kotlin.random.Random

class ParticipantColors private constructor(private val colorList: List<Int>) {

    val size get() = colorList.size

    fun getColorForPosition(i: Int) : Int = colorList[i]

    fun getColorOrBlack(i: Int) : Int {
        if (i < colorList.size && i >= 0)
            return colorList[i]

        return Color.BLACK
    }

    fun mergeWith(other: ParticipantColors) : ParticipantColors {
        val merged = this.colorList + other.colorList
        return ParticipantColors(merged)
    }

    fun resizeWith(newSize: Int, producer: ((Int) -> ParticipantColors)) : ParticipantColors {
        return if (size >= newSize)
            ParticipantColors((colorList.subList(0, newSize)))
        else
            mergeWith(producer(newSize - size))
    }

    fun toColorArray() = colorList

    class Factory(private val storage: Storage) {
        fun fromPref() : ParticipantColors? =
            storage.getParticipantPositionColors()?.let { ParticipantColors(it) }

        fun saveToPref(pc: ParticipantColors) {
            storage.setParticipantPositionColors(pc.toColorArray())
        }

        fun fromPrefOrDefaultOrRandomAndSave(def: List<Int>, nbPositions: Int) : ParticipantColors {
            var colors = fromPref()

            if (colors == null) {
                colors = fromList(def)
                saveToPref(colors)
            }

            if (colors.size < nbPositions) {
                val newColors = fromRandom(nbPositions - colors.size)
                colors = colors.mergeWith(newColors)
            }

            return colors
        }

        fun fromList(list: List<Int>) = ParticipantColors(list)

        fun fromRandom(n: Int) : ParticipantColors {
            val l = ArrayList<Int>(n)

            val r = List(n) { Random.nextInt(0, 255) }
            val g = List(n) { Random.nextInt(0, 255) }
            val b = List(n) { Random.nextInt(0, 255) }

            for (i in 0 until n)
                l.add(Color.rgb(r[i], g[i], b[i]))

            return ParticipantColors(l)
        }
    }
}