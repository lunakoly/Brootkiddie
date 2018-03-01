package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.custom.MiningScript
import ru.cryhards.brootkiddie.items.custom.SpreadingScript


/**
 * Global reference to the player
 */
object Player {
    var name = "user0"
    var level = 0
    var money = 0.0f
    var dialogs = mutableListOf<Dialog>()

    /**
     * Collection of items owned by player
     */
    object Inventory {
        /**
         * All things that Player has
         */
        val items = ArrayList<Item>()

        // test bench
        init {
            items.add(MiningScript())
            items.add(SpreadingScript())
        }
        // tools (script optimizer!!! - decreases size)
    }

}