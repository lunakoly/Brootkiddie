package ru.cryhards.brootkiddie.engine.util.components

import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec2

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class Rotation(
        horizontal: NotNullableProperty<Double>,
        vertical: NotNullableProperty<Double>) : Vec2<Double>(horizontal, vertical) {

    var horizontal
        get() = x
        set(value) {
            x = value
        }
    var vertical
        get() = y
        set(value) {
            y = value
        }


    constructor(horizontal: Double = 0.0,
                vertical: Double = 0.0) : this(
            NotNullableProperty(horizontal),
            NotNullableProperty(vertical))

    constructor() : this(
            NotNullableProperty(0.0),
            NotNullableProperty(0.0))
}