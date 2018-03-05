@file:Suppress("UnusedImport")

package ru.cryhards.brootkiddie.screens.market

import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.ui.UI
import java.lang.Math.pow
import java.lang.Math.sqrt
import kotlin.math.max

object MarketItems {

    fun generateItems(): ArrayList<Item> {
        val result = ArrayList<Item>()

        val got = Environment.player.inventory.items.map {
            Pair<String, Int>(it.title, if (it is Script) {
                it.level
            } else {
                0
            })
        }

        var miningLevel = 1
        var spreadingLevel = 1

        got.forEach {
            if (it.first == "Miner") {
                miningLevel = max(miningLevel, it.second + 1)
            }
            if (it.first == "Spreading Script") {
                spreadingLevel = max(spreadingLevel, it.second + 1)
            }
        }

        val mining = UI.SpreaderV3000()
        val multiplier = UI.spreadingMultiplier(Script.SIDES.LEFT)
        result.add(MarketItem(mining.title, mining.info, mining.iconTexture, pow(50.0, sqrt(miningLevel.toDouble())).toFloat(), mining))
        result.add(MarketItem(multiplier.title, multiplier.info, multiplier.iconTexture, pow(100.0, sqrt(spreadingLevel.toDouble())).toFloat(), multiplier))


        return result
    }

}