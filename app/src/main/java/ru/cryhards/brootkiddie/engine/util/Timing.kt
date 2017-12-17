package ru.cryhards.brootkiddie.engine.util

import java.util.*

/**
 * Manages time
 *
 * Created with love by luna_koly on 12/11/17.
 */
object Timing {
    /**
     * Executes code after a time period
     */
    fun delay(delay: Long, code: () -> Unit): Timer {
        val timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                code()
            }
        }, delay)

        return timer
    }

    /**
     * Repeats code within a time period
     */
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