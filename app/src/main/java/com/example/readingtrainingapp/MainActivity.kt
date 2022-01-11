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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*
import java.util.Collections.singletonList

class MainActivity : AppCompatActivity() {

    private val visibilityTime: Long = 2000

    private val SHOW_NUMBER_TEXT: String = "Pokaż"

    private val CHECK_NUMBER_TEXT: String =  "Sprawdź"

    private val NEXT_TEXT: String =  "Następny"

    private lateinit var gamePhase: Phase

    private lateinit var readCheckButton: Button

    private lateinit var textToRemember: TextView

    private lateinit var inputNumber: TextView

    private lateinit var mAdView : AdView

    private var digitButtons: List<Int> = listOf(R.id._0, R.id._1, R.id._2, R.id._3, R.id._4, R.id._5, R.id._6,
        R.id._7, R.id._8, R.id._9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this)

        mAdView = findViewById(R.id.adView)

        mAdView.loadAd(AdRequest.Builder().build());

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

        for(id in digitButtons.indices) {
            findViewById<Button>(digitButtons[id]).setOnClickListener {
                inputNumber.append((id.toString()))
            }
        }

        findViewById<Button>(R.id.delete).setOnClickListener {
            inputNumber.text = inputNumber.text.dropLast(1);
        }
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
        inputNumber.text = ""
        setButtonText(SHOW_NUMBER_TEXT)
        gamePhase = Phase.READ
    }

    private fun setButtonText(text: String) {
        readCheckButton.text = text
    }

}
