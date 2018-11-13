package com.example.harrisonbrock.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class GameActivity : AppCompatActivity() {

    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapMeButton: Button
    internal var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        
        tapMeButton.setOnClickListener { v ->  incrementScore() }
        
    }

    private fun incrementScore() {
        score++

        val newScore = "Your Score: " + Integer.toString(score)
        gameScoreTextView.text = newScore

    }

    private fun resetGame() {

    }

    private fun startGame() {

    }

    private fun endGame() {
        
    }
}
