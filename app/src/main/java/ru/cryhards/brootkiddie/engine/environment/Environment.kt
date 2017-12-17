package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Holds environment parameters that
 * are used by shaders for rendering
 *
 * Created with love by luna_koly on 01.11.2017.
 */
class Environment {
    /**
     * Camera View Matrix
     */
    lateinit var vMatrix: Matrix4
    /**
     * Camera Projection Matrix
     */
    lateinit var pMatrix: Matrix4
    /**
     * Camera Position Vector
     */
    lateinit var eyePosition: Transform
    /**
     * Global system time checked before rendering
     */
    var globalTime: Long = 0


    /**
     * Ambient intensity
     */
    var ambientCoefficient = NotNullableProperty(0.06f)
    /**
     * Color of the sunlight
     */
    var sunlight = Vec3(1.0f, 1.0f, 0.99f)
    /**
     * Sun direction vector
     */
    var sunDirection = Vec3(0.0f, -1.0f, 0.0f)
}