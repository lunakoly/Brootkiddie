package ru.cryhards.brootkiddie.engine.util.maths

class Vec4 {
    open var v: FloatArray

    constructor() {
        v = FloatArray(4)
    }

    constructor(x: Float, y: Float, z: Float, w: Float) {
        v = floatArrayOf(x, y, z, w)
    }
}