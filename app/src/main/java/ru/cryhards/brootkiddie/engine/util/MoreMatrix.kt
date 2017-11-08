package ru.cryhards.brootkiddie.engine.util

import android.opengl.Matrix
import java.lang.Math.cos
import java.lang.Math.sin

/**
 * Created with love by luna_koly on 31.10.2017.
 */
object MoreMatrix {
    fun getLookAroundRotationM(horizontally: Double, vertically: Double): FloatArray {
        val mat = FloatArray(16)
        Matrix.setIdentityM(mat, 0)

        val ry = floatArrayOf(
                cos(horizontally).toFloat(), 0f, -sin(horizontally).toFloat(), 0f,
                0f,                    1f,                     0f, 0f,
                sin(horizontally).toFloat(), 0f,  cos(horizontally).toFloat(), 0f,
                0f,                    0f,                     0f, 1f
        )

        val rx = floatArrayOf(
                1f,                    0f,                     0f, 0f,
                0f, cos(vertically).toFloat(), -sin(vertically).toFloat(), 0f,
                0f, sin(vertically).toFloat(),  cos(vertically).toFloat(), 0f,
                0f,                    0f,                      0f, 1f
        )

        Matrix.multiplyMM(mat, 0, mat, 0, rx, 0)
        Matrix.multiplyMM(mat, 0, mat, 0, ry, 0)
        return mat
    }

    fun getTranslationM(x: Float, y: Float, z: Float): FloatArray =
            floatArrayOf(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                 x,  -y,  z, 1f
        )
}