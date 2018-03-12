package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.Scripts
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMap
import ru.cryhards.brootkiddie.ui.ShaderableConsole
import java.io.Serializable

/**
 * Holds gameplay stage
 */

object Environment : Serializable {
    /**
     * Time that one in-game day occures (ms)
     */
    const val DAY_TASK_PERIOD = 2000L

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

    var consoleCounter = 0

    lateinit var player: Player

    /**
     * Loads game state. Call on startup
     */
    fun initialize() {

        day = 0

        activeMalware = null
        infectedNodes = 0L

        currentSuspiciousness = 0.0f
        isMalwareDetected = false

        consoleCounter = 0

        player = Player()

        player.dialogs.add(Dialog.readFromFile("dialogs/introduction1.json"))
        Environment.player.inventory.items.add(Scripts.emptyItem())
        Environment.player.inventory.items.add(Scripts.loremItem())
        Environment.player.inventory.items.add(Scripts.spreaderV3000())
        Environment.player.inventory.items.add(Scripts.spreadingMultiplier(1, Script.SIDES.LEFT))

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