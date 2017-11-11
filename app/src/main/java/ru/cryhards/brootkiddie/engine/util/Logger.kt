package ru.cryhards.brootkiddie.engine.util

/**
 * Created with love by luna_koly on 11.11.2017.
 */
object Logger {
    fun getArr(arr: ArrayList<Float>): String {
        var out = "\nSize: " + arr.size.toString()
        for (i in 0 until arr.size / 3) {
            out += "\n" + i + ": "
            for (j in 0 until 3)
                out += arr[i * 3 + j].toString() + " "
        }
        return out
    }

    fun getArr(arr: Array<Float>): String {
        var out = "\nSize: " + arr.size.toString()
        for (i in 0 until arr.size / 3) {
            out += "\n" + i + ": "
            for (j in 0 until 3)
                out += arr[i * 3 + j].toString() + " "
        }
        return out
    }
}