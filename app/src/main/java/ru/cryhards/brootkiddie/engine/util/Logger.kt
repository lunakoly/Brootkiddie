package ru.cryhards.brootkiddie.engine.util

import ru.cryhards.brootkiddie.engine.util.maths.Matrix4
import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Manages string output formatting
 *
 * Created with love by luna_koly on 11.11.2017.
 */
object Logger {
    /**
     * Returns array containing string
     */
    fun getArr(arr: ArrayList<Float>): String {
        var out = "\nSize: " + arr.size.toString()
        for (i in 0 until arr.size / 3) {
            out += "\n" + i + ": "
            for (j in 0 until 3)
                out += arr[i * 3 + j].toString() + " "
        }
        return out
    }

    /**
     * Returns array containing string
     */
    fun getArr(arr: Array<Float>): String {
        var out = "\nSize: " + arr.size.toString()
        for (i in 0 until arr.size / 3) {
            out += "\n" + i + ": "
            for (j in 0 until 3)
                out += arr[i * 3 + j].toString() + " "
        }
        return out
    }

    /**
     * Returns vector containing string
     */
    fun getVector4(vec: Vector4): String {
        return "Vector4: [" + vec.x + ", " + vec.y + ", " + vec.z + ", " + vec.w + "]"
    }

    /**
     * Returns matrix containing string
     */
    fun getMatrix4(mat: Matrix4): String {
        var out = "Matrix4: "
        for (i in 0 until 4) {
            out += "\n" + i + ": "
            for (j in 0 until 4)
                out += mat[i, j].toString() + " "
        }
        return out
    }
}