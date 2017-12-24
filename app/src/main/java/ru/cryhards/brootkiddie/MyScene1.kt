package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.engine.environment.MeshFactory
import ru.cryhards.brootkiddie.engine.environment.Scene
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.environment.meshes.colliders.PlaneCollider
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class MyScene1(name: String) : Scene(name) {
    private lateinit var sphere: StaticObject
    private lateinit var cube: StaticObject
    private lateinit var plane: StaticObject

    override fun init() {
        sphere = new.Sphere()
        sphere.transform.y.value = -2.0f
        sphere.transform.x.value = -2.0f
        sphere.transform.z.value = 3.0f
        sphere.material.diffuseLight = Vec3(1f, 0.5f, 0.5f)
        sphere.material.specularLight = Vec3(1f, 0.8f, 0.5f)
        objects.add(sphere)

        plane = new.Plane()
        plane.transform.z.value = 3f
        plane.transform.y.value = -5f
        plane.scale.x.value = 10f
        plane.scale.y.value = 10f
        plane.rotation.vertical.value = 3.14 / 2
        plane.material.shininess.value = 256f
        plane.material.diffuseLight = Vec3(0.5f, 0.8f, 0.5f)
        objects.add(plane)

        cube = new.Cube()
        cube.transform.y.value = -2.0f
        cube.transform.x.value = 2.0f
        cube.transform.z.value = 3.0f
        objects.add(cube)

        // IMPLEMENT THE FUCKING ORDER INDEPENDENT TRANSPARENCY !!!!

        cube.material.texture = MeshFactory.loadTexture(registry.context, "img/map2.jpeg", 2, 4000)
//        cube.material.texture = MeshFactory.loadTexture(registry.context, "img/diamond.png")
//        cube.material.type.value = Materials.SKIN

        val cam = PickingFPSCam()
        activeCamera = cam
        objects.add(cam)


        plane.collider = PlaneCollider(plane)


        environment.sunDirection.z.value = -0.3f
        environment.sunDirection.x.value = -0.5f
        environment.sunDirection.y.value = -1.0f
    }

    override fun update(dt: Long) {
        val inc = dt / 16.0
        sphere.rotation.vertical.value += 0.01 * inc
        cube.rotation.vertical.value += 0.01 * inc
        cube.rotation.horizontal.value -= 0.01 * inc
    }

    override fun load() {

    }

    override fun unload() {

    }
}