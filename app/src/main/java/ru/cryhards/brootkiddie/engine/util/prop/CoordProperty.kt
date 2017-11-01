package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class CoordProperty(val x: NotNullableProperty<Float> = NotNullableProperty(0.0f),
                    val y: NotNullableProperty<Float> = NotNullableProperty(0.0f),
                    val z: NotNullableProperty<Float> = NotNullableProperty(0.0f)) {

    constructor(x: Float = 0.0f,
                y: Float = 0.0f,
                z: Float = 0.0f) : this(
            NotNullableProperty(x),
            NotNullableProperty(y),
            NotNullableProperty(z))

    constructor() : this(
            NotNullableProperty(0.0f),
            NotNullableProperty(0.0f),
            NotNullableProperty(0.0f))
}