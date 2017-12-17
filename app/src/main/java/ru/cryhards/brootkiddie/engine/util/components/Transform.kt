package ru.cryhards.brootkiddie.engine.util.components

import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Transform component
 * Represents object transform
 *
 * Created with love by luna_koly on 29.10.2017.
 */
class Transform(x: NotNullableProperty<Float>,
                y: NotNullableProperty<Float>,
                z: NotNullableProperty<Float>) : Vec3<Float>(x, y, z) {

    /**
     * Creates (x, y, z)
     */
    constructor(x: Float = 0.0f,
                y: Float = 0.0f,
                z: Float = 0.0f) : this(
            NotNullableProperty(x),
            NotNullableProperty(y),
            NotNullableProperty(z))

    /**
     * Creates (0, 0, 0)
     */
    constructor() : this(
            NotNullableProperty(0.0f),
            NotNullableProperty(0.0f),
            NotNullableProperty(0.0f))
}