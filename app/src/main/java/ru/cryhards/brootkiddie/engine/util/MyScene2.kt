package ru.cryhards.brootkiddie.engine.util

import ru.cryhards.brootkiddie.SphereController1
import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.cam.FloatCamera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 17.12.2017.
 */
class MyScene2(name: String): Scene(name) {
    private lateinit var sphere: StaticObject

    override fun init() {
        sphere = new.Sphere()
        sphere.transform.y.value = -2.0f
        sphere.transform.x.value = -2.0f
        sphere.transform.z.value = 3.0f
        sphere.material.diffuseLight = Vec3(1f, 0.5f, 0.5f)
        sphere.material.specularLight = Vec3(1f, 0.8f, 0.5f)
        objects.add(sphere)

        sphere.controller = SphereController1(sphere)

        val cam = FloatCamera()
        activeCamera = cam
        objects.add(cam)
    }

    override fun unload() {

    }

    override fun load() {

    }

    override fun update() {
        sphere.rotation.horizontal.value -= 0.01
    }
}