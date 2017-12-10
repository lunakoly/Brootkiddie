package ru.cryhards.brootkiddie.engine.environment

import android.util.Log
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.cam.Camera

/**
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Scene {
    lateinit var registry: EngineRegistry

    val environment = Environment()
    val objects = ArrayList<Object>()
    val ui = ArrayList<Object>()

    var activeCamera: Camera? = null
        set(value) {
            value?.activate(registry.surface)
            field = value
        }

    abstract fun start(registry: EngineRegistry)
}