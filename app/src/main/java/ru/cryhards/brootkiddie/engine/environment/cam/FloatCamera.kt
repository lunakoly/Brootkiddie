package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.interfaces.Camera
import ru.cryhards.brootkiddie.engine.environment.interfaces.CameraBehaviour
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty

/**
 * Created with love by luna_koly on 03.12.2017.
 */
class FloatCamera(registry: EngineRegistry) : Camera {
    override val position = CoordProperty()
    val rotation = RotationProperty()
    private val projectionMatrix: Mat4

    init {
        val aspect = registry.surface.width.toFloat() / registry.surface.height
        projectionMatrix = Mat4.frustrum(-aspect, aspect, -1f, 1f, 3f, 1000f)
    }

    override fun getModelMatrix(): Mat4 {
        val camRotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Mat4.translate(
                -position.x.value,
                -position.y.value,
                -position.z.value)
        return camRotationMatrix.multiply(camTransitionMatrix)
    }

    override fun getProjectionMatrix(): Mat4 {
        return projectionMatrix
    }
}