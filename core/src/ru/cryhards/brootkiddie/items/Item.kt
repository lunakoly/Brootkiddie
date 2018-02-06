package ru.cryhards.brootkiddie.items

/**
 * Represents an item that the Player may own
 */
open class Item(var name: String, var info: String, val type: Type) {

    /**
     * Represents an item type
     */
    enum class Type(val info: String) {
        SCRIPT("Code written by the community that can do something. Combining the right code pieces will increment the common malware effect. Some scripts may have dependencies to another ones (frameworks)"),
        MALWARE("A multifunctional set of cooperating scripts (source codes)")
    }


    /**
     * Holds all effects applied to the item
     */
    val effects = ArrayList<Effect>()

    /**
     * Returns effect with matching name
     */
    fun findEffect(name: String) = effects.find { it.name == name }


    /**
     * Represents an effect that can be applied to an item
     */
    open class Effect(val name: String, val info: String) {
        /**
         * Affects he given malware stats
         */
        open fun affect(malware: Malware): Effect {
            return this
        }
    }
}