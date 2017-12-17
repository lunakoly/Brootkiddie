package ru.cryhards.brootkiddie.engine.environment

import android.content.Context
import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Scene: Container() {
    lateinit var registry: EngineRegistry
    var isInitialized: Boolean = false

    val environment = Environment()
    val ui = ArrayList<Object>()

    var activeCamera: Camera? = null
        set(value) {
            value?.onActivatedEvent(registry.surface)
            field = value
        }

    override fun draw(environment: Environment, parentModelMatrix: Matrix4): Object {
        objects.forEach {
            it.draw(environment, parentModelMatrix.x(getModelMatrix()))
        }

        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)

        ui.forEach {
            it.draw(environment, parentModelMatrix.x(getModelMatrix()))
        }

        return this
    }

    abstract fun init()
    abstract fun load()
    abstract fun unload()


    fun preInit() {
        new = SceneContextFactory(registry.context)
        isInitialized = true
    }

    lateinit var new: SceneContextFactory

    class SceneContextFactory(private val context: Context) {
        fun OBJ(url: String): StaticObject = MeshFactory.loadObj(context, url)
        fun Sphere(): StaticObject = MeshFactory.loadObj(context, "models/sphere.obj")
        fun Cube(): StaticObject = MeshFactory.loadObj(context, "models/cube.obj")
        fun Plain(): StaticObject = MeshFactory.loadObj(context, "models/plain.obj")
    }
}