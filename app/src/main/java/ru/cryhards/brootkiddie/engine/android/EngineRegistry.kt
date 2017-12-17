package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.util.Log
import ru.cryhards.brootkiddie.engine.environment.Scene

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineRegistry(var context: Context) {
    lateinit var renderer: EngineRenderer
    lateinit var surface: EngineSurface
    lateinit var activity: EngineActivity

    val scenes = HashMap<String, Scene>()
    var activeScene: Scene? = null

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

    fun startScene() {
        activeScene?.preInit()
        activeScene?.preLoad()
    }
}