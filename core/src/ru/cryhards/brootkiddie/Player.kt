package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Script


/**
 * Global reference to the player
 */
object Player {
    var name = "user0"
    var level = 0
    var money = 0.0
    var day = 0
    var dialogs = mutableListOf<Dialog>()
    /**
     * Collection of items owned by player
     */
    val inventory = Inventory()

    /**
     * Collection of items owned by player
     */
    class Inventory {
        val scripts = ArrayList<Script>()
        val malware = ArrayList<Malware>()
        // tools (script optimizer!!! - decreases size)
    }

}