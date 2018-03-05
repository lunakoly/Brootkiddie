package ru.cryhards.brootkiddie.items.effects

import java.lang.Math.abs
import java.lang.String.format
import java.util.*
import kotlin.math.truncate

/**
 * Contains useful converting functions that could be
 * applied to effects values
 *
 * Converter functions can restrict result to the particular limits
 */
object Converter {

    fun pnsqrt(x: Float) =
            if (x >= 0)
                Math.sqrt(x.toDouble()).toFloat()
            else
                -Math.sqrt(-x.toDouble()).toFloat()

    fun sigmoid(x: Float) =
            1f / (1f + Math.exp(-10.0 * x + 5.0)).toFloat()

    fun humanReadable(x: Long): String {
        if (abs(x) < 1000) return "$x"

        val suffixes = arrayListOf("", "k", "M", "B", "T", "q", "Q")
        var sufid = 0

        val sign = if (x < 0) "-" else ""
        var num = abs(x)
        var prenum = abs(x) * 1000
        while (abs(num) >= 1000) {
            sufid++
            num /= 1000
            prenum /= 1000
        }
        val ns = "$num"
        val ps = format(Locale.ENGLISH, "%03d", prenum % 1000)

        return if (ns.length == 3)
            sign + ns + suffixes[sufid]
        else
            sign + ns + "." + ps.subSequence(0, 3 - ns.length) + suffixes[sufid]
    }

    fun humanReadable(x: Float): String {
        val suffixes = arrayListOf("", "k", "M", "B", "T", "q", "Q")
        var sufid = 0

        val sign = if (x < 0) "-" else ""
        var num = truncate(x).toLong()
        var prenum = truncate(x * 1000).toLong()

        while (abs(num) >= 1000) {
            sufid++
            prenum = num
            num /= 1000
        }

        val ns = "$num"
        val ps = format(Locale.ENGLISH, "%03d", prenum % 1000)

        return if (ns.length == 3)
            sign + ns + suffixes[sufid]
        else
            sign + ns + "." + ps.subSequence(0, 3 - ns.length) + suffixes[sufid]
    }
}