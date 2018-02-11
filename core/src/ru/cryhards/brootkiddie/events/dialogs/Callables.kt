package ru.cryhards.brootkiddie.events.dialogs

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.events.Events

/**
 * Created by remmargorp on 11.02.18.
 */

/**
 * Library that hold all functions that can be called from JSON config of Dialog
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

        callables.put("runEvent", object : Callable(){
            override val id: String
                get() = "runEvent"

            override fun act(data: Any?): Any? {
                Events.eventsByName[(data as Map<String, Any>)["event"]]!!.act(data["data"] as Map<String, Any?>)
                return null
            }

        })
    }

    fun get(key: String): Callable? {
        return callables[key]
    }
}