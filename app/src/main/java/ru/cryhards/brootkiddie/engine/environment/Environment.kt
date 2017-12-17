package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Created with love by luna_koly on 01.11.2017.
 */
class Environment {
    lateinit var vMatrix: Matrix4
    lateinit var pMatrix: Matrix4
    lateinit var eyePosition: Transform

    var ambientCoefficient = NotNullableProperty(0.06f)
    var sunlight = Vec3(1.0f, 1.0f, 0.99f)
    var sunDirection = Vec3(0.0f, -1.0f, 0.0f)
}