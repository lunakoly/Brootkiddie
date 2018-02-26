package ru.cryhards.brootkiddie.screens.market

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.items.Item

object MarketItems {

    fun generateItems(): ArrayList<Item> {

        val result = ArrayList<Item>()



        return result
    }

    fun buy(item: Item) {
        Gdx.app.log("MARKET", "BUY ${item.title}")
    }

}