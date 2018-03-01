@file:Suppress("UnusedImport")

package ru.cryhards.brootkiddie.screens.market

import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.custom.MiningScript
import ru.cryhards.brootkiddie.items.custom.SpreadingScript
import java.lang.Math.pow
import java.lang.Math.sqrt

object MarketItems {

    fun generateItems(): ArrayList<Item> {
        val result = ArrayList<Item>()

        val got = Player.Inventory.items.map {
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

        val mining = MiningScript(miningLevel)
        result.add(MarketItem(mining.title, mining.info, mining.iconTexture, pow(50.0, sqrt(miningLevel.toDouble())).toFloat(), mining))

        val spreading = SpreadingScript(spreadingLevel)
        result.add(MarketItem(spreading.title, spreading.info, spreading.iconTexture, pow(50.0, sqrt(spreadingLevel.toDouble())).toFloat(), spreading))

        return result
    }

}