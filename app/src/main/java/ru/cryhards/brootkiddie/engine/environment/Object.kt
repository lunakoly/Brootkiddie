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

    init {
        components.add(transform)
    }

    inline fun <reified T> getComponent(): T? {
        for (comp in components)
            if (comp is T)
                return comp
        return null
    }

    open fun draw(environment: Environment): Object {
        // Ha-ha, nothing man, keep going
        return this
    }

    abstract fun getModelMatrix(): Mat4
}