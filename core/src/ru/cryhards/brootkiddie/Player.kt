package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.items.Item


/**
 * Global reference to the player
 */
class Player {
    var name = "user0"
    var level = 0
    var money = 0.0f
    val dialogs = mutableListOf<Dialog>()
    val senders = mapOf(Pair("Game Master", "img/items/worm.png"))
    val inventory = Player.Inventory()
    /**
     * Collection of items owned by player
     */
    class Inventory {
        /**
         * All things that Player has
         */
        val items = ArrayList<Item>()
        // tools (script optimizer!!! - decreases size)
    }

}