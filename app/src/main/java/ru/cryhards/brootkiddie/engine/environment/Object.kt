package ru.cryhards.brootkiddie.engine.environment

import ru.cryhards.brootkiddie.engine.util.components.Component
import ru.cryhards.brootkiddie.engine.util.components.Transform
import ru.cryhards.brootkiddie.engine.util.maths.Mat4

/**
 * Created with love by luna_koly on 10.12.2017.
 */
abstract class Object {
    val transform = Transform()
    val components = ArrayList<Component>()

    val objects = ArrayList<Object>()
    var parent: Object? = null

    init {
        components.add(transform)
    }

    inline fun <reified T> getComponent(): T? {
        for (comp in components)
            if (comp is T)
                return comp
        return null
    }

    open fun draw(environment: Environment, parentModelMatrix: Mat4): Object {
        objects
                .forEach { it.draw(environment, parentModelMatrix.multiply(getModelMatrix())) }
        return this
    }

    fun <T> ArrayList<T>.add(element: Object): Boolean {
        element.parent = this@Object
        return this.add(element)
    }

    fun getFullModelMatrix(): Mat4 {
        val parentModelMatrix = if (parent != null)
            parent!!.getFullModelMatrix()
        else
            Mat4.identity()

        return getModelMatrix().multiply(parentModelMatrix)
    }

    abstract fun getModelMatrix(): Mat4
}