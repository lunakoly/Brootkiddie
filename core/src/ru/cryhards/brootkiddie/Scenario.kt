package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.events.dialogs.Dialog

/**
 * Created by Dima on 12.02.2018.
 */
object Scenario {
    fun initialize() {
        Player.dialogs.add(Dialog.readFromFile("dialogs/example.json"))
    }
}