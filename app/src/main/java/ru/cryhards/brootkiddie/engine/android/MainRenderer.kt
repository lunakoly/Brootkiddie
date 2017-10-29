package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import ru.cryhards.brootkiddie.engine.util.GameRegistry
import ru.cryhards.brootkiddie.engine.util.Shaders
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class MainRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var registry = GameRegistry(context)

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        registry.renderer = this
        GLES30.glClearColor(0.7f, 1.0f, 0.7f, 1.0f)
        Shaders.init(context)
        registry.runTask()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        registry.primaryLayer
                .forEach { it.draw() }
    }

}