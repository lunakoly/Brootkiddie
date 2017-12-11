package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.components.Scale
import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 12/11/17.
 */
open class Container: Object() {
    val rotation = Rotation()
    val scale = Scale(1.0f, 1.0f, 1.0f)

    init {
        components.add(rotation)
        components.add(scale)
    }

    override fun getModelMatrix(): Mat4 {
        val camScaleMatrix = Mat4.scale(
                scale.x.value,
                scale.y.value,
                scale.z.value)
        val camRotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Mat4.translate(
                -transform.x.value,
                -transform.y.value,
                -transform.z.value)
        return camScaleMatrix
                .multiply(camRotationMatrix)
                .multiply(camTransitionMatrix)
    }
}