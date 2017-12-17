package ru.cryhards.brootkiddie.engine.environment

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

    fun getAbsoluteModelMatrix(): Matrix4 {
        val parentModelMatrix = if (parent != null)
            parent!!.getAbsoluteModelMatrix()
        else
            Matrix4()

        return parentModelMatrix.x(getModelMatrix())
    }

    abstract fun getModelMatrix(): Matrix4
}