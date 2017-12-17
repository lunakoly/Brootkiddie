package ru.cryhards.brootkiddie.engine.environment.util

/**
 * Created with love by luna_koly on 17.12.2017.
 */
class ObservableCollection<T>: ArrayList<T>() {
    var listener: ((T) -> Unit)? = null

    override fun add(element: T): Boolean {
        listener?.invoke(element)
        return super.add(element)
    }
}