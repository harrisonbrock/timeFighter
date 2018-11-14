package com.example.harrisonbrock.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {

    internal val TAG = GameActivity::class.java.simpleName

    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapMeButton: Button

    internal var score = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 60000
    internal var countDownInterval: Long = 1000
    internal var timeleft = 60

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called. Score is: $score")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        
        tapMeButton.setOnClickListener { v ->  incrementScore() }

       if (savedInstanceState != null) {
           score = savedInstanceState.getInt(SCORE_KEY)
           timeleft = savedInstanceState.getInt(TIME_LEFT_KEY)
           restoreGame()
       } else {
           resetGame()
       }
        
    }

    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left, Integer.toString(timeleft))
        timeLeftTextView.text = restoredTime

        countDownTimer = object : CountDownTimer((timeleft * 1000).toLong(), countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timeleft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, Integer.toString(timeleft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }


        }
        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeleft)
        countDownTimer.cancel()

        Log.d(TAG, "OnSaveInstanceState: Saving Score: $score & Time Left: $timeleft")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy call.")
    }

    private fun incrementScore() {

        if (!gameStarted) {
            startGame()
        }

        score++

        val newScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = newScore

    }

    private fun resetGame() {
        score = 0

        val initialScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, Integer.toString(60))
        timeLeftTextView.text = initialTimeLeft


        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timeleft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, Integer.toString(timeleft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }


        }
        gameStarted = false

    }

    private fun startGame() {

        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, Integer.toString(score)), Toast.LENGTH_LONG).show()
        resetGame()
    }
}
