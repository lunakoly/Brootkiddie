package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.items.ItemBlock


/**
 * Global reference to the player
 */
object Player {
    var name = "user0"
    var level = 0
    var money = 0.0
    var dialogs = mutableListOf<Dialog>()

    /**
     * Collection of items owned by player
     */
    object Inventory {
        /**
         * All things that Player has
         */
        val items = ArrayList<ItemBlock>()
        // tools (script optimizer!!! - decreases size)
    }

}