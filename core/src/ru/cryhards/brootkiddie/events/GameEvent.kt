package ru.cryhards.brootkiddie.events

/**
    Event class
 */
abstract class GameEvent(val name: String, val description: String) {
    abstract fun act(data : Map<String, Any?>)
}