package ru.cryhards.brootkiddie.engine.environment.cam

import ru.cryhards.brootkiddie.engine.environment.interfaces.CameraBehaviour
import ru.cryhards.brootkiddie.engine.environment.interfaces.Viewable
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class FPSCamera: Viewable {
    val position = CoordProperty()
    val rotation = RotationProperty()

    override fun getMatrix(): Mat4 {
        val camRotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Mat4.translate(
                position.x.value,
                -position.y.value,
                position.z.value)
        return camRotationMatrix.multiply(camTransitionMatrix)
    }

    fun withBehaviourOf(behaviourProtocol: CameraBehaviour): FPSCamera {
        behaviourProtocol.setCam(this)
        behaviourProtocol.init()
        return this
    }
}