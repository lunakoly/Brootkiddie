package ru.cryhards.brootkiddie.utils

import java.util.*

fun Float.fixed(p: Int): String {
    return "%.${p}f".format(this, Locale.GERMAN)
}

fun Float.humanReadable(): String {
    if (this >= 1e11.toFloat())
        return "${(this / 1e9.toFloat()).fixed(0)}B"
    if (this >= 1e10.toFloat())
        return "${(this / 1e9.toFloat()).fixed(1)}B"
    if (this >= 1e9.toFloat())
        return "${(this / 1e9.toFloat()).fixed(2)}B"

    if (this >= 1e8.toFloat())
        return "${(this / 1e6.toFloat()).fixed(0)}m"
    if (this >= 1e7.toFloat())
        return "${(this / 1e6.toFloat()).fixed(1)}m"
    if (this >= 1e6.toFloat())
        return "${(this / 1e6.toFloat()).fixed(2)}m"

    if (this >= 1e5.toFloat())
        return "${(this / 1e3.toFloat()).fixed(0)}k"
    if (this >= 1e4.toFloat())
        return "${(this / 1e3.toFloat()).fixed(1)}k"
    if (this >= 1e3.toFloat())
        return "${(this / 1e3.toFloat()).fixed(2)}k"

    if (this >= 1e2.toFloat())
        return "${(this / 1e0.toFloat()).fixed(0)}k"
    if (this >= 1e1.toFloat())
        return "${(this / 1e0.toFloat()).fixed(1)}k"
    if (this >= 1e0.toFloat())
        return "${(this / 1e0.toFloat()).fixed(2)}k"
    return "${this.fixed(3)}"
}

fun Long.humanReadable(): String {
    if (this >= 1e11.toLong())
        return "${(this / 1e9.toFloat()).fixed(0)}B"
    if (this >= 1e10.toLong())
        return "${(this / 1e9.toFloat()).fixed(1)}B"
    if (this >= 1e9.toLong())
        return "${(this / 1e9.toFloat()).fixed(2)}B"

    if (this >= 1e8.toLong())
        return "${(this / 1e6.toFloat()).fixed(0)}m"
    if (this >= 1e7.toLong())
        return "${(this / 1e6.toFloat()).fixed(1)}m"
    if (this >= 1e6.toLong())
        return "${(this / 1e6.toFloat()).fixed(2)}m"

    if (this >= 1e5.toLong())
        return "${(this / 1e3.toFloat()).fixed(0)}k"
    if (this >= 1e4.toLong())
        return "${(this / 1e3.toFloat()).fixed(1)}k"
    if (this >= 1e3.toLong())
        return "${(this / 1e3.toFloat()).fixed(2)}k"
    return "$this"
}