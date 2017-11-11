package ru.cryhards.brootkiddie.engine.environment.interfaces

import ru.cryhards.brootkiddie.engine.environment.Environment

/**
 * Created with love by luna_koly on 29.10.2017.
 */
interface Mesh : Viewable {
    fun draw(environment: Environment): Mesh
    fun genBuffers(): Mesh { return this }
}