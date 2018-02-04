package ru.cryhards.brootkiddie.cleanup

import ru.cryhards.brootkiddie.cleanup.items.Malware
import ru.cryhards.brootkiddie.cleanup.items.Script

/**
 * Global reference to the player
 */
object Player {
    var name = "user0"
    var level = 0

    /**
     * just instance
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