package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.ui.items.ItemBlock
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
        /**
         * All things that Player has
         */
        val items = ArrayList<ItemBlock>()
        // tools (script optimizer!!! - decreases size)
    }

}