package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.environment.meshes.ObjectController
import ru.cryhards.brootkiddie.engine.environment.meshes.colliders.ObjectCollider
import ru.cryhards.brootkiddie.engine.environment.util.ObservableCollection
import ru.cryhards.brootkiddie.engine.util.components.Component
import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Represents common scene object behaviour
 *
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Object {
    /**
     * Transform component
     */
    val transform = Transform()
    /**
     * Holds references to all object's components
     */
    val components = ArrayList<Component>()
    /**
     * Object behaviour controller
     */
    var controller: ObjectController? = null
    /**
     * Object collisions calculations helper
     */
    var collider: ObjectCollider? = null


    /**
     * Holds references to child objects
     */
    val objects = ObservableCollection<Object>()
    /**
     * Holds references to object's parent object
     */
    @Suppress("MemberVisibilityCanPrivate")
    var parent: Object? = null
    // is that a bug that Intellij wants to make it private?


    init {
        // register components
        components.add(transform)

        // if a new child has been added, set his parent to this
        // the listener is required only for this
        objects.listener = { it ->
            it.parent = this
        }
    }

    /**
     * Returns the component of the specified type
     */
    inline fun <reified T> getComponent(): T? {
        for (comp in components)
            if (comp is T)
                return comp
        return null
    }

    /**
     * Draws the object on the surface
     */
    open fun draw(environment: Environment, parentModelMatrix: Matrix4): Object {
        objects.forEach {
            it.draw(environment, parentModelMatrix.x(getModelMatrix()))
        }
        return this
    }

    /**
     * Runs controller's init() for self and all child objects
     */
    open fun preInit(): Object {
        controller?.init()
        objects.forEach { it.preInit() }
        return this
    }

    /**
     * Runs controller's load() for self and all child objects
     */
    open fun preLoad(): Object {
        controller?.load()
        objects.forEach { it.preLoad() }
        return this
    }

    /**
     * Runs controller's unload() for self and all child objects
     */
    open fun preUnload(): Object {
        controller?.unload()
        objects.forEach { it.preUnload() }
        return this
    }

    /**
     * Runs controller's update() for self and all child objects
     */
    open fun preUpdate(environment: Environment, parentModelMatrix: Matrix4, dt: Long): Object {
        controller?.update(dt)
        draw(environment, parentModelMatrix)
        objects.forEach { it.preUpdate(environment, parentModelMatrix, dt) }
        return this
    }

    /**
     * Returns model matrix multiplied by all of the parent ones
     */
    fun getAbsoluteModelMatrix(): Matrix4 {
        val parentModelMatrix = if (parent != null)
            parent!!.getAbsoluteModelMatrix()
        else
            Matrix4()

        return parentModelMatrix.x(getModelMatrix())
    }

    /**
     * Returns the root of the hierarchy
     */
    fun getAbsoluteParent(): Object {
        return if (parent != null)
            parent!!
        else
            this
    }

    /**
     * Returns model matrix according to local parent
     */
    abstract fun getModelMatrix(): Matrix4
}