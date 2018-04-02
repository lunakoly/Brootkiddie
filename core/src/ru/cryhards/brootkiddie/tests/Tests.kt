package ru.cryhards.brootkiddie.tests

import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.screens.SplashScreen

/**
 * Write some kinda tests if u dont want to see Ideas warnings
 */
class Tests {
    fun test() {
        val apple = Item("Apple", "Fruit", "img/items/worm.png", Item.Type.SCRIPT)
        println(apple.title)
        println(apple.info)
        println(apple.type.info)

        val greyDeath = Item.Effect("Grey Death", "Kills everyone")
        println(greyDeath.title)
        println(greyDeath.info)

        apple.effects.add(greyDeath)
        println(apple.findEffect(greyDeath.title))
    }


    fun frost() {
//        val map = GlobalMap()
//        map.mapCoordinates(Actor())
//        map.unmapCoordinates(Actor())
//        map.mapCoordinates(Actor())
//        map.mapCoordinates(Actor())
    }

    fun nuke() {
        println(Environment.instance.player.money)
        println(Environment.instance.day)
        Core.instance.switchBackgroundMusic(Assets.Sounds.AUTUMNS_DREAM_LULLABY)
        Core.instance.switchScreen(SplashScreen())
    }
}