package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var registry = EngineRegistry(context)

    private var projectionMatrix = Mat4()

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        registry.renderer = this

        GLES30.glClearColor(0f, 0f, 0f, 1.0f)
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)
        GLES30.glEnable(GLES30.GL_BLEND)

        Shaders.init(context)
        registry.runTask()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)

        val aspect = width.toFloat() / height
        projectionMatrix = Mat4.frustrum(-aspect, aspect, -1f, 1f, 3f, 1000f)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        val viewMatrix = registry.environment.activeCamera.getMatrix()
        registry.environment.mvpMatrix = projectionMatrix.multiply(viewMatrix)

        registry.environment.activeCameraPositionMatrix = Mat4.translate(
                -registry.environment.activeCamera.position.x.value,
                -registry.environment.activeCamera.position.y.value,
                -registry.environment.activeCamera.position.z.value).invert()!!

        registry.primaryLayer
                .forEach { it.draw(registry.environment) }
    }

}