package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.environment.Container
import ru.cryhards.brootkiddie.engine.environment.MeshFactory
import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.Logger
import ru.cryhards.brootkiddie.engine.util.Timing
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class MyScene1 : Scene() {
    private lateinit var sphere: StaticObject
    private lateinit var cube: StaticObject
    private lateinit var plain: StaticObject

    override fun init() {
        sphere = new.Sphere()
        sphere.transform.y.value = -2.0f
        sphere.transform.x.value = -2.0f
        sphere.transform.z.value = 3.0f
        sphere.material.diffuseLight = Vec3(1f, 0.5f, 0.5f)
        sphere.material.specularLight = Vec3(1f, 0.8f, 0.5f)
        objects.add(sphere)

        cube = new.Cube()
        cube.transform.y.value = -2.0f
        cube.transform.x.value = 2.0f
        cube.transform.z.value = 3.0f
        objects.add(cube)

        plain = new.Plain()
        plain.transform.z.value = 3f
        plain.transform.y.value = -5f
        plain.scale.x.value = 10f
        plain.scale.y.value = 10f
        plain.rotation.vertical.value = 3.14 / 2
        plain.material.shininess.value = 256f
        plain.material.diffuseLight = Vec3(0.5f, 0.8f, 0.5f)
        objects.add(plain)

        val cam = FPSCamera()
        activeCamera = cam
        objects.add(cam)

        environment.sunDirection.z.value = -0.3f
        environment.sunDirection.x.value = -0.5f
        environment.sunDirection.y.value = -1.0f
    }

    override fun load() {
        Timing.repeat(0, 16) {
            sphere.rotation.vertical.value += 0.01
            cube.rotation.vertical.value += 0.01
            cube.rotation.horizontal.value -= 0.01
        }
    }

    override fun unload() {

    }
}