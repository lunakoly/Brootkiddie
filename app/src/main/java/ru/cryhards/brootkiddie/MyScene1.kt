package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.environment.Container
import ru.cryhards.brootkiddie.engine.environment.MeshFactory
import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.Timing

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class MyScene1 : Scene() {
    private lateinit var sphere: StaticObject
    private lateinit var cube: StaticObject
    private lateinit var cont: Container

    override fun init() {
        sphere = MeshFactory.loadObj(registry.context, "models/sphere.obj")
        sphere.transform.y.value = -2.0f
        sphere.transform.x.value = -2.0f
//        objects.add(sphere)

        cube = MeshFactory.loadObj(registry.context, "models/test_cube.obj")
        cube.transform.y.value = -2.0f
        cube.transform.x.value = 2.0f
        cube.transform.z.value = -2f
        objects.add(cube)

        val cam = FPSCamera()
        activeCamera = cam
        cam.transform.z.value = 4f
        cam.rotation.horizontal.value = 0.2
        cam.rotation.vertical.value = -0.2

        environment.sunDirection.z.value = -0.3f
        environment.sunDirection.x.value = -0.5f
        environment.sunDirection.y.value = -1.0f

        cont = Container()
        cont.objects.add(sphere)
        cont.objects.add(cam)
        objects.add(cont)

        scale.x.value = 0.5f
    }

    override fun load() {
        Timing.repeat(0, 16) {
            sphere.rotation.horizontal.value += 0.01
            cont.transform.z.value += 0.01f
        }
    }

    override fun unload() {

    }
}