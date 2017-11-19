package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.environment.interfaces.Viewable
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty

/**
 * Created with love by luna_koly on 01.11.2017.
 */
class Environment {
    lateinit var activeCamera: Viewable
    var ambientLight = CoordProperty(0.1f, 0.1f, 0.1f)
    var sunlight = CoordProperty(1.0f, 1.0f, 1.0f)
    var sunDirection = CoordProperty(1.0f, -1.0f, 1.0f)
    var mvpMatrix = Mat4()
}