package ru.cryhards.brootkiddie.levels

import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.malware.Malware

abstract class Level(val player: Player) {
    val malwareList = mutableListOf<Malware>()

    abstract fun act(deltaT: Float)
}