package ru.cryhards.brootkiddie.tests

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.effects.DisguiseEffect
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.screens.SplashScreen

/**
 * Write some kinda tests if u dont want to see Ideas warnings
 */
class Tests {
    fun test() {
        val apple = Item("Apple", "Fruit", Texture("img/items/worm.png"), Item.Type.SCRIPT)
        println(apple.title)
        println(apple.info)
        println(apple.type.info)

        val greyDeath = Item.Effect("Grey Death", "Kills everyone")
        println(greyDeath.title)
        println(greyDeath.info)

        apple.effects.add(greyDeath)
        println(apple.findEffect(greyDeath.title))
    }

    fun fest() {
        val scr1 = Script("Crack", "Focus-Pocus", Texture("img/items/worm.png"), 10)
        val scr2 = Script("EVIL", "Cry, baby", Texture("img/items/worm.png"), 5)
        val scr3 = Script("BadAss", "Very bad", Texture("img/items/worm.png"), 15)

        var mal = scr1.combine(scr2)
        println(mal.title)
        println(mal.info)

        mal = scr1.combine(scr2).combine(scr3)
        println(mal.title)
        println(mal.info)

        mal = scr1 + scr2 + scr3
        println(mal.title)
        println(mal.info)

        mal = scr1.combine(scr2 + scr3)
        println(mal.title)
        println(mal.info)

        println(mal.stats)
        println(scr1.level)
        println(scr2.size)

        mal.combine(mal)
    }

    fun brest() {
        val mining  = MiningEffect()
        val hiding = DisguiseEffect()
        val spreading = SpreadingEffect()

        val s = Script("Crack", "Focus-Pocus", Texture("img/items/worm.png"), 10)

        s.effects.add(mining)
        s.effects.add(hiding)
        s.effects.add(spreading)

        mining.miningSpeed += 0.1f
        hiding.suspiciousness += 0.1f
        spreading.suspiciousness += 0.1f
        spreading.spreadingSpeed += 0.1f
        spreading.infectiousness += 0.1f

        (s + s).update()
    }

    fun frost() {
//        val map = GlobalMap()
//        map.mapCoordinates(Actor())
//        map.unmapCoordinates(Actor())
//        map.mapCoordinates(Actor())
//        map.mapCoordinates(Actor())
    }

    fun nuke() {
        println(Player.money)
        println(Environment.day)
        Core.instance.switchBackgroundMusic(Assets.Sounds.AUTUMNS_DREAM_LULLABY)
        Core.instance.switchScreen(SplashScreen())
    }
}