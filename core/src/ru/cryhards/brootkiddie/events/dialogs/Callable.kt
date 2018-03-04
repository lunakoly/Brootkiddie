package ru.cryhards.brootkiddie.events.dialogs

/**
 * Created by remmargorp on 11.02.18.
 */

/**
 * Callable interface to make possible function calls from json config of Dialog
 */

abstract class Callable {
    abstract val id: String
    abstract fun act(data: Any?): Any?
}