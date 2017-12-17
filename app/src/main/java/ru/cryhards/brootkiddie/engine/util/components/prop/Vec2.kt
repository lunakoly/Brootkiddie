package ru.cryhards.brootkiddie.engine.util.components.prop

import ru.cryhards.brootkiddie.engine.util.components.Component

/**
 * Container for 2 values
 *
 * Created with love by luna_koly on 10.12.2017.
 */
open class Vec2<T>(
        x: NotNullableProperty<T>,
        y: NotNullableProperty<T>) : Component() {

    var x: NotNullableProperty<T> = x
        set(value) {
            r = value
            field = value
        }

    var y: NotNullableProperty<T> = y
        set(value) {
            g = value
            field = value
        }

    var r = x
        set(value) {
            x = value
            field = value
        }

    var g = y
        set(value) {
            y = value
            field = value
        }

    constructor(x: T, y: T) : this(
            NotNullableProperty(x),
            NotNullableProperty(y))
}