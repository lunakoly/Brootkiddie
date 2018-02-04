package ru.cryhards.brootkiddie.cleanup.items.effects

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

}