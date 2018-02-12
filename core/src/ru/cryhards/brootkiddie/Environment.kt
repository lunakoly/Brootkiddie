package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.ShaderableConsole

/**
 * Created by user on 2/12/18.
 */

object Environment {
    val DAY_TASK_PERIOD = 3000L

    var day = 0

    fun initialize() {
        Player.dialogs.add(Dialog.readFromFile("dialogs/example.json"))

        // run day updater
        Core.instance.addTask(Core.Task(-1, Core.Task.DayTaskPeriod, {
            Player.day++
            UI.console?.log("Day ${Player.day}")
        }))
    }


//    val ui = UI()

    object UI {
        var console: ShaderableConsole? = null
    }
}