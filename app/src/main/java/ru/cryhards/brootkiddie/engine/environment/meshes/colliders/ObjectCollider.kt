package ru.cryhards.brootkiddie.engine.environment.meshes.colliders

import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Helps calculate object collisions
 *
 * Created with love by luna_koly on 24.12.2017.
 */
interface ObjectCollider {
    /**
     * If intersection point exists, returns
     * point [x, y, z, 1]
     * Otherwise returns [0, 0, 0, 0]
     */
    fun intersect(origin: Vector4, ray: Vector4): Vector4
}