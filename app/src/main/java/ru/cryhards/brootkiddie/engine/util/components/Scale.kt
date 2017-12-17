package ru.cryhards.brootkiddie.engine.util.components

import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Scale component
 * Represents object scale
 *
 * Created with love by luna_koly on 12/11/17.
 */
class Scale(
        x: NotNullableProperty<Float>,
        y: NotNullableProperty<Float>,
        z: NotNullableProperty<Float>) : Vec3<Float>(x, y, z) {

    /**
     * Creates (x, y, z)
     */
    constructor(x: Float = 1.0f,
                y: Float = 1.0f,
                z: Float = 1.0f) : this(
            NotNullableProperty(x),
            NotNullableProperty(y),
            NotNullableProperty(z))

    /**
     * Creates (1, 1, 1)
     */
    @Suppress("unused")
    constructor() : this(
            NotNullableProperty(1.0f),
            NotNullableProperty(1.0f),
            NotNullableProperty(1.0f))
}