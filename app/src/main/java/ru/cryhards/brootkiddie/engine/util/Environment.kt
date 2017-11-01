package ru.cryhards.brootkiddie.engine.util

import ru.cryhards.brootkiddie.engine.util.prop.Vec3FloatProperty

/**
 * Created with love by luna_koly on 01.11.2017.
 */
class Environment {
    var ambientLight = Vec3FloatProperty(1.0f, 1.0f, 1.0f)
    val mvpMatrix = FloatArray(16)
}