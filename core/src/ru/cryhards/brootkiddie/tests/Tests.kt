package ru.cryhards.brootkiddie.tests

import ru.cryhards.brootkiddie.items.effects.DisguiseEffect
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Script

/**
 * Write some kinda tests if u dont want to see Ideas warnings
 */
class Tests {
    fun test() {
        val apple = Item("Apple", "Fruit", Item.Type.SCRIPT)
        println(apple.name)
        println(apple.info)
        println(apple.type.info)

        val greyDeath = Item.Effect("Grey Death", "Kills everyone")
        println(greyDeath.name)
        println(greyDeath.info)

        apple.effects.add(greyDeath)
        println(apple.findEffect(greyDeath.name))
    }

    fun fest() {
        val scr1 = Script("Crack", "Focus-Pocus", 10.4f)
        val scr2 = Script("EVIL", "Cry, baby", 5f)
        val scr3 = Script("BadAss", "Very bad", 15f)

        var mal = scr1.combine(scr2)
        println(mal.name)
        println(mal.info)

        mal = scr1.combine(scr2).combine(scr3)
        println(mal.name)
        println(mal.info)

        mal = scr1 + scr2 + scr3
        println(mal.name)
        println(mal.info)

        mal = scr1.combine(scr2 + scr3)
        println(mal.name)
        println(mal.info)
    }

    fun brest() {
        val mining  = MiningEffect()
        val hiding = DisguiseEffect()
        val spreading = SpreadingEffect()

        val s = Script("Crack", "Focus-Pocus", 10.4f)

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
}