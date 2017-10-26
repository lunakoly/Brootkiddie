package ru.cryhards.brootkiddie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.cryhards.brootkiddie.engine.util.Property

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // testing Property
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

        println(c.value ?: "lulz")
    }
}
