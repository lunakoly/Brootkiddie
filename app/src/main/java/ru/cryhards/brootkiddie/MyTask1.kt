package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.scene.RectanglePlane
import ru.cryhards.brootkiddie.engine.scene.TrianglePlane
import ru.cryhards.brootkiddie.engine.scene.VertexObject
import ru.cryhards.brootkiddie.engine.scene.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.scene.cam.behaviour.BasicFPS
import ru.cryhards.brootkiddie.engine.util.GameRegistry
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.Task
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import java.util.*

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

        val cube = VertexObject(floatArrayOf(
                // Front face
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,

                // Back face
                -1.0f, -1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,

                // Top face
                -1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f, -1.0f,

                // Bottom face
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,

                // Right face
                1.0f, -1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,

                // Left face
                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f, -1.0f
        ), shortArrayOf(
                0,  1,  2,      0,  2,  3,    // front
                4,  5,  6,      4,  6,  7,    // back
                8,  9,  10,     8,  10, 11,   // top
                12, 13, 14,     12, 14, 15,   // bottom
                16, 17, 18,     16, 18, 19,   // right
                20, 21, 22,     20, 22, 23   // left
        )).genBuffers() as VertexObject
        cube.position.z.value -= 3
        cube.position.x.value -= 3
        cube.position.y.value -= 3
        cube.rotation.vertical.value = -0.7
        cube.rotation.horizontal.value = -0.7

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                cube.rotation.horizontal.value -= -0.01
            }
        }, 0, 16)

//        registry.primaryLayer.add(mesh1)
//        registry.primaryLayer.add(mesh2)
//        registry.primaryLayer.add(cube)


        val cam = FPSCamera().withBehaviourOf(BasicFPS(registry))
        registry.activeCamera = cam
        cam.position.z.value = -4f
        cam.rotation.horizontal.value = 0.2
        cam.rotation.vertical.value = 0.2

//        cam.position.x.value += 2
//        cam.position.y.value += 2
//        cam.rotation.horizontal.value = -0.1
//        cam.rotation.vertical.value = -0.6

        registry.environment.sunDirection.z.value = -0.3f
        registry.environment.sunDirection.x.value = -0.5f
        registry.environment.sunDirection.y.value = -1.0f

        registry.environment.ambientLight = CoordProperty(0.1f, 0.1f, 0.1f)


        val cube2 = Shaders.decodeObj(registry.context, "models/basic_cube.obj")
        cube2.position.z.value = -2.0f
        cube2.position.y.value = -2.0f
        cube2.position.x.value = 2.0f
        registry.primaryLayer.add(cube2)

//        registry.environment.ambientLight = CoordProperty(1f, 1f, 1f)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                cube2.rotation.horizontal.value -= 0.01
                cube2.rotation.vertical.value -= 0.01
            }
        }, 0, 16)

        val sphere = Shaders.decodeObj(registry.context, "models/sphere.obj")
        sphere.position.y.value = -2.0f
        sphere.position.x.value = -2.0f
        registry.primaryLayer.add(sphere)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                sphere.rotation.vertical.value += 0.005
            }
        }, 0, 16)
    }
}