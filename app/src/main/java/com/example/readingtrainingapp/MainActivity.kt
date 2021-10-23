package com.example.readingtrainingapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val visibilityTime: Long = 2000

    private val SHOW_NUMBER_TEXT: String =  getString(R.string.show_number)

    private val CHECK_NUMBER_TEXT: String =  getString(R.string.check_number)

    private val NEXT_TEXT: String =  getString(R.string.next)

    private lateinit var gamePhase: Phase

    private lateinit var readCheckButton: Button

    private lateinit var textToRemember: TextView

    private lateinit var inputNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readCheckButton = findViewById(R.id.read_check_button)
        inputNumber = findViewById(R.id.input_number_edit_text)
        gamePhase = Phase.READ
        readCheckButton.setOnClickListener {
            when(gamePhase) {
                Phase.READ ->  makeVisibleForNMilis(visibilityTime)
                Phase.CHECK -> validateAnswer()
                Phase.BETWEEN_TURNS -> initNextTurn()
                else -> {}
            }
        }
        textToRemember = findViewById(R.id.text_to_remember_textview)
    }

    private fun makeVisibleForNMilis(milis: Long) {
        gamePhase = Phase.DURING_ACTIVITY
        textToRemember.text = (Random().nextInt(1000000 - 100000) + 100000).toString()
        textToRemember.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            textToRemember.visibility = View.INVISIBLE
            setButtonText(CHECK_NUMBER_TEXT)
            gamePhase = Phase.CHECK
        }, milis)
    }

    private fun validateAnswer() {
        gamePhase = Phase.DURING_ACTIVITY
        textToRemember.visibility = View.VISIBLE
        if (textToRemember.text.equals(inputNumber.text.toString()))
            inputNumber.setTextColor(Color.GREEN)
        else
            inputNumber.setTextColor(Color.RED)
        setButtonText(NEXT_TEXT)
        gamePhase = Phase.BETWEEN_TURNS
    }

    private fun initNextTurn() {
        gamePhase = Phase.DURING_ACTIVITY
        textToRemember.visibility = View.INVISIBLE
        inputNumber.setTextColor(Color.BLACK)
        inputNumber.text.clear()
        setButtonText(SHOW_NUMBER_TEXT)
        gamePhase = Phase.READ
    }

    private fun setButtonText(text: String) {
        readCheckButton.text = text
    }

}
