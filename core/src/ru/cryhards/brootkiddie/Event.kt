package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.levels.Level

/**
 * Created by Dima on 04.02.2018.
 */
abstract class Event(level: Level) {
    abstract val name : String
    abstract val description : String

    abstract fun act()
}