package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 27.10.2017.
 */
class PropertyHandler<T> {
    private var onChangeListeners = ArrayList<(Property<T>, T?, T?) -> Unit>()
    private var onUseListeners = ArrayList<(Property<T>, T?) -> Unit>()

    fun bindForward(prop: Property<T>): PropertyHandler<T> {
        onChangeListeners.add {
            _, _, new ->
            prop.value = new
        }
        return this
    }

    fun onChange(prop: Property<T>, old: T?, new: T?): PropertyHandler<T> {
        onChangeListeners
                .forEach { it.invoke(prop, old, new) }
        return this
    }

    fun onUse(prop: Property<T>, old: T?): PropertyHandler<T> {
        onUseListeners
                .forEach { it.invoke(prop, old) }
        return this
    }

    fun addOnChangeListener(listener: (Property<T>, T?, T?) -> Unit): PropertyHandler<T> {
        onChangeListeners.add(listener)
        return this
    }

    fun addOnUseListener(listener: (Property<T>, T?) -> Unit): PropertyHandler<T> {
        onUseListeners.add(listener)
        return this
    }
}