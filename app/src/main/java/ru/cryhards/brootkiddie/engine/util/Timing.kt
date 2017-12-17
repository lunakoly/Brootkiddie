package ru.cryhards.brootkiddie.engine.util

import java.util.*

/**
 * Created with love by luna_koly on 12/11/17.
 */
object Timing {
    fun delay(delay: Long, code: () -> Unit): Timer {
        val timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                code()
            }
        }, delay)

        return timer
    }

    fun repeat(delay: Long, interval: Long, code: () -> Unit): Timer {
        val timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                code()
            }
        }, delay, interval)

        return timer
    }
}