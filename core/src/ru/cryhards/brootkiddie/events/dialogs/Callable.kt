package ru.cryhards.brootkiddie.events.dialogs

import java.io.Serializable

/**
 * Created by remmargorp on 11.02.18.
 */

/**
 * Callable interface to make possible function calls from json config of Dialog
 */

abstract class Callable : Serializable {
    abstract val id: String
    abstract fun act(data: Any?): Any?
}