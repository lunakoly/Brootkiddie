package ru.cryhards.brootkiddie.events

/**
 * Created by Dima on 11.02.2018.
 */
abstract class GameEvent(val name: String, val description: String) {
    abstract fun act()
}