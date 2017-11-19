package ru.cryhards.brootkiddie.engine.environment.interfaces

import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty

/**
 * Created with love by luna_koly on 31.10.2017.
 */
interface Viewable {
    val position: CoordProperty
    fun getMatrix(): Mat4
}