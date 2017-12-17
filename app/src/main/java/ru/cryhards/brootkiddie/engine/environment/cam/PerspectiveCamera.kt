package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
open class PerspectiveCamera : Camera() {
    private lateinit var projectionMatrix: Matrix4
    private var lastAspect = 0.0f
    val rotation = Rotation()


    init {
        components.add(rotation)
    }


    override fun onActivatedEvent(surface: EngineSurface): Camera {
        lastAspect = surface.width.toFloat() / surface.height
        projectionMatrix = Matrix4.getPerspective(0.785f, lastAspect, 0.1f, 1000f)
        return this
    }

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

    override fun getProjectionMatrix(): Matrix4 {
        return projectionMatrix
    }
}