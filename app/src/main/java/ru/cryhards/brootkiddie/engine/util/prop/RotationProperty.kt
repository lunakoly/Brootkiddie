package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class RotationProperty(
        val x: Property<Double> = Property(0.0),
        val y: Property<Double> = Property(0.0),
        val z: Property<Double> = Property(0.0)) {

    constructor(x: Double = 0.0,
                y: Double = 0.0,
                z: Double = 0.0) : this(Property(x), Property(y), Property(z))

    constructor() : this(Property(0.0), Property(0.0), Property(0.0))
}