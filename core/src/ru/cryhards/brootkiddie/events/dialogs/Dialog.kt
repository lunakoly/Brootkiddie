package ru.cryhards.brootkiddie.events.dialogs

import com.badlogic.gdx.Gdx
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by remmargorp on 11.02.18.
 */

class Dialog(config: Map<String, Any>) {

    val id: String
    val states = mutableMapOf<String, State>()
    val transitions = mutableMapOf<String, MutableList<Transition>>()
    var currentState: State? = null

    init {
        Gdx.app.log("Dialog", "Parsing config...")

        id = config["id"] as String

        for (state in (config["states"] as ArrayList<Any>)) {
            val parsed = State(state as Map<String, Any>)
            Gdx.app.log("Dialog", parsed.toString())
            states.put(parsed.id, parsed)

            if (parsed.start) {
                if (currentState != null) throw Exception("More than 1 start state in Dialog")
                currentState = parsed
            }
        }

        if (currentState == null) throw Exception("No start state in Dialog")

        for (transition in (config["transitions"] as ArrayList<Any>)) {
            val parsed = Transition(transition as Map<String, Any>)
            Gdx.app.log("Dialog", parsed.toString())

            if (transitions.containsKey(parsed.from)) {
                transitions[parsed.from]!!.add(parsed)
            } else {
                transitions[parsed.from] = mutableListOf(parsed)
            }
        }

        Gdx.app.log("Dialog", "Parsing is completed")
        Gdx.app.log("Dialog", "states: $states")
        Gdx.app.log("Dialog", "transitions: $transitions")
    }

    class State(config: Map<String, Any>) {
        val id = config["id"] as String
        val start = if (config.containsKey("start")) {
            config["start"] as Boolean
        } else {
            false
        }
        val triggers = ArrayList<CallableWithData>()

        init {
            for (rawtrigger in config["triggers"] as ArrayList<Any>) {
                triggers.add(CallableWithData.parseFromConfig(rawtrigger as Map<String, Any>))
            }
        }

        val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)

        override fun toString(): String {
            return "STATE id=$id start=$start triggers=$triggers info=$info"
        }
    }

    class Transition(config: Map<String, Any>) {
        val from = config["from"] as String
        val to = config["to"] as String
        val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)
        val availability = CallableWithData.parseFromConfig(config["availability"] as Map<String, Any>)

        override fun toString(): String {
            return "TRANSITION $from-->$to info:$info availability:$availability"
        }
    }


    companion object {
        fun readFromFile(jsonConfigPath: String): Dialog {
            val gson = Gson()
            val config = gson.fromJson<Any>(Gdx.files.internal(jsonConfigPath).reader())
            Gdx.app.log("Dialog", config.toString())
            return Dialog(config as Map<String, Any>)
        }
    }
}