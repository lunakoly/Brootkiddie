package ru.cryhards.brootkiddie.engine.environment.cam

import android.opengl.Matrix
import ru.cryhards.brootkiddie.engine.environment.interfaces.CameraBehaviour
import ru.cryhards.brootkiddie.engine.environment.interfaces.Viewable
import ru.cryhards.brootkiddie.engine.util.MoreMatrix
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class FPSCamera: Viewable {
    val position = CoordProperty()
    val rotation = RotationProperty()

    override fun getMatrix(): FloatArray {
        val viewMatrix = FloatArray(16)
        val camRotationMatrix = MoreMatrix.getLookAroundRotationM(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = MoreMatrix.getTranslationM(
                position.x.value,
                -position.y.value,
                position.z.value)

        Matrix.multiplyMM(viewMatrix, 0, camRotationMatrix, 0, camTransitionMatrix, 0)
        return viewMatrix
    }

    fun withBehaviourOf(behaviourProtocol: CameraBehaviour): FPSCamera {
        behaviourProtocol.setCam(this)
        behaviourProtocol.init()
        return this
    }
}