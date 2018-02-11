package ru.cryhards.brootkiddie.events.dialogs

/**
 * Created by remmargorp on 11.02.18.
 */

/**
 * Callable with predefined data field
 */

class CallableWithData(private val callable: Callable, private val data: Any) {
    fun act(): Any? {
        return callable.act(data)
    }

    override fun toString(): String {
        return "${callable.id}($data)"
    }

    companion object {
        /**
         * parses callable from a Map<String, Any> that structured like this:
         * {
         *   "function" : "functionName",
         *   "data": {anything}
         * }
         */
        fun parseFromConfig(config: Map<String, Any>): CallableWithData {
            return CallableWithData(
                    Callables.get(config["function"]!! as String)!!,
                    config["data"]!!
            )
        }
    }
}