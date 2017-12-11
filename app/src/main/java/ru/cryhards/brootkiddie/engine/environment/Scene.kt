package ru.cryhards.brootkiddie.engine.environment

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Scene: Container() {
    lateinit var registry: EngineRegistry

    val environment = Environment()
    val ui = ArrayList<Object>()

    var activeCamera: Camera? = null
        set(value) {
            value?.activate(registry.surface)
            field = value
        }

    override fun draw(environment: Environment, parentModelMatrix: Mat4): Object {
        objects
                .forEach { it.draw(environment, parentModelMatrix.multiply(getModelMatrix())) }
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)
        ui
                .forEach { it.draw(environment, parentModelMatrix.multiply(getModelMatrix())) }
        return this
    }

    abstract fun init()
    abstract fun load()
    abstract fun unload()
}