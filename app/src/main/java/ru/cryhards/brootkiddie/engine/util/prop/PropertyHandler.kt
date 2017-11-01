package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 27.10.2017.
 */
class PropertyHandler<T> {
    private var onChangeListeners = ArrayList<(T?, T?) -> Unit>()
    private var onUseListeners = ArrayList<(T?) -> Unit>()

    fun onChange(old: T?, new: T?): PropertyHandler<T> {
        onChangeListeners
                .forEach { it.invoke(old, new) }
        return this
    }

    fun onUse(old: T?): PropertyHandler<T> {
        onUseListeners
                .forEach { it.invoke(old) }
        return this
    }

    fun addOnChangeListener(listener: (T?, T?) -> Unit): PropertyHandler<T> {
        onChangeListeners.add(listener)
        return this
    }

    fun addOnUseListener(listener: (T?) -> Unit): PropertyHandler<T> {
        onUseListeners.add(listener)
        return this
    }
}