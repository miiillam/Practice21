package com.example.practice21kotline

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StopwatchViewModel : ViewModel() {

    private val _time = MutableLiveData<Long>(0) // Время в миллисекундах
    val time: LiveData<Long> get() = _time

    private var isRunning = false
    private var startTime: Long = 0
    private var elapsedTime: Long = 0

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                _time.value = System.currentTimeMillis() - startTime + elapsedTime
                handler.postDelayed(this, 16L) // Обновление 60 раз в секунду (~16ms)
            }
        }
    }

    fun startTimer() {
        if (isRunning) return
        isRunning = true
        startTime = System.currentTimeMillis()
        handler.post(updateRunnable)
    }

    fun stopTimer() {
        if (!isRunning) return
        isRunning = false
        elapsedTime += System.currentTimeMillis() - startTime
        handler.removeCallbacks(updateRunnable)
    }

    fun resetTimer() {
        stopTimer()
        elapsedTime = 0
        _time.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updateRunnable)
    }
}