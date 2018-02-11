package ru.cryhards.brootkiddie.events

import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import java.util.*

/**
 * Created by Dima on 11.02.2018.
 */
class Events {
    companion object {
        val eventsById = listOf<GameEvent>(
                object : GameEvent("Free script", "Some anonymous gifted you a script."){
                    override val id = 0
                    override fun act() {
                        val script = Script("Miner", "Mines crypto", 5f)
                        script.effects.add(MiningEffect())
                        Player.inventory.scripts.add(Script("Miner", "Mines crypto", 5f))
                    }
                },

                object : GameEvent("FBI inspection", "FBI found one of your bank accounts and blocked it. You've lost some of your money."){
                    override val id = 1
                    override fun act() {
                        Player.money*=0.6 + 0.4*Random().nextDouble()
                    }
                }
        )
    }
}