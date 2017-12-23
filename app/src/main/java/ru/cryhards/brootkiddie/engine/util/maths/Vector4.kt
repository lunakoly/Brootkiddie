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
}