package com.example.practice21kotline

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var textTimer: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonStop: Button
    private lateinit var buttonReset: Button

    private val viewModel: StopwatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textTimer = findViewById(R.id.text_timer)
        buttonStart = findViewById(R.id.button_start)
        buttonStop = findViewById(R.id.button_stop)
        buttonReset = findViewById(R.id.button_reset)

        viewModel.time.observe(this, Observer { time ->
            textTimer.text = formatTime(time)
        })

        buttonStart.setOnClickListener {
            viewModel.startTimer()
        }

        buttonStop.setOnClickListener {
            viewModel.stopTimer()

            val stoppedTime = viewModel.time.value ?: 0
            Toast.makeText(
                this,
                "Таймер остановлен на ${formatTime(stoppedTime)}",
                Toast.LENGTH_SHORT
            ).show()
        }

        buttonReset.setOnClickListener {
            viewModel.resetTimer()
        }
    }

    private fun formatTime(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val millis = milliseconds % 1000 / 10
        return String.format("%02d:%02d:%02d.%02d", hours, minutes % 60, seconds % 60, millis)
    }
}