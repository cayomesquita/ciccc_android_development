package com.example.colorquiz

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var question: ColorQuestion = ColorQuestion()
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateUI()
    }

    private fun updateUI() {
        val leftBtn = findViewById<Button>(R.id.left_color_btn)
        val rightBtn = findViewById<Button>(R.id.right_color_btn)
        val target = findViewById<TextView>(R.id.color_target_label)
        val score = findViewById<TextView>(R.id.scoreTxt)
        leftBtn.setBackgroundColor(question.color1.toArgb())
        rightBtn.setBackgroundColor(question.color2.toArgb())
        target.text = question.answerColor
        score.text = counter.toString()
    }

    fun colorBtnOnClick(view: View) {
        val btn = view as Button
        val color = btn.background as ColorDrawable
        if (question.guess(Color.valueOf(color.color))){
            counter++
            Toast.makeText(this, "Correct answer", Toast.LENGTH_SHORT).show()
        } else {
            counter--
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show()
        }
        refreshUI()
    }

    fun refreshUI() {
        question = ColorQuestion()
        updateUI()
    }

}