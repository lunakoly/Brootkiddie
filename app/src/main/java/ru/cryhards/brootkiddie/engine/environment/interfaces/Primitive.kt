package ru.cryhards.brootkiddie.engine.environment.interfaces

/**
 * Created with love by luna_koly on 03.12.2017.
 */
interface Primitive : Mesh {
    fun genBuffers(): Primitive { return this }
}