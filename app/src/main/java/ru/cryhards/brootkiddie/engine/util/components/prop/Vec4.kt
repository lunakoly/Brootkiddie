package ru.cryhards.brootkiddie.engine.util.components.prop

import ru.cryhards.brootkiddie.engine.util.components.Component

/**
 * Created with love by luna_koly on 10.12.2017.
 */
open class Vec4<T>(
        x: NotNullableProperty<T>,
        y: NotNullableProperty<T>,
        z: NotNullableProperty<T>,
        w: NotNullableProperty<T>): Component() {

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

    var z: NotNullableProperty<T> = z
        set(value) {
            b = value
            field = value
        }

    var w: NotNullableProperty<T> = w
        set(value) {
            a = value
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

    var b = z
        set(value) {
            z = value
            field = value
        }


    var a = w
        set(value) {
            w = value
            field = value
        }

    constructor(x: T, y: T, z: T, w: T) : this(
            NotNullableProperty(x),
            NotNullableProperty(y),
            NotNullableProperty(z),
            NotNullableProperty(w))
}