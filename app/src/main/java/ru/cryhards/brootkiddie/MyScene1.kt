package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.MeshFactory
import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import java.util.*

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class MyScene1 : Scene() {
    override fun start(registry: EngineRegistry) {

        val sphere = MeshFactory.loadObj(registry.context, "models/sphere.obj")
        sphere.transform.y.value = -2.0f
        sphere.transform.x.value = -2.0f
        objects.add(sphere)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                sphere.rotation.vertical.value += 0.005
            }
        }, 0, 16)


        val cam = FPSCamera()
        activeCamera = cam
        cam.transform.z.value = 4f
        cam.rotation.horizontal.value = 0.2
        cam.rotation.vertical.value = -0.2

        environment.sunDirection.z.value = -0.3f
        environment.sunDirection.x.value = -0.5f
        environment.sunDirection.y.value = -1.0f

    }
}