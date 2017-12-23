package ru.cryhards.brootkiddie.engine.environment

import android.content.Context
import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.environment.util.ObjectController
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Root container of the drawing tree
 *
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Scene(val sceneName: String): Container(), ObjectController {
    /**
     * Holds a reference to the main engine structure
     */
    lateinit var registry: EngineRegistry

    /**
     * True if scene has already been active
     */
    var isInitialized: Boolean = false

    /**
     * Holds a reference to environment parameters
     */
    val environment = Environment()

    /**
     * Special children branch for displaying UI
     */
    @Suppress("MemberVisibilityCanPrivate")
    protected val ui = ArrayList<Object>()


    /**
     * Holds reference to the active camera instance
     */
    var activeCamera: Camera? = null
        set(value) {
            value?.onActivatedEvent(registry.surface)
            field = value
        }

    /**
     * Draws the object on the surface
     */
    final override fun draw(environment: Environment, parentModelMatrix: Matrix4): Object {
        val mat = parentModelMatrix.x(getModelMatrix())

        objects.forEach {
            it.draw(environment, mat)
        }

        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT)

        ui.forEach {
            it.draw(environment, mat)
        }

        return this
    }


    // self controlled
    override fun init() {}
    override fun load() {}
    override fun unload() {}
    override fun update(dt: Long) {}


    /**
     * Runs controller's init() for self and all child objects
     * Additionally: performs some actions required
     * for the scene to work properly
     */
    final override fun preInit(): Object {
        new = SceneContextFactory(registry.context)
        controller = this
        isInitialized = true

        return super.preInit()
    }

    /**
     * Runs controller's load() for self and all child objects
     * + restriction to override for user not to get
     * disappointed
     */
    final override fun preLoad(): Object {
        return super.preLoad()
    }

    /**
     * Runs controller's unload() for self and all child objects
     * + restriction to override for user not to get
     * disappointed
     */
    final override fun preUnload(): Object {
        return super.preUnload()
    }

    /**
     * Runs controller's update() for self and all child objects
     * + restriction to override for user not to get
     * disappointed
     */
    final override fun preUpdate(environment: Environment, parentModelMatrix: Matrix4, dt: Long): Object {
        return super.preUpdate(environment, parentModelMatrix, dt)
    }


    /**
     * Shortcut for MeshFactory methods
     */
    lateinit var new: SceneContextFactory

    /**
     * Shortcut for MeshFactory methods
     */
    @Suppress("FunctionName")
    class SceneContextFactory(private val context: Context) {
        /**
         * Returns SceneObject based on the given file
         */
        @Suppress("unused")
        fun OBJ(url: String): StaticObject = MeshFactory.loadObj(context, url)
        /**
         * Returns basic sphere
         */
        fun Sphere(): StaticObject = MeshFactory.loadObj(context, "models/sphere.obj")
        /**
         * Returns basic cube
         */
        fun Cube(): StaticObject = MeshFactory.loadObj(context, "models/cube.obj")
        /**
         * Returns basic plain
         */
        fun Plane(): StaticObject = MeshFactory.loadObj(context, "models/plane.obj")
    }
}