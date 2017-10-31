package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.scene.RectanglePlane
import ru.cryhards.brootkiddie.engine.scene.TrianglePlane
import ru.cryhards.brootkiddie.engine.scene.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.scene.cam.behaviour.BasicFPS
import ru.cryhards.brootkiddie.engine.util.GameRegistry
import ru.cryhards.brootkiddie.engine.util.Task

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class MyTask1 : Task {
    override fun execute(registry: GameRegistry) {
        val mesh1 = TrianglePlane().build(floatArrayOf(
                0.0f,  0.622008459f, 0.0f,   // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        )).genBuffers() as TrianglePlane

        val mesh2 = RectanglePlane().build(floatArrayOf(
                -0.5f,  0.5f, 0.0f,    // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f,  0.5f, 0.0f   // top right
        )).genBuffers() as RectanglePlane

        mesh2.position.x.value = 1f
        mesh1.rotation.vertical.value = -1.3


        registry.primaryLayer.add(mesh1)
        registry.primaryLayer.add(mesh2)


        val cam = FPSCamera().withBehaviourOf(BasicFPS(registry.activity))
        registry.activeCamera = cam
        cam.position.z.value = -4f
        cam.rotation.horizontal.value = 0.2
        cam.rotation.vertical.value = 0.2
    }
}