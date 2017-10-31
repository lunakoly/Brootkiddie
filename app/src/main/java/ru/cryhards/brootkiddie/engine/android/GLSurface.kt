package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLSurfaceView
import ru.cryhards.brootkiddie.engine.util.GameRegistry

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class GLSurface(context: Context) : GLSurfaceView(context) {
    var registry: GameRegistry

    init {
        setEGLContextClientVersion(3)
        val renderer = MainRenderer(context)
        setRenderer(renderer)
//        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        registry = renderer.registry
        registry.surface = this
    }
}