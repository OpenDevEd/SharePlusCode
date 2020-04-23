package com.android.shareolc.timers

import android.os.Handler
import android.util.Log

class HandlerTimer {

    val timeHandler: Handler = Handler()
    var isRunning = false
    var stopHandler = false

    private var mSeconds = 0
    private var mTimerTickListener: TimerTickListener? = null

    val timeRunnable: Runnable = object : Runnable {
        override fun run() {
            if (!stopHandler) {
                try {
                    isRunning = true
                    if (mTimerTickListener != null) {
                        mTimerTickListener?.onTickListener(15 - mSeconds)
                    }
                    mSeconds += 1
                    timeHandler.postDelayed(this, UPDATE_INTERVAL * 60L)
                } catch (e: Exception) {
                    e.printStackTrace()
                    isRunning = false
                }
            }
        }
    }

    fun removeTimerCallbacks() {
        timeHandler.removeCallbacksAndMessages(null)
        isRunning = false
        stopHandler = true
        Log.e("removeTimerCallbacks : ", "===> " + "cleared")
    }

    interface TimerTickListener {
        fun onTickListener(minutes: Int)
    }

    fun setOnTimeListener(timerTickListener: TimerTickListener) {
        mTimerTickListener = timerTickListener
    }

    companion object {
        private const val UPDATE_INTERVAL = 1000L
    }
}