package ru.cryhards.brootkiddie

import org.junit.Test

import org.junit.Assert.*
import ru.cryhards.brootkiddie.engine.util.Property
import ru.cryhards.brootkiddie.engine.util.PropertyHandler

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testAddition() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testProperty() {
        val a = Property(5.0, PropertyHandler())
        val b = Property(0.1, PropertyHandler())
        val c = Property(3.2)
        c.handle = PropertyHandler()

        var listening = 0
        b.handle?.addOnChangeListener { _, _, _ -> listening++ }
        b.handle?.addOnUseListener { _, _ -> listening++ }

        a + b - c   // returns b, but creates a chain of properties: a -> b -> c
        /*
            a - (b + c) + (d - e)
            b --> c
                  ^
                  |
                  a -> d -> e

            returns d
         */
        a.value = 6.9

        assertEquals(6.9, c.value)
        assertEquals(b.value, c.value)
        assertEquals(2, listening)
    }
}
