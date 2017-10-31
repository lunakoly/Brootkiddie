package ru.cryhards.brootkiddie.engine.util

import android.opengl.Matrix
import java.lang.Math.cos
import java.lang.Math.sin

/**
 * Created with love by luna_koly on 31.10.2017.
 */
object MoreMatrix {
    fun getLookAroundRotationM(x: Double, y: Double, z: Double): FloatArray {
        val mat = FloatArray(16)
        Matrix.setIdentityM(mat, 0)

        val rx = floatArrayOf(
                1f,                0f,               0f, 0f,
                0f,  cos(x).toFloat(), sin(x).toFloat(), 0f,
                0f, -sin(x).toFloat(), cos(x).toFloat(), 0f,
                0f,                0f,               0f, 1f
        )

        // TODO

        Matrix.multiplyMM(mat, 0, rx, 0, mat, 0)
        return mat
    }

    fun getTranslationM(x: Float, y: Float, z: Float): FloatArray {
        return floatArrayOf(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
        )
    }
}