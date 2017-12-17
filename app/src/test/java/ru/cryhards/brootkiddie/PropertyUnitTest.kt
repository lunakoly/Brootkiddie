package ru.cryhards.brootkiddie

import org.junit.Assert
import org.junit.Test
import ru.cryhards.brootkiddie.engine.util.components.*
import ru.cryhards.brootkiddie.engine.util.components.prop.*

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class PropertyUnitTest {
    @Test
    fun testNotNullableProperty() {
        val prop = NotNullableProperty(5)
        Assert.assertEquals(5, prop.value)

        var test = 0
        val handle = PropertyHandler<Int>()
        handle
                .addOnUseListener { p ->
                    if (p != null)
                        test = p
                }
                .addOnChangeListener { old, new ->
                    if (old == 5 && new == 7)
                        test = 10
                }

        prop.handle = handle
        val using = prop.value
        Assert.assertEquals(using, test)

        prop.value = 7
        Assert.assertEquals(10, test)
    }

    @Test
    fun testNullableProperty() {
        val prop = NullableProperty<Int>(null)
        Assert.assertEquals(null, prop.value)
        prop.value = 5

        var test = 0
        val handle = PropertyHandler<Int>()
        handle
                .addOnUseListener { p ->
                    if (p != null)
                        test = p
                }
                .addOnChangeListener { old, new ->
                    if (old == 5 && new == 7)
                        test = 10
                }

        prop.handle = handle
        prop.handle!!.onUse(5)
        Assert.assertEquals(5, test)

        prop.handle!!.onChange(5, 7)
        Assert.assertEquals(10, test)
    }

    @Test
    fun testTransform() {
        val coord = Transform(0.1f, 2.3f, 9.8f)
        Assert.assertEquals(0.1f, coord.x.value)
        Assert.assertEquals(2.3f, coord.y.value)
        Assert.assertEquals(9.8f, coord.z.value)

        val coord2 = Transform(
                NotNullableProperty(0.1f),
                NotNullableProperty(2.3f),
                NotNullableProperty(9.8f)
        )
        Assert.assertEquals(0.1f, coord2.x.value)
        Assert.assertEquals(2.3f, coord2.y.value)
        Assert.assertEquals(9.8f, coord2.z.value)

        val coord3 = Transform()
        Assert.assertEquals(0.0f, coord3.x.value)
        Assert.assertEquals(0.0f, coord3.y.value)
        Assert.assertEquals(0.0f, coord3.z.value)
    }

    @Test
    fun testRotation() {
        val rot = Rotation(0.1, 2.3)
        Assert.assertEquals(0.1, rot.horizontal.value, 0.0)
        Assert.assertEquals(2.3, rot.vertical.value, 0.0)

        rot.x.value = 10.0
        rot.r.value = 3.0
        Assert.assertEquals(rot.horizontal.value, rot.x.value, 0.0)

        val rot2 = Rotation(
                NotNullableProperty(0.1),
                NotNullableProperty(2.3)
        )
        Assert.assertEquals(0.1, rot2.horizontal.value, 0.0)
        Assert.assertEquals(2.3, rot2.vertical.value, 0.0)

        val rot3 = Rotation()
        Assert.assertEquals(0.0, rot3.horizontal.value, 0.0)
        Assert.assertEquals(0.0, rot3.vertical.value, 0.0)
    }

    @Test
    fun testVec2() {
        val v1 = Vec2(2.3f, 5.0f)
        Assert.assertEquals(2.3f, v1.x.value)
        Assert.assertEquals(5.0f, v1.y.value)
        Assert.assertEquals(2.3f, v1.r.value)
        Assert.assertEquals(5.0f, v1.g.value)

        val v2 = Vec2(
                NotNullableProperty(0.1f),
                NotNullableProperty(2.3f)
        )
        Assert.assertEquals(0.1f, v2.x.value)
        Assert.assertEquals(2.3f, v2.y.value)
        Assert.assertEquals(0.1f, v2.r.value)
        Assert.assertEquals(2.3f, v2.g.value)
    }

    @Test
    fun testVec3() {
        val v1 = Vec3(2.3, 5.0, 7.8)
        Assert.assertEquals(2.3, v1.x.value, 0.0)
        Assert.assertEquals(5.0, v1.y.value, 0.0)
        Assert.assertEquals(7.8, v1.z.value, 0.0)
        Assert.assertEquals(2.3, v1.r.value, 0.0)
        Assert.assertEquals(5.0, v1.g.value, 0.0)
        Assert.assertEquals(7.8, v1.b.value, 0.0)


        val v2 = Vec3(
                NotNullableProperty(0.1),
                NotNullableProperty(2.3),
                NotNullableProperty(7.8)
        )
        Assert.assertEquals(2.3, v1.x.value, 0.0)
        Assert.assertEquals(5.0, v1.y.value, 0.0)
        Assert.assertEquals(7.8, v1.z.value, 0.0)
        Assert.assertEquals(2.3, v1.r.value, 0.0)
        Assert.assertEquals(5.0, v1.g.value, 0.0)
        Assert.assertEquals(7.8, v1.b.value, 0.0)
    }

    @Test
    fun testVec4() {
        val v1 = Vec4("I", "am", "really", "tired")
        Assert.assertEquals("I", v1.x.value)
        Assert.assertEquals("am", v1.y.value)
        Assert.assertEquals("really", v1.z.value)
        Assert.assertEquals("tired", v1.w.value)
        Assert.assertEquals("I", v1.r.value)
        Assert.assertEquals("am", v1.g.value)
        Assert.assertEquals("really", v1.b.value)
        Assert.assertEquals("tired", v1.a.value)


        val v2 = Vec4(
                NotNullableProperty("I"),
                NotNullableProperty("am"),
                NotNullableProperty("really"),
                NotNullableProperty("tired")
        )
        Assert.assertEquals("I", v2.x.value)
        Assert.assertEquals("am", v2.y.value)
        Assert.assertEquals("really", v2.z.value)
        Assert.assertEquals("tired", v2.w.value)
        Assert.assertEquals("I", v2.r.value)
        Assert.assertEquals("am", v2.g.value)
        Assert.assertEquals("really", v2.b.value)
        Assert.assertEquals("tired", v2.a.value)
    }
}