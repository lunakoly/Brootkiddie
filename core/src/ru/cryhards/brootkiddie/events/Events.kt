package ru.cryhards.brootkiddie.events


import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMapScreen
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
                        Core.instance.addTask(Core.Task((data["delay"] as Double).toInt(), Environment.DAY_TASK_PERIOD, {
                                delay-=1
                                if (delay == 0) {
                                    val dialog = Dialog.readFromFile("dialogs/${data["id"]}.json")
                                    Player.dialogs.add(dialog)
                                    Environment.UI.console?.log("You've got a letter from ${dialog.sender}")
                                }
                            false
                        }))
                    }
                }),

                Pair("Introduction 1", object : GameEvent("Introduction 1", "One of the introduction events") {
                    override fun act(data: Map<String, Any?>) {
                        var countdown = 30
                        Core.instance.addTask(Core.Task(-1, (Environment.DAY_TASK_PERIOD*1.5f).toLong(), {
                            countdown-=1
                            if (countdown == 25) {

                                Environment.UI.console?.log("Feels empty here, right?")
                            }
                            if (countdown == 23) {

                                Environment.UI.console?.log("Hold on a second. I will fix it right now.")
                            }

                            if (countdown == 20) {

                                Environment.UI.console?.log("Wait, wait. Don't leave, please.")
                            }

                            if (countdown == 18) {
                                Environment.UI.console?.log("I just need a little more time.")
                            }

                            if (countdown == 12) {

                                Environment.UI.console?.log("OK. Done.")
                                Environment.UI.console?.log("I need your help now.")
                            }

                            if (countdown == 10) {
                                Environment.UI.console?.log("Click console 3 times.")
                            }

                            if (countdown == 8) {
                                Environment.UI.console?.log("Yes. That thing with text is console.")
                            }

                            if (countdown == 3) {
                                if (Environment.consoleCounter < 3)
                                    Environment.UI.console?.log("What are you waiting for?")
                            }

                            if (countdown == 3) {
                                if (Environment.consoleCounter < 3)
                                    Environment.UI.console?.log("Trust me: nothing will happen until you do what I've said.")
                            }

                            if (countdown == 0) {
                                if (Environment.consoleCounter < 3) {
                                    Environment.UI.console?.log("Just click it already. I will say nothing till then.")
                                }
                                return@Task true
                            }

                            return@Task false

                        }))
                    }
                }),

                Pair("Introduction 2", object : GameEvent("Introduction 2", "One of the introduction events"){
                    override fun act(data: Map<String, Any?>) {
                    }
                })
        )
    }
}