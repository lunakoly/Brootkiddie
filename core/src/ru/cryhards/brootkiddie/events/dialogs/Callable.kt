package ru.cryhards.brootkiddie.events.dialogs

/**
 * Created by remmargorp on 11.02.18.
 */

abstract class Callable {
    abstract val id: String
    abstract fun act(data: Any?): Any?
}