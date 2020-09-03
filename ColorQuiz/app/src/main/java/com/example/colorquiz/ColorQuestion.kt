package com.example.colorquiz

import android.graphics.Color

class ColorQuestion() {
    private val colors: List<Color> = listOf(Color.valueOf(Color.BLACK)
        ,Color.valueOf(Color.BLUE)
        ,Color.valueOf(Color.GREEN)
        ,Color.valueOf(Color.RED)
        ,Color.valueOf(Color.WHITE)
        ,Color.valueOf(Color.YELLOW)
    )

    private val target = colors.get((0 until colors.size).random())
    lateinit var color1: Color
    lateinit var color2: Color

    val answerColor: String = run {
        when (target.toArgb()) {
            Color.BLACK -> "Black"
            Color.BLUE -> "Blue"
            Color.GREEN -> "Green"
            Color.RED -> "RED"
            Color.WHITE -> "White"
            Color.YELLOW -> "Yellow"
            else -> ""
        }
    }

    init {
        if ((0..1).random() > 0) {
            color1 = target
            do {
                color2 = colors.get((0 until colors.size).random())
            } while (color1 === color2)
        } else {
            color2 = target
            do {
                color1 = colors.get((0 until colors.size).random())
            } while (color1 === color2)
        }
    }

    fun guess (color: Color): Boolean {
        return target == color
    }
}