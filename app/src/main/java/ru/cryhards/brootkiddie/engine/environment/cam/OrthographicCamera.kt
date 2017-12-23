package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Model of a camera with a orthographic projection
 *
 * Created with love by luna_koly on 23.12.2017.
 */
open class OrthographicCamera: Camera() {
    private lateinit var orthographicMatrix: Matrix4
    private var lastAspect = 0.0f

    protected lateinit var surface: EngineSurface

    /**
     * Rotation component
     */
    val rotation = Rotation()


    init {
        // registering components
        components.add(rotation)
    }

    /**
     * Invalidate projection dimensions
     */
    override fun onActivatedEvent(surface: EngineSurface): Camera {
        this.surface = surface
        lastAspect = surface.width.toFloat() / surface.height
        orthographicMatrix = Matrix4.getScale(1 / lastAspect, 1f, 0.001f)
        return this
    }

    /**
     * Returns model matrix according to local parent
     */
    override fun getModelMatrix(): Matrix4 {
        val camRotationMatrix = Matrix4.getFPSRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Matrix4.getTranslation(
                transform.x.value,
                transform.y.value,
                transform.z.value)
        return camTransitionMatrix.x(camRotationMatrix)
    }

    /**
     * Represents the way that scene
     * is projected
     */
    override fun getProjectionMatrix(): Matrix4 {
        return orthographicMatrix
    }
}