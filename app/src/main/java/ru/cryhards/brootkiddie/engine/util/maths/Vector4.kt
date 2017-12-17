package ru.cryhards.brootkiddie.engine.util.maths

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

    constructor() : this(0f, 0f, 0f, 0f)

    operator fun get(i: Int): Float = v[i]
    operator fun set(i: Int, value: Float) { v[i] = value }
}