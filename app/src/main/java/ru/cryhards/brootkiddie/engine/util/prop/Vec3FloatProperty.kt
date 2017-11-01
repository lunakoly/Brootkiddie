package ru.cryhards.brootkiddie.engine.util.prop

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class Vec3FloatProperty(val x: Property<Float> = Property(0.0f),
                        val y: Property<Float> = Property(0.0f),
                        val z: Property<Float> = Property(0.0f)) {

    constructor(x: Float = 0.0f,
                y: Float = 0.0f,
                z: Float = 0.0f) : this(Property(x), Property(y), Property(z))

    constructor() : this(Property(0.0f), Property(0.0f), Property(0.0f))
}