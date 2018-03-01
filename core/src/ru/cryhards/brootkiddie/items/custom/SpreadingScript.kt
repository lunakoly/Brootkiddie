package ru.cryhards.brootkiddie.items.custom

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect

class SpreadingScript : Script("Spreading Script", "Spreads malware", Texture("img/items/worm.png"), 10f) {
    init {
        this.combine(SpreadingEffect())
    }
}