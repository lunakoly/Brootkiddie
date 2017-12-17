package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import ru.cryhards.brootkiddie.engine.environment.Scene

/**
 * Main engine structure. Use it to access
 * main components
 *
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineRegistry(var context: Context) {
    /**
     * Renderer component
     */
    lateinit var renderer: EngineRenderer
    /**
     * Surface View component
     */
    lateinit var surface: EngineSurface
    /**
     * Activity component
     */
    lateinit var activity: EngineActivity


    /**
     * Holds all registered scenes
     */
    val scenes = HashMap<String, Scene>()
    /**
     * Reference to scene that is being drawn
     */
    var activeScene: Scene? = null


    /**
     * Switches the active scene to the specified one
     */
    fun switchScene(name: String) {
        activeScene?.preUnload()
        activeScene = scenes[name]

        if (activeScene == null)
            return

        if (!activeScene!!.isInitialized) {
            activeScene!!.preInit()
        }

        activeScene!!.preLoad()
    }
}