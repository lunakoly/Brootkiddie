package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import ru.cryhards.brootkiddie.engine.util.GameRegistry
import ru.cryhards.brootkiddie.engine.util.Shaders
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class MainRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var registry = GameRegistry(context)

    private var projectionMatrix = FloatArray(16)

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        registry.renderer = this
        GLES30.glClearColor(0.7f, 1.0f, 0.7f, 1.0f)
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        Shaders.init(context)
        registry.runTask()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

        val aspect = width.toFloat() / height
        Matrix.frustumM(projectionMatrix, 0, -aspect, aspect, -1f, 1f, 3f, 1000f)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        val viewMatrix = registry.activeCamera.getMatrix()
        Matrix.multiplyMM(registry.environment.mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        registry.primaryLayer
                .forEach { it.draw(registry.environment) }
    }

}