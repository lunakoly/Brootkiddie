package ru.cryhards.brootkiddie.engine.util

/**
 * Created with love by luna_koly on 26.10.2017.
 */
open class Property<T>(value: T? = null) {
    private var onChangeListeners = ArrayList<(T?, T?) -> Unit>()

    var value: T? = value
    set(value) {
        onChangeListeners
                .forEach { it.invoke(field, value) }
        field = value
    }

    private fun bindForward(prop: Property<T>) {
        onChangeListeners.add {
            _, new ->
            prop.value = new
        }
    }

    operator fun plus(prop: Property<T>) : Property<T> {
        bindForward(prop)
        return prop
    }

    operator fun minus(prop: Property<T>) : Property<T> {
        bindForward(prop)
        return this
    }
}