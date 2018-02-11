package ru.cryhards.brootkiddie.events.dialogs

import com.badlogic.gdx.Gdx

/**
 * Created by remmargorp on 11.02.18.
 */

object Callables {
    private val callables = mutableMapOf<String, Callable>()

    init {
        callables.put("printer", object : Callable() {
            override val id: String
                get() = "printer"

            override fun act(data: Any?): Any? {
                return data as String
            }
        })

        callables.put("logger", object : Callable() {
            override val id: String
                get() = "logger"

            override fun act(data: Any?): Any? {
                Gdx.app.log("Callable-logger", data.toString())
                return null
            }
        })

        callables.put("true", object : Callable() {
            override val id: String
                get() = "true"

            override fun act(data: Any?): Any? {
                return true
            }
        })

        callables.put("false", object : Callable() {
            override val id: String
                get() = "false"

            override fun act(data: Any?): Any? {
                return false
            }
        })
    }

    fun get(key: String): Callable? {
        return callables[key]
    }
}