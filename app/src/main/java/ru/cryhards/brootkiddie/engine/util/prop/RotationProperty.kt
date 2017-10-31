package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class RotationProperty(
        val horizontal: Property<Double> = Property(0.0),
        val vertical: Property<Double> = Property(0.0)) {

    constructor(horizontal: Double = 0.0,
                vertical: Double = 0.0) : this(Property(horizontal), Property(vertical))

    constructor() : this(Property(0.0), Property(0.0))
}