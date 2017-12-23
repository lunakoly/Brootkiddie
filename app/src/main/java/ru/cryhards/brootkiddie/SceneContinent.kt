package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 17.12.2017.
 */
class SceneContinent(name: String) : Scene(name) {
    private lateinit var continent: StaticObject

    override fun init() {
        continent = new.OBJ("africa.obj")
        continent.transform.y.value = -8.0f
        continent.transform.x.value = -2.0f
        continent.transform.z.value = 3.0f
        continent.material.diffuseLight = Vec3(1f, 0.5f, 0.5f)
        continent.material.specularLight = Vec3(1f, 0.8f, 0.5f)
        objects.add(continent)

        val cam = FPSCamera()
        activeCamera = cam
        objects.add(cam)
    }

    override fun unload() {

    }

    override fun load() {

    }

    override fun update(dt: Long) {

    }
}