package ru.cryhards.brootkiddie.events.dialogs

/**
 * Created by remmargorp on 11.02.18.
 */

class CallableWithData(val callable: Callable, val data: Any) {
    fun act(): Any? {
        return callable.act(data)
    }

    override fun toString(): String {
        return "${callable.id}($data)"
    }

    companion object {
        fun parseFromConfig(config: Map<String, Any>): CallableWithData {
            return CallableWithData(
                    Callables.get(config["function"]!! as String)!!,
                    config["data"]!!
            )
        }
    }
}