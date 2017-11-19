package ru.cryhards.brootkiddie.engine.util.maths

import android.opengl.Matrix
import java.lang.Math.cos
import java.lang.Math.sin

class Mat4 {
    open var m: FloatArray

    constructor() {
        m = FloatArray(16)
    }


    constructor(x0: Float, y0: Float, z0: Float, w0: Float,
                x1: Float, y1: Float, z1: Float, w1: Float,
                x2: Float, y2: Float, z2: Float, w2: Float,
                x3: Float, y3: Float, z3: Float, w3: Float) {
        m = floatArrayOf(
                x0, y0, z0, w0,
                x1, y1, z1, w1,
                x2, y2, z2, w2,
                x3, y3, z3, w3)
    }

    fun multiply(rhs: Mat4): Mat4 {
        var res = Mat4()
        Matrix.multiplyMM(res.m, 0, m, 0, rhs.m, 0)
        return res
    }

    fun multiply(vec: Vec4): Vec4 {
        var res = Vec4()
        Matrix.multiplyMV(res.v, 0, m, 0, vec.v, 0)
        return res
    }

    fun transpose(): Mat4 {
        var res = Mat4()
        Matrix.transposeM(res.m, 0, m, 0)
        return res
    }

    fun invert(): Mat4? {
        var res: Mat4? = Mat4()
        if (!Matrix.invertM(res!!.m, 0, m, 0)) {
            res = null
        }
        return res
    }

    fun scale(x: Float, y: Float, z: Float): Mat4 {
        var res = Mat4()
        Matrix.scaleM(res.m, 0, m, 0, x, y, z)
        return res
    }

    fun scale(factor: Float): Mat4 = scale(factor, factor, factor)

    fun translate(x: Float, y: Float, z: Float): Mat4 {
        return this.multiply(translate(x, y, z))
    }

    fun rotate(alpha: Float, x: Float, y: Float, z: Float): Mat4 {
        var res = Mat4()
        Matrix.rotateM(res.m, 0, m, 0, alpha, x, y, z)
        return res
    }

    companion object {
        fun ortho(left: Float, right: Float, bottom: Float, top: Float,
                  near: Float, far: Float): Mat4 {
            var res = Mat4()
            Matrix.orthoM(res.m, 0, left, right, bottom, top, near, far)
            return res
        }

        fun frustrum(left: Float, right: Float, bottom: Float, top: Float,
                     near: Float, far: Float): Mat4 {
            var res = Mat4()
            Matrix.frustumM(res.m, 0, left, right, bottom, top, near, far)
            return res
        }

        fun perspective(fovy: Float, aspect: Float, zNear: Float, zFar: Float): Mat4 {
            var res = Mat4()
            Matrix.perspectiveM(res.m, 0, fovy, aspect, zNear, zFar)
            return res
        }

        fun diagonal(d: Float): Mat4 {
            var res = Mat4()
            res.m[0] = d
            res.m[5] = d
            res.m[10] = d
            res.m[15] = d
            return res
        }

        fun identity(): Mat4 = diagonal(1.0f)

        fun translate(x: Float, y: Float, z: Float): Mat4 = Mat4(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                x, y, z, 1f
        )

        fun rotate(alpha: Float, x: Float, y: Float, z: Float): Mat4 {
            var res = Mat4()
            Matrix.setRotateM(res.m, 0, alpha, x, y, z)
            return res
        }

        fun rotateX(alpha: Double): Mat4 = Mat4(
                1f, 0f, 0f, 0f,
                0f, cos(alpha).toFloat(), -sin(alpha).toFloat(), 0f,
                0f, sin(alpha).toFloat(), cos(alpha).toFloat(), 0f,
                0f, 0f, 0f, 1f
        )

        fun rotateY(alpha: Double): Mat4 = Mat4(
                cos(alpha).toFloat(), 0f, -sin(alpha).toFloat(), 0f,
                0f, 1f, 0f, 0f,
                sin(alpha).toFloat(), 0f, cos(alpha).toFloat(), 0f,
                0f, 0f, 0f, 1f
        )

        fun rotateZ(alpha: Double): Mat4 = Mat4(
                cos(alpha).toFloat(), -sin(alpha).toFloat(), 0f, 0f,
                sin(alpha).toFloat(), cos(alpha).toFloat(), 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
        )

        fun rotateEuler(x: Float, y: Float, z: Float): Mat4 {
            var res = Mat4()
            Matrix.setRotateEulerM(res.m, 0, x, y, z)
            return res
        }

        fun lookAt(eyeX: Float, eyeY: Float, eyeZ: Float,
                   centerX: Float, centerY: Float, centerZ: Float,
                   upX: Float, upY: Float, upZ: Float): Mat4 {
            var res = Mat4()
            Matrix.setLookAtM(res.m, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ,
                    upX, upY, upZ)
            return res
        }

        fun lookAroundRotation(horizontally: Double, vertically: Double): Mat4 {
            val mat = identity()

            val ry = rotateY(horizontally)
            val rx = rotateX(vertically)

            return mat.multiply(rx).multiply(ry)
        }
    }
}