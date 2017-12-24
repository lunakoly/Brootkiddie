package ru.cryhards.brootkiddie.engine.environment.meshes.colliders

import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Defines Plane collision behaviour
 *
 * Created with love by luna_koly on 24.12.2017.
 */
class PlaneCollider(private val plane: StaticObject): ObjectCollider {
    /**
     * Möller-Trumbore algorithm
     *
     * If intersection point exists, returns
     * point [x, y, z, 1]
     * Otherwise returns [0, 0, 0, 0]
     */
    @Suppress("FunctionName")
    private fun MollerTrumbore(origin: Vector4, ray: Vector4, p1: Vector4, p2: Vector4, p3: Vector4): Vector4 {
        val edge1 = p2 - p1
        val edge2 = p3 - p1
        val pvec = ray.cross(edge2)
        val det = edge1.dot(pvec)

        // triangle is backfacing of parallel to ray
        if (det < 0.001) return Vector4()

        val invDet = 1.0f / det

        val tvec = origin - p1
        val u = tvec.dot(pvec) * invDet
        // if point is outside of the triangle
        if (u < 0 || u > 1) return Vector4()

        val qvec = tvec.cross(edge1)
        val v = ray.dot(qvec) * invDet
        // if point is outside of the triangle
        if (v < 0 || u + v > 1) return Vector4()

        val t = edge2.dot(qvec) * invDet
        return origin + ray * t
    }

    /**
     * If intersection point exists, returns
     * point [x, y, z, 1]
     * Otherwise returns [0, 0, 0, 0]
     */
    override fun intersect(origin: Vector4, ray: Vector4): Vector4 {
        val mat = plane.getAbsoluteModelMatrix()
        val p1 = mat.x(Vector4(1f, 1f, 0f, 1f))
        val p2 = mat.x(Vector4(-1f, 1f, 0f, 1f))
        val p3 = mat.x(Vector4(-1f, -1f, 0f, 1f))
        val p4 = mat.x(Vector4(1f, -1f, 0f, 1f))

        var p = MollerTrumbore(origin, ray, p1, p2, p3)
        if (p.w == 0f)
            p = MollerTrumbore(origin, ray, p1, p3, p4)
        return p
    }
}