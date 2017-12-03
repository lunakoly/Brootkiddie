package ru.cryhards.brootkiddie.engine.environment.interfaces

import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 19.11.2017.
 */
interface Camera : Viewable {
    fun getProjectionMatrix() : Mat4
    fun withBehaviourOf(behaviourProtocol: CameraBehaviour): Camera {
        behaviourProtocol.setCam(this)
        behaviourProtocol.init()
        return this
    }
}