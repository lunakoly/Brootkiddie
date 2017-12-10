package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 01.11.2017.
 */
class Environment {
    lateinit var activeCameraPositionMatrix: Mat4
    lateinit var eyePosition: Transform

    var ambientLight = Vec3(0.1f, 0.1f, 0.1f)
    var sunlight = Vec3(1.0f, 1.0f, 0.99f)
    var sunDirection = Vec3(1.0f, -1.0f, 1.0f)
    var mvpMatrix = Mat4()
}