package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import ru.cryhards.brootkiddie.engine.environment.util.Shaders
import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineRenderer(private val context: Context) : GLSurfaceView.Renderer {
    var registry = EngineRegistry(context)

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        registry.renderer = this

        GLES30.glClearColor(0f, 0f, 0f, 1.0f)
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)
        GLES30.glEnable(GLES30.GL_BLEND)

        Shaders.init(context)
        registry.startScene()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

        if (registry.activeScene == null)
            return

        if (registry.activeScene?.activeCamera == null)
            return

        val scene = registry.activeScene!!
        val cam = scene.activeCamera!!
        val env = scene.environment

        val projectionMatrix = cam.getProjectionMatrix()
        val viewMatrix =       cam.getModelMatrix()

        env.mvpMatrix = projectionMatrix.multiply(viewMatrix)
        env.activeCameraPositionMatrix = Mat4.translate(
                -cam.transform.x.value,
                -cam.transform.y.value,
                -cam.transform.z.value).invert()!!

        env.eyePosition = Transform(
                cam.transform.x,
                cam.transform.y,
                cam.transform.z
        )

        scene.objects
                .forEach { it.draw(env) }

        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)

        scene.ui
                .forEach { it.draw(env) }
    }

}