package ru.cryhards.brootkiddie.events.dialogs

import com.badlogic.gdx.Gdx
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by remmargorp on 11.02.18.
 */

class Dialog(config: Map<String, Any>) {

    val id: String
    private val states = mutableMapOf<String, State>()
    private val transitions = mutableMapOf<String, MutableList<Transition>>()
    private var currentState: State? = null

    init {
        Gdx.app.log("Dialog", "Parsing config...")

        id = config["id"] as String

        for (rawState in (config["states"] as ArrayList<Any>)) {
            val state = State(rawState as Map<String, Any>)
            Gdx.app.log("Dialog", state.toString())
            states.put(state.id, state)

            if (state.start) {
                if (currentState != null) throw Exception("More than 1 start state in Dialog")
                currentState = state
            }
        }

        if (currentState == null) throw Exception("No start state in Dialog")

        for (rawTransition in (config["transitions"] as ArrayList<Any>)) {
            val transition = Transition(rawTransition as Map<String, Any>)
            Gdx.app.log("Dialog", transition.toString())

            if (transitions.containsKey(transition.from)) {
                transitions[transition.from]!!.add(transition)
            } else {
                transitions[transition.from] = mutableListOf(transition)
            }
        }

        Gdx.app.log("Dialog", "Parsing is completed")
        Gdx.app.log("Dialog", "states: $states")
        Gdx.app.log("Dialog", "transitions: $transitions")
        Gdx.app.log("Dialog", "start state: $currentState")

        currentState!!.trigger()
    }

    fun getStateById(id: String): State? {
        return states.get(id)
    }

    fun getTransitions(id: String): List<Transition> {
        if (transitions.containsKey(id))
            return transitions[id] as List<Transition>
        return emptyList()
    }

    fun getTransitions(): List<Transition> {
        if (transitions.containsKey(currentState!!.id))
            return transitions[currentState!!.id] as List<Transition>
        return emptyList()
    }

    fun getCurrentState(): State? {
        return currentState
    }

    fun go(transition: Transition): Boolean {
        if (transition.isAvailable()) {
            currentState = getStateById(transition.to)
            currentState!!.trigger()
            return true
        }
        return false
    }

    class State(config: Map<String, Any>) {
        val id = config["id"] as String
        val start = if (config.containsKey("start")) {
            config["start"] as Boolean
        } else {
            false
        }
        private val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)
        private val triggers = ArrayList<CallableWithData>()
        init {
            for (rawTrigger in config["triggers"] as ArrayList<Any>) {
                triggers.add(CallableWithData.parseFromConfig(rawTrigger as Map<String, Any>))
            }
        }

        fun getInfo(): String {
            return info.act() as String
        }

        fun trigger() {
            triggers.forEach { it.act() }
        }

        override fun toString(): String {
            return "STATE id=$id start=$start triggers=$triggers info=$info"
        }
    }

    class Transition(config: Map<String, Any>) {
        val from = config["from"] as String
        val to = config["to"] as String
        private val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)
        private val availability = CallableWithData.parseFromConfig(config["availability"] as Map<String, Any>)

        override fun toString(): String {
            return "TRANSITION $from-->$to info:$info availability:$availability"
        }

        fun isAvailable(): Boolean {
            return availability.act() as Boolean
        }

        fun getInfo(): String {
            return info.act() as String
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