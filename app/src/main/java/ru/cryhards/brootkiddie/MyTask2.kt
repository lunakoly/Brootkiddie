package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.MeshManager
import ru.cryhards.brootkiddie.engine.environment.cam.FloatCamera
import ru.cryhards.brootkiddie.engine.environment.cam.behaviour.BasicFloatBehaviour
import ru.cryhards.brootkiddie.engine.util.Task
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import java.util.*

/**
 * Created with love by luna_koly on 03.12.2017.
 */
class MyTask2 : Task {
    override fun execute(registry: EngineRegistry) {
        val cam = FloatCamera(registry).withBehaviourOf(BasicFloatBehaviour(registry)) as FloatCamera
        registry.environment.activeCamera = cam
        cam.position.z.value = 4f

        val map = MeshManager.genRectangleTextureObject(registry.context, "img/map.jpeg")
        registry.primaryLayer.add(map)

        map.v1.x.value = 2.5f
        map.v2.x.value = -2.5f
        map.v3.x.value = -2.5f
        map.v4.x.value = 2.5f

        map.v1.y.value = 2f
        map.v2.y.value = 2f
        map.v3.y.value = -2f
        map.v4.y.value = -2f
        map.genBuffers()

        registry.environment.sunDirection.z.value = -1f
        registry.environment.sunDirection.x.value = -0f
        registry.environment.sunDirection.y.value = -0f
    }
}