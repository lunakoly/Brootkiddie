package ru.cryhards.brootkiddie

import org.junit.Test
import ru.cryhards.brootkiddie.engine.util.Logger
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4
import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Created with love by luna_koly on 16.12.2017.
 */
class MathsTest {
    @Test
    fun vector4Test() {
        var v = Vector4()
        println(Logger.getVector4(v))
        v = Vector4(0f, 0f, 0f, 0f)
        println(Logger.getVector4(v))
        v = Vector4(1f, 2f, 3f, 4f)
        println(Logger.getVector4(v))
        v = Vector4()
        v.x = 1f
        v.y = 2f
        v.z = 3f
        v.w = 4f
        println(Logger.getVector4(v))
        println("Vector4: [" + v[0] + ", " + v[1] + ", " + v[2] + ", " + v[3] + "]")
    }

    @Test
    fun matrix4Test() {
        var m = Matrix4()
        println(Logger.getMatrix4(m))
        m = Matrix4.getTranslation(1f, 2f, 3f)
        println(Logger.getMatrix4(m))
        m = m.x(Matrix4.getTranslation(1f, 2f, 3f))
        println(Logger.getMatrix4(m))

        m = Matrix4.getScale(1f, 2f, 3f)
        println(Logger.getMatrix4(m))

        var v = Vector4(1f, 2f, 3f, 1f)
        v = m.x(v)
        println(Logger.getVector4(v))

        m = Matrix4.getTranslation(-1f, -1f, -1f)
        println(Logger.getVector4(m.x(v)))

        m = Matrix4(
                1f, 0f, 0f, 5f,
                0f, 1f, 0f, 6f,
                0f, 0f, 1f, 7f,
                0f, 0f, 0f, 1f
        )
        println(Logger.getMatrix4(m))
        println(Logger.getArr(m.raw().toTypedArray()))
        println(Logger.getVector4(m[3]))
    }

    @Test
    fun matTest() {
        val m1 = Matrix4(
                1f, 0f, 2f, -1f,
                -2f, 0f, -4f, 2f,
                1f, 0f, 2f, -1f,
                3f, 0f, 6f, -3f
        )

        val m2 = Matrix4(
                2f, 1f, 3f, -1f,
                -4f, -2f, -6f, 2f,
                2f, 1f, 3f, -1f,
                6f, 3f, 9f, -3f
        )

        println(Logger.getMatrix4(m1.x(m2)))
        println(Logger.getMatrix4(m1.reflect()))
    }
}