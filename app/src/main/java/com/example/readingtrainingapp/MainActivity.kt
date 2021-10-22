package com.example.readingtrainingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toggleButton: Button = findViewById(R.id.toggle_visibility_button)
        toggleButton.setOnClickListener {
            toggleVisibility()
        }
    }

    private fun toggleVisibility() {
        val textToRemember: TextView = findViewById(R.id.text_to_remember_textview)
        textToRemember.text = (Random().nextInt(1000000 - 100000) + 100000).toString()
        textToRemember.visibility = View.VISIBLE

    }
}
