package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
open class FrustumCamera: Camera() {
    private lateinit var projectionMatrix: Mat4
    private var lastAspect = 0.0f
    val rotation = Rotation()


    init {
        components.add(rotation)
    }


    override fun activate(surface: EngineSurface): Camera {
        lastAspect = surface.width.toFloat() / surface.height
        projectionMatrix = Mat4.frustrum(-lastAspect, lastAspect, -1f, 1f, 3f, 1000f)
        return this
    }

    override fun getModelMatrix(): Mat4 {
        val camRotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Mat4.translate(
                -transform.x.value,
                -transform.y.value,
                -transform.z.value)
        return camRotationMatrix.multiply(camTransitionMatrix)
    }

    override fun getProjectionMatrix(): Mat4 {
        return projectionMatrix
    }
}