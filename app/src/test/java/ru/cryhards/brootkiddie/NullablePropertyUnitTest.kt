package ru.cryhards.brootkiddie

import org.junit.Assert
import org.junit.Test
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class NullablePropertyUnitTest {
//    @Test
//    fun testProperty() {
//        val a = NullableProperty(5.0, PropertyHandler())
//        val b = NullableProperty(0.1, PropertyHandler())
//        val c = NullableProperty(3.2)
//        c.handle = PropertyHandler()
//
//        var listening = 0
//        b.handle?.addOnChangeListener { _, _, _ -> listening++ }
//        b.handle?.addOnUseListener { _, _ -> listening++ }
//
//        a + b - c   // returns b, but creates a chain of properties: a -> b -> c
//        /*
//            a - (b + c) + (d - e)
//            b --> c
//                  ^
//                  |
//                  a -> d -> e
//
//            returns d
//         */
//        a.value = 6.9
//
//        Assert.assertEquals(6.9, c.value)
//        Assert.assertEquals(b.value, c.value)
//        Assert.assertEquals(2, listening)
//    }

    @Test
    fun testCoordProperty() {
        val coord = CoordProperty(0.1f, 2.3f, 9.8f)
        Assert.assertEquals(9.8f, coord.z.value!!)
    }
}