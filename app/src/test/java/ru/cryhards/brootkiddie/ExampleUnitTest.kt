package ru.cryhards.brootkiddie

import org.junit.Test

import org.junit.Assert.*
import ru.cryhards.brootkiddie.engine.util.Property

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
        val a = Property(5.0)
        val b = Property(0.1)
        val c = Property(3.2)

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

        assertEquals(c.value, 6.9)
    }
}
