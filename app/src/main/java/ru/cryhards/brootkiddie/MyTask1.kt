package ru.cryhards.brootkiddie

import android.util.Log
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.MeshManager
import ru.cryhards.brootkiddie.engine.environment.RectangleColoredObject
import ru.cryhards.brootkiddie.engine.environment.TriangleColoredObject
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.environment.cam.behaviour.BasicFPSBehaviour
import ru.cryhards.brootkiddie.engine.util.Task
import java.util.*

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class MyTask1 : Task {
    override fun execute(registry: EngineRegistry) {
        val sphere = MeshManager.loadObj(registry.context, "models/sphere.obj")
        sphere.position.y.value = -2.0f
        sphere.position.x.value = -2.0f
        registry.primaryLayer.add(sphere)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                sphere.rotation.vertical.value += 0.005
            }
        }, 0, 16)


        val triangle = TriangleColoredObject(floatArrayOf(
                0.0f,  0.622008459f, 0.0f,   // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        )).genBuffers() as TriangleColoredObject
        triangle.rotation.vertical.value = -1.3
        triangle.position.z.value += 0.01f
        registry.primaryLayer.add(triangle)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                triangle.rotation.vertical.value += 0.005
            }
        }, 0, 16)


        val rectangle = RectangleColoredObject(floatArrayOf(
                -0.5f,  0.5f, 0.0f,    // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f,  0.5f, 0.0f   // top right
        )).genBuffers() as RectangleColoredObject
        rectangle.position.x.value = 1f
        registry.primaryLayer.add(rectangle)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                rectangle.rotation.horizontal.value += 0.05
            }
        }, 0, 16)


        val cube = MeshManager.loadObj(registry.context, "models/basic_cube.obj")
        cube.position.z.value = -2.0f
        cube.position.y.value = -2.0f
        cube.position.x.value = 2.0f
        registry.primaryLayer.add(cube)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                cube.rotation.horizontal.value -= 0.01
                cube.rotation.vertical.value -= 0.01
            }
        }, 0, 16)


        val cam = FPSCamera().withBehaviourOf(BasicFPSBehaviour(registry))
        registry.environment.activeCamera = cam
        cam.position.z.value = 4f
        cam.rotation.horizontal.value = 0.2
        cam.rotation.vertical.value = -0.2

//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                cam.position.x.value -= 0.01f
//            }
//        }, 0, 16)

        registry.environment.sunDirection.z.value = -0.3f
        registry.environment.sunDirection.x.value = -0.5f
        registry.environment.sunDirection.y.value = -1.0f
    }
}