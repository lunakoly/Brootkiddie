package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.shapes.RectanglePlane
import ru.cryhards.brootkiddie.engine.shapes.TrianglePlane
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
        )).genBuffers()

        val mesh2 = RectanglePlane().build(floatArrayOf(
                -0.5f,  0.5f, 0.0f,    // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                0.5f, -0.5f, 0.0f,   // bottom right
                0.5f,  0.5f, 0.0f   // top right
        )).genBuffers()

        registry.primaryLayer.add(mesh1)
        registry.primaryLayer.add(mesh2)

        (mesh2 as RectanglePlane).position.x.value = 1f
        (mesh1 as TrianglePlane).rotation.x.value = -1.3
    }
}