package ru.cryhards.brootkiddie.engine.scene

import ru.cryhards.brootkiddie.engine.util.Environment

/**
 * Created with love by luna_koly on 29.10.2017.
 */
interface Mesh : Viewable {
    fun draw(environment: Environment): Mesh
    fun genBuffers(): Mesh
    fun build(src: FloatArray): Mesh
}