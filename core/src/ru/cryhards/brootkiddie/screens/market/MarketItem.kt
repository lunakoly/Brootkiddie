package ru.cryhards.brootkiddie.screens.market

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.UI

/**
 * Created by user on 2/26/18.
 */

class MarketItem(title: String, info: String, iconTexture: Texture, type: Item.Type, val cost: Float) : Item(title, info, iconTexture, type) {
    init {
        val buyButton = UI.GlitchTextButton("BUY")
    }
}