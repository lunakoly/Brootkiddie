package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.environment.util.ObjectController
import ru.cryhards.brootkiddie.engine.environment.util.ObservableCollection
import ru.cryhards.brootkiddie.engine.util.components.Component
import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Object {
    val transform = Transform()
    val components = ArrayList<Component>()
    var controller: ObjectController? = null

    val objects = ObservableCollection<Object>()
    var parent: Object? = null

    init {
        components.add(transform)
        objects.listener = { it ->
            it.parent = this
        }
    }

    inline fun <reified T> getComponent(): T? {
        for (comp in components)
            if (comp is T)
                return comp
        return null
    }

    open fun draw(environment: Environment, parentModelMatrix: Matrix4): Object {
        objects.forEach {
            it.draw(environment, parentModelMatrix.x(getModelMatrix()))
        }
        return this
    }

    open fun preInit(): Object {
        controller?.init()
        objects.forEach { it.preInit() }
        return this
    }

    open fun preLoad(): Object {
        controller?.load()
        objects.forEach { it.preLoad() }
        return this
    }

    open fun preUnload(): Object {
        controller?.unload()
        objects.forEach { it.preUnload() }
        return this
    }

    open fun preUpdate(environment: Environment, parentModelMatrix: Matrix4): Object {
        controller?.update()
        draw(environment, parentModelMatrix)
        objects.forEach { it.preUpdate(environment, parentModelMatrix) }
        return this
    }

    fun getAbsoluteModelMatrix(): Matrix4 {
        val parentModelMatrix = if (parent != null)
            parent!!.getAbsoluteModelMatrix()
        else
            Matrix4()

        return parentModelMatrix.x(getModelMatrix())
    }

    abstract fun getModelMatrix(): Matrix4
}