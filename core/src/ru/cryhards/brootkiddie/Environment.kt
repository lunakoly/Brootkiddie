package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMap
import ru.cryhards.brootkiddie.ui.ShaderableConsole

/**
 * Holds gameplay stage
 */

object Environment {
    /**
     * Time that one in-game day occures (ms)
     */
    const val DAY_TASK_PERIOD = 1000L

    /**
     * Current day
     */
    var day = 0

    var activeMalware: Malware? = null

    val TOTAL_NODES = 1.3e9.toLong()
    var infectedNodes = 0L

    val SUSPICIOUSNESS_DETECT = 1.0f
    var currentSuspiciousness = 0.0f
    var isMalwareDetected = false

    /**
     * Loads game state. Call on startup
     */
    fun initialize() {
        Player.dialogs.add(Dialog.readFromFile("dialogs/example.json"))

        // run day updater
        Core.instance.addTask(Core.Task(-1, DAY_TASK_PERIOD, {
            UI.console?.log("Day ${++day}")
            false
        }))
    }


    /**
     * Gives quick access to main ui
     */
    object UI {
        var console: ShaderableConsole? = null
        var globalMap: GlobalMap? = null
    }
}