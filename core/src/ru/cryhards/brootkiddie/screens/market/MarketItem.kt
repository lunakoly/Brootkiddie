package ru.cryhards.brootkiddie.screens.market

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.effects.Converter
import ru.cryhards.brootkiddie.ui.UI

/**
 * Created by remmargorp on 02.03.18.
 */

class MarketItem(title: String, info: String, iconTexture: Texture, val cost: Float, val payload: Item) : Item(title, info, iconTexture, Item.Type.MARKET) {
    var bought = false

    init {
        val buyButton = UI.GlitchTextButton("BUY")
        buyButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (bought) return

                if (Environment.player.money < cost) {
                    buyButton.setText("NOT ENOUGH $")
                } else {
                    Environment.player.money -= cost
                    Environment.player.inventory.items.add(payload)
                    buyButton.setText("BOUGHT")
                    bought = true
                }
            }
        })
        actions.add(buyButton)
    }

    override fun toString(): String {
        return "Cost: $${Converter.humanReadable(cost)}\n" + payload.toString()
    }

    override fun represent(): Array<Actor> {
        val out = ArrayList<Actor>()

        val eff = UI.StaticLabel("Effects:")
        eff.setWrap(true)
        eff.style.background = null
        eff.color = Color.CORAL
        if (effects.size != 0) {
            effects.forEach { eff.setText(eff.text.toString() + "\n * ${it.title}\n ${it.info}") }
        } else {
            eff.setText(eff.text.toString() + "\n ** NONE **")
        }
        out += eff

        actions.forEach { out += it }
        return out.toTypedArray()
    }
}