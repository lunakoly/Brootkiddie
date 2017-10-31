package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 26.10.2017.
 */
open class Property<T>(value: T? = null,
                       var handle: PropertyHandler<T>? = null) {

    var value: T? = value
        get() {
            handle?.onUse(this, field)
            return field
        }
        set(value) {
            handle?.onChange(this, field, value)
            field = value
        }

    operator fun plus(prop: Property<T>) : Property<T> {
        handle?.bindForward(prop)
        return prop
    }

    operator fun minus(prop: Property<T>) : Property<T> {
        handle?.bindForward(prop)
        return this
    }
}