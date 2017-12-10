package ru.cryhards.brootkiddie.engine.util.components.prop

/**
 * Created with love by luna_koly on 26.10.2017.
 */
open class NullableProperty<T>(value: T? = null,
                               var handle: PropertyHandler<T>? = null) {

    var value: T? = value
        get() {
            handle?.onUse(field)
            return field
        }
        set(value) {
            handle?.onChange(field, value)
            field = value
        }
}