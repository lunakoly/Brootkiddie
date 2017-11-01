package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class RotationProperty(
        val horizontal: NotNullableProperty<Double> = NotNullableProperty(0.0),
        val vertical:   NotNullableProperty<Double> = NotNullableProperty(0.0)) {

    constructor(horizontal: Double = 0.0,
                vertical: Double = 0.0) : this(
            NotNullableProperty(horizontal),
            NotNullableProperty(vertical))

    constructor() : this(
            NotNullableProperty(0.0),
            NotNullableProperty(0.0))
}