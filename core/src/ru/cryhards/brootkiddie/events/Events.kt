package ru.cryhards.brootkiddie.events

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import java.util.*

/**
 * List of all available events
 */
class Events {
    companion object {
        val eventsByName = mapOf<String, GameEvent>(
                Pair("Delayed Letter" , object : GameEvent("Delayed letter", "It took some time for this letter to get to you.") {
                    override fun act(data: Map<String, Any?>) {
                        var delay = (data["delay"] as Double).toInt()
                        Core.instance.addTask(Core.Task((data["delay"] as Double).toInt(), Core.Task.DayTaskPeriod, {
                                delay-=1
                                if (delay == 0) {
                                    Player.dialogs.add(Dialog.readFromFile("dialogs/${data["id"]}.json"))
                                }
                        }))
                    }
                })
        )
    }
}