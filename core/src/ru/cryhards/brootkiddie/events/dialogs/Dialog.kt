package ru.cryhards.brootkiddie.events.dialogs

import com.badlogic.gdx.Gdx
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by remmargorp on 11.02.18.
 */

/**
 * Represents Dialog's logic
 */

class Dialog(config: Map<String, Any>) {

    /**
     * Id of the dialog
     * Not used yet
     */
    val id: String

    /**
     * states = Map<id, State>
     * Represents all states of the Finite Automaton of the Dialog
     */
    private val states = mutableMapOf<String, State>()

    /**
     * transitions = Map<fromId, Transition>
     * Represents all transitions of the Finite Automaton of the Dialog
     */
    private val transitions = mutableMapOf<String, MutableList<Transition>>()

    /**
     * Current state of the Dialog
     */
    private var currentState: State? = null


    /**
     * This init block parses JSON config
     */
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

    /**
     * Returns State specified by id
     */
    fun getStateById(id: String): State? {
        return states.get(id)
    }

    /**
     * Returns all Transitions from specified State id
     */
    fun getTransitions(id: String): List<Transition> {
        if (transitions.containsKey(id))
            return transitions[id] as List<Transition>
        return emptyList()
    }

    /**
     * Return all Transitions from currentState
     */
    fun getTransitions(): List<Transition> {
        if (transitions.containsKey(currentState!!.id))
            return transitions[currentState!!.id] as List<Transition>
        return emptyList()
    }

    /**
     * Returns currentState
     */
    fun getCurrentState(): State? {
        return currentState
    }

    /**
     * Makes specified transition (if it's available)
     */
    fun go(transition: Transition): Boolean {
        if (transition.isAvailable()) {
            currentState = getStateById(transition.to)
            currentState!!.trigger()
            return true
        }
        return false
    }

    /**
     * Represents single State of the Finite Automaton (FA) used in Dialog
     */
    class State(config: Map<String, Any>) {
        /**
         * Id of the State
         */
        val id = config["id"] as String
        /**
         * start = true if this is the initial state of the FA
         */
        val start = if (config.containsKey("start")) {
            config["start"] as Boolean
        } else {
            false
        }
        /**
         * info - callable, produces info message for player
         */
        private val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)
        /**
         * triggers - list of callables that will be triggered when this state will be reached
         */
        private val triggers = ArrayList<CallableWithData>()

        init {
            for (rawTrigger in config["triggers"] as ArrayList<Any>) {
                triggers.add(CallableWithData.parseFromConfig(rawTrigger as Map<String, Any>))
            }
        }

        /**
         * produces info message
         */
        fun getInfo(): String {
            return info.act() as String
        }

        /**
         * triggers all predefined actions
         */
        fun trigger() {
            triggers.forEach { it.act() }
        }

        override fun toString(): String {
            return "STATE id=$id start=$start triggers=$triggers info=$info"
        }
    }

    /**
     * Represents single Transition of FA
     */
    class Transition(config: Map<String, Any>) {
        /**
         * from & to - State ids
         */
        val from = config["from"] as String
        val to = config["to"] as String

        /**
         * info - callable, produces info message for player
         */
        private val info = CallableWithData.parseFromConfig(config["info"] as Map<String, Any>)
        /**
         * availability - callable, determines whether or not this transition is available [Boolean]
         */
        private val availability = CallableWithData.parseFromConfig(config["availability"] as Map<String, Any>)

        override fun toString(): String {
            return "TRANSITION $from-->$to info:$info availability:$availability"
        }

        /**
         * Determines Transition's availability
         */
        fun isAvailable(): Boolean {
            return availability.act() as Boolean
        }

        /**
         * Produces info message for player
         */
        fun getInfo(): String {
            return info.act() as String
        }
    }

    companion object {
        /**
         * Reads the config file to make a Dialog
         */
        fun readFromFile(jsonConfigPath: String): Dialog {
            val gson = Gson()
            val config = gson.fromJson<Any>(Gdx.files.internal(jsonConfigPath).reader())
            Gdx.app.log("Dialog", config.toString())
            return Dialog(config as Map<String, Any>)
        }
    }
}