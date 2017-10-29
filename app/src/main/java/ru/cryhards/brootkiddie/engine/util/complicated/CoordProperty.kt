package ru.cryhards.brootkiddie.engine.util.complicated

import ru.cryhards.brootkiddie.engine.util.Property

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class CoordProperty(val x: Property<Float> = Property(0.0f),
                    val y: Property<Float> = Property(0.0f),
                    val z: Property<Float> = Property(0.0f)) {

    constructor(x: Float = 0.0f,
                y: Float = 0.0f,
                z: Float = 0.0f) : this(Property(x), Property(y), Property(z))

    constructor() : this(Property(0.0f), Property(0.0f), Property(0.0f))
}