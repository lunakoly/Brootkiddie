package ru.cryhards.brootkiddie.engine.util.maths

import kotlin.math.sqrt


/**
 * Vector4 objects with encapsulated calculations
 */
class Vector4(x: Float, y: Float, z: Float, w: Float) {
    private val v = floatArrayOf(x, y, z, w)

    var x
        get() = this[0]
        set(value) {
            this[0] = value
        }
    var y
        get() = this[1]
        set(value) {
            this[1] = value
        }
    var z
        get() = this[2]
        set(value) {
            this[2] = value
        }
    var w
        get() = this[3]
        set(value) {
            this[3] = value
        }

    /**
     * Creates [0, 0, 0, 0]
     */
    constructor() : this(0f, 0f, 0f, 0f)

    operator fun get(i: Int): Float = v[i]
    operator fun set(i: Int, value: Float) { v[i] = value }


    /**
     * Returns normalized vector
     */
    fun normalize(): Vector4 {
        val len = sqrt(x * x + y * y + z * z + w * w)
        return if (len == 0f)
            Vector4(0f, 0f, 0f, 0f)
        else
            Vector4(x / len, y / len, z / len, w / len)
    }

    /**
     * Returns dot product of 2 vectors
     */
    fun dot(vec: Vector4): Float =
            x*vec.x + y*vec.y + z*vec.z //+ w*vec.w

    /**
     * Returns cross product of 2 vectors
     */
    fun cross(vec: Vector4): Vector4 = Vector4(
            y * vec.z - z * vec.y,
            z * vec.x - x * vec.z,
            z * vec.y - y * vec.x,
            0f
    )

    /**
     * Returns vector that is a sum of the 2 ones
     */
    operator fun plus(vec: Vector4): Vector4 = Vector4(
            x + vec.x,
            y + vec.y,
            z + vec.z,
            1f //w + vec.w
    )

    /**
     * Returns vector with the opposite direction
     */
    operator fun unaryMinus(): Vector4 = Vector4(-x, -y, -z, w)

    /**
     * Returns vector that is a sum of this and -vec
     */
    operator fun minus(vec: Vector4): Vector4 = Vector4(
            x - vec.x,
            y - vec.y,
            z - vec.z,
            1f //w - vec.w
    )

    /**
     * Returns vector with x, y, z multiplied by k
     */
    operator fun times(k: Float): Vector4 = Vector4(x * k, y * k, z * k, w)
}