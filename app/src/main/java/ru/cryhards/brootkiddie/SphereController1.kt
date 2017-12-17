package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.environment.util.Material
import ru.cryhards.brootkiddie.engine.environment.util.ObjectController
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 17.12.2017.
 */
class SphereController1(private val obj: Object): ObjectController {
    private var material: Material? = null

    override fun init() {
        material = obj.getComponent<Material>()
    }

    override fun load() {

    }

    override fun unload() {

    }

    override fun update(dt: Long) {
        material?.diffuseLight = Vec3(Math.random().toFloat(), Math.random().toFloat(), Math.random().toFloat())
    }
}