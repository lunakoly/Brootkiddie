package ru.cryhards.brootkiddie.engine.util.maths

import android.opengl.Matrix
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.tan

/**
 * Matrix4x4 (column-major) objects with encapsulated calculations
 */
class Matrix4(vec0: Vector4, vec1: Vector4, vec2: Vector4, vec3: Vector4) {
    // Vectors are the columns !!!
    private val m: Array<Vector4> = arrayOf(
            vec0, vec1, vec2, vec3
    )

    /**
     * Creates Identity matrix
     */
    constructor(): this(
            // identity
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
    )

    /**
     * Creates matrix of the specified values
     */
    constructor(
            x0: Float, y0: Float, z0: Float, w0: Float,
            x1: Float, y1: Float, z1: Float, w1: Float,
            x2: Float, y2: Float, z2: Float, w2: Float,
            x3: Float, y3: Float, z3: Float, w3: Float): this(
            Vector4(x0, x1, x2, x3),
            Vector4(y0, y1, y2, y3),
            Vector4(z0, z1, z2, z3),
            Vector4(w0, w1, w2, w3)
    )


    /**
     * Returns value of (i,j) as column-major
     */
    operator fun get(i: Int, j: Int): Float = m[j][i]
    /**
     * Sets value of (i,j) as column-major
     */
    operator fun set(i: Int, j: Int, value: Float) { m[j][i] = value }
    /**
     * Returns column at index i
     */
    operator fun get(i: Int): Vector4 = m[i]
    /**
     * Sets column at index i
     */
    operator fun set(i: Int, value: Vector4) { m[i] = value }


    /**
     * Returns vector V = this x vec
     */
    fun x(vec: Vector4): Vector4 {
        return Vector4(
                this[0, 0] * vec.x + this[0, 1] * vec.y + this[0, 2] * vec.z + this[0, 3] * vec.w,
                this[1, 0] * vec.x + this[1, 1] * vec.y + this[1, 2] * vec.z + this[1, 3] * vec.w,
                this[2, 0] * vec.x + this[2, 1] * vec.y + this[2, 2] * vec.z + this[2, 3] * vec.w,
                this[3, 0] * vec.x + this[3, 1] * vec.y + this[3, 2] * vec.z + this[3, 3] * vec.w
        )
    }

    /**
     * Returns matrix M = this x mat
     */
    fun x(mat: Matrix4): Matrix4 {
        val vec0 = this.x(mat[0])
        val vec1 = this.x(mat[1])
        val vec2 = this.x(mat[2])
        val vec3 = this.x(mat[3])
        return Matrix4(vec0, vec1, vec2, vec3)
    }


    /**
     * Returns raw matrix representation
     */
    fun raw(): FloatArray {
        return floatArrayOf(
                this[0, 0], this[0, 1], this[0, 2], this[0, 3],
                this[1, 0], this[1, 1], this[1, 2], this[1, 3],
                this[2, 0], this[2, 1], this[2, 2], this[2, 3],
                this[3, 0], this[3, 1], this[3, 2], this[3, 3]
        )
    }

    /**
     * Returns matrix mirrored by diagonal
     */
    fun reflect(): Matrix4 {
        return Matrix4(
                this[0][0], this[0][1], this[0][2], this[0][3],
                this[1][0], this[1][1], this[1][2], this[1][3],
                this[2][0], this[2][1], this[2][2], this[2][3],
                this[3][0], this[3][1], this[3][2], this[3][3]
        )
    }

    /**
     * Returns raw matrix representation mirrored by diagonal
     */
    fun convert(): FloatArray {
        return floatArrayOf(
                this[0, 0], this[1, 0], this[2, 0], this[3, 0],
                this[0, 1], this[1, 1], this[2, 1], this[3, 1],
                this[0, 2], this[1, 2], this[2, 2], this[3, 2],
                this[0, 3], this[1, 3], this[2, 3], this[3, 3]
        )
    }

    /**
     * Returns matrix that is invert to this
     */
    fun invert(): Matrix4 {
        val m = FloatArray(16)
        Matrix.invertM(m, 0, convert(), 0)
        return Matrix4(
                m[0], m[4], m[8], m[12],
                m[1], m[5], m[9], m[13],
                m[2], m[6], m[10], m[14],
                m[3], m[7], m[11], m[15]
        )
    }


    companion object {
        /**
         * Returns Translation Matrix
         */
        fun getTranslation(x: Float, y: Float, z: Float): Matrix4 = Matrix4(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
        )

        /**
         * Returns Scale Matrix
         */
        fun getScale(x: Float, y: Float, z: Float): Matrix4 = Matrix4(
                x, 0f, 0f, 0f,
                0f, y, 0f, 0f,
                0f, 0f, z, 0f,
                0f, 0f, 0f, 1f
        )

        /**
         * Returns X-Rotation Matrix
         */
        @Suppress("MemberVisibilityCanPrivate")
        fun getRotationX(alpha: Double): Matrix4 = Matrix4(
                1f, 0f, 0f, 0f,
                0f, cos(alpha).toFloat(), sin(alpha).toFloat(), 0f,
                0f, -sin(alpha).toFloat(), cos(alpha).toFloat(), 0f,
                0f, 0f, 0f, 1f
        )

        /**
         * Returns Y-Rotation Matrix
         */
        @Suppress("MemberVisibilityCanPrivate")
        fun getRotationY(alpha: Double): Matrix4 = Matrix4(
                cos(alpha).toFloat(), 0f, sin(alpha).toFloat(), 0f,
                0f, 1f, 0f, 0f,
                -sin(alpha).toFloat(), 0f, cos(alpha).toFloat(), 0f,
                0f, 0f, 0f, 1f
        )

        /**
         * Returns FPS View Matrix
         */
        fun getFPSRotation(horizontally: Double, vertically: Double): Matrix4 {
            val ry = getRotationY(horizontally)
            val rx = getRotationX(vertically)
            return ry.x(rx)
        }

        /**
         * Returns Perspective Projection Matrix
         */
        fun getPerspective(fovy: Float, aspect: Float, n: Float, f: Float) = Matrix4(
                1 / tan(fovy / 2) / aspect, 0f, 0f, 0f,
                0f, 1 / tan(fovy / 2), 0f, 0f,
                0f, 0f, (f + n) / (f - n), -2 * f * n / (f - n),
                0f, 0f, 1f, 0f
        )
    }
}