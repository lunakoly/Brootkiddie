package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4
import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Model of a camera with a perspective projection
 *
 * Created with love by luna_koly on 10.12.2017.
 */
open class PerspectiveCamera : Camera() {
    /**
     * Sets camera sight angle
     */
    private var fovy = 0.785f
    private var lastAspect = 0.0f
    private lateinit var projectionMatrix: Matrix4

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
        projectionMatrix = Matrix4.getPerspective(fovy, lastAspect, 0.1f, 1000f)
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
        return projectionMatrix
    }

    /**
     * Returns vector to camera global position
     */
    fun getGlobalPosition(): Vector4 {
        return getAbsoluteModelMatrix().invert()
                .x(Vector4(transform.x.value, transform.y.value, transform.z.value, 1f))
    }

    /**
     * Returns vector of camera looking direction with
     * transform of screen x and y
     */
    fun getDirectionRay(x: Float, y: Float): Vector4 {
        val eyeRay = getProjectionMatrix().invert().x(Vector4(
                (2 * (x) / surface.width - 1.0f),
                (-2 * (y) / surface.height + 1.0f),
                1f, 0f))

        eyeRay.z = 1f
        eyeRay.w = 0f
        return getAbsoluteModelMatrix().x(eyeRay).normalize()
    }
}