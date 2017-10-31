package ru.cryhards.brootkiddie.engine.shapes

/**
 * Created with love by luna_koly on 29.10.2017.
 */
interface Mesh {
    fun draw(mvpMatrix: FloatArray): Mesh
    fun genBuffers(): Mesh
    fun build(floatArrayOf: FloatArray): Mesh
}