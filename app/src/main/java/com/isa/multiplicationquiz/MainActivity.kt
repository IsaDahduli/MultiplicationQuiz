package com.isa.multiplicationquiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var resetButton: Button
    private lateinit var scoreTextView: TextView

    private var num1: Int = 0
    private var num2: Int = 0
    private var correctAns: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        questionTextView = findViewById(R.id.tv_title)
        answerEditText = findViewById(R.id.et_loan_amount)
        submitButton = findViewById(R.id.btn_submit)
        scoreTextView = findViewById(R.id.tv_score)
        resetButton = findViewById(R.id.btn_reset)

        // Retrieve score from SharedPreferences
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 0)
        updateScore()

        // Generate a new question
        generateQuestion()

        submitButton.setOnClickListener {
            val userAns = answerEditText.text.toString().toIntOrNull()
            if (userAns != null) {
                if (userAns == correctAns) {
                    score++
                } else {
                    score--
                }
                updateScore()
                saveScore(sharedPreferences)
                generateQuestion()
            }
        }

        resetButton.setOnClickListener {
            answerEditText.isEnabled = true;
            submitButton.isEnabled = true;
            scoreTextView.setText("Score: 0")
            score = 0;
        }
    }

    private fun generateQuestion() {
        num1 = Random.nextInt(1, 11)
        num2 = Random.nextInt(1, 11)
        correctAns = num1 * num2
        questionTextView.text = "What is $num1 multiply by $num2?"
        answerEditText.text.clear()
    }

    private fun updateScore() {
        scoreTextView.text = "Score: $score"

        if(score < 0)
        {
            Toast.makeText(this@MainActivity, "Game Over, you have failed", Toast.LENGTH_SHORT).show()
            scoreTextView.setText("Game Over, you have failed")
            answerEditText.isEnabled = false;
            submitButton.isEnabled = false;
        }
    }

    private fun saveScore(sharedPreferences: SharedPreferences) {
        with(sharedPreferences.edit()) {
            putInt("score", score)
            apply()
        }
    }
}
